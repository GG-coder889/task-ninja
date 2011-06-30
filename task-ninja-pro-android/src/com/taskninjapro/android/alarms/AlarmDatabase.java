package com.taskninjapro.android.alarms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.taskninjapro.android.app.Constants;

public class AlarmDatabase extends SQLiteOpenHelper implements Constants {
	
	public static HashMap<Integer, Alarm> mAlarms;
	public static List<Integer> mIds;
	public static AlarmDatabase mAlarmDatabase;
	
	private AlarmDatabase(Context context) {
		super(context, ALARMS_DATABASE, null, ALARM_DATABASE_VERSION);
		
		if (mIds == null) {
			mIds = getIds();
		}
		if (mAlarms == null) {
			mAlarms = new HashMap<Integer, Alarm>();
		}
		
	}

	public static AlarmDatabase getInstance(Context context) {
		if (mAlarmDatabase == null) {
			mAlarmDatabase = new AlarmDatabase(context);
		}
		return mAlarmDatabase;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + ALARMS_TABLE + "(" + 
				_ID + " INTEGER PRIMARY KEY ON CONFLICT REPLACE AUTOINCREMENT, " + 
				KEY_TASK_ID + " INTEGER," +
				KEY_WHEN + " INTEGER," +
				KEY_RECURRING + " INTEGER, " +
				KEY_QUEUING + " INTEGER, " +
				KEY_NOTIFICATION + " INTEGER" +
				");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Integer> getIds() {
		LinkedList<Integer> ids = new LinkedList<Integer>();
		SQLiteDatabase db = getReadableDatabase();
		String query = String.format("SELECT %s FROM %s ORDER BY %s ASC;", _ID,
				ALARMS_TABLE, _ID);
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			int index = cursor.getColumnIndex(_ID);
			do {
				ids.add(cursor.getInt(index));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return ids;
	}


	public List<Alarm> getAlarms() {
		List<Alarm> alarms = new LinkedList<Alarm>();
		for (int id: mIds){
			Alarm alarm = getAlarm(id);
			if (alarm != null && !alarm.needsDelete()){
				alarms.add(alarm);
			}
		}
		return alarms;
	}
	


	public Alarm getAlarm(int id) {
		Alarm alarm = mAlarms.get(id);
		if (alarm != null){
			return alarm;
		}
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(ALARMS_TABLE, ALARM_KEYS, _ID + " = " + id,
				null, null, null, null);
		if (cursor.moveToFirst()){
			ContentValues values = new ContentValues();
			int index;

			for (String key : ALARM_KEYS) {
				index = cursor.getColumnIndex(key);
				if (index != -1) {
					if (key.equals(KEY_WHEN) || 
							key.equals(KEY_RECURRING))
						values.put(key, cursor.getLong(index));
					else
						values.put(key, cursor.getInt(index));
				}
			}
		} 
		cursor.close();
		db.close();
		return alarm;
	}

	public void add(Alarm alarm) {
		mAlarms.put(alarm.getAsInteger(_ID), alarm);
	}

	public int getNewId() {
		Integer newId = 1;
		for (int i = 0; i < mIds.size(); i++) {
			Integer id = mIds.get(i);
			if (id - newId > 0) {
				newId = id - 1;
				mIds.add(i, newId);
				return newId;
			} else {
				newId = id + 1;
			}
		}
		mIds.add(newId);
		return newId;
	}
	
	
	boolean mWriting = false;

	public void writeCycle() {
		if (!mWriting) {
			Set<Integer> keys = mAlarms.keySet();
			for (Integer key : keys) {
				Alarm alarm = mAlarms.get(key);
				if (alarm.needsUpdate()) {
					mWriting = true;
					alarm.setNeedsUpdate(false);
					new AsyncUpdate().execute(alarm);
					break;
				}
			}
		}
	}
	
	private class AsyncUpdate extends AsyncTask<Alarm, Void, List<Integer>> {
		@Override
		protected List<Integer> doInBackground(Alarm ... alarms) {
			return update(alarms);
		}

		@Override
		protected void onPostExecute(List<Integer> ids) {
			for (Integer id : ids) {
				if (id < 0) {
					Alarm alarm = mAlarms.get(-id);
					if (alarm != null) {
						alarm.setNeedsUpdate(true);
					}
				} else {
					Alarm alarm = mAlarms.get(id);
					if (alarm != null) {
						if (!alarm.needsUpdate()) {
							mAlarms.remove(id);
						}
					}
				}
			}
			mWriting = false;
			writeCycle();
		}
	}
	
	public List<Integer> update(Alarm ... alarms){
		SQLiteDatabase db = getWritableDatabase();
		List<Integer> Ids = new LinkedList<Integer>();
		for (Alarm alarm : alarms) {
			int id = alarm.getAsInteger(_ID);
			if (alarm.needsDelete()) {
				try {
					db.delete(ALARMS_TABLE, _ID + " = " + id, null);
					Ids.add(id);
				} catch (Exception e) {
					e.printStackTrace();
					Ids.add(-id);
				}
			} else {
				try {
					db.insert(ALARMS_TABLE, null, alarm.getValues());
					Ids.add(id);
				} catch (Exception e) {
					e.printStackTrace();
					Ids.add(-id);
				}
			}
		}
		db.close();
		return Ids;
	}
	
}
