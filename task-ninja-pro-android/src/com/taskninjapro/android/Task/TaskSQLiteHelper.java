package com.taskninjapro.android.Task;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.taskninjapro.android.app.Constants;

public class TaskSQLiteHelper extends SQLiteOpenHelper implements Constants {

	private static final String TAG = "DatabaseHelper";

	private static TaskSQLiteHelper mHelper;

	private Context mContext;

	private TaskSQLiteHelper(Context context) {
		super(context, TASKS_DATABASE, null, DATABASE_VERSION);
		mContext = context;
	}

	public static TaskSQLiteHelper getInstance(Context context) {
		if (mHelper == null) {
			mHelper = new TaskSQLiteHelper(context);
		}
		return mHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TASKS_TABLE + "(" + 
				_ID + " INTEGER PRIMARY KEY ON CONFLICT REPLACE AUTOINCREMENT, " + 
				KEY_WHAT + " TEXT, " + 
				KEY_NOTES + " TEXT, " + 
				KEY_DUE_DATE + " INTEGER, " + 
				KEY_COMPLETED + " BOOLEAN, " + 
				KEY_TASKS + " TEXT, " + 
				KEY_PARENT + " INTEGER, " + 
				KEY_PRIORITY + " INTEGER, " +
				
				KEY_SINGLE_ALERT + " INTEGER, " +
				KEY_SINGLE_ALERT_TIME + " INTEGER, " +
				
				KEY_RECURRING_ALERT + " INTEGER, " +
				KEY_RECURRING_ALERT_TIME + " INTEGER, " +
				KEY_RECURRING_ALERT_MONDAY + " INTEGER, " +
				KEY_RECURRING_ALERT_TUESDAY + " INTEGER, " +
				KEY_RECURRING_ALERT_WEDNESDAY + " INTEGER, " +
				KEY_RECURRING_ALERT_THURSDAY + " INTEGER, " +
				KEY_RECURRING_ALERT_FRIDAY + " INTEGER, " +
				KEY_RECURRING_ALERT_SATURDAY + " INTEGER, " +
				KEY_RECURRING_ALERT_SUNDAY + " INTEGER, " +
				
				KEY_SINGLE_QUEUING + " INTEGER, " +
				KEY_SINGLE_QUEUING_TIME + " INTEGER, " +
				KEY_SINGLE_QUEUEING_FRONT + " INTEGER, " +
				
				KEY_RECURRING_QUEUING + " INTEGER, " +
				KEY_RECURRING_QUEUING_TIME + " INTEGER, " +
				KEY_RECURRING_QUEUING_MONDAY + " INTEGER, " +
				KEY_RECURRING_QUEUING_TUESDAY + " INTEGER, " +
				KEY_RECURRING_QUEUING_WEDNESDAY + " INTEGER, " +
				KEY_RECURRING_QUEUING_THURSDAY + " INTEGER, " +
				KEY_RECURRING_QUEUING_FRIDAY + " INTEGER, " +
				KEY_RECURRING_QUEUING_SATURDAY + " INTEGER, " +
				KEY_RECURRING_QUEUING_SUNDAY + " INTEGER, " +
				KEY_RECURRING_QUEUING_FRONT + " INTEGER" +
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
				TASKS_TABLE, _ID);
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

	private Task readTask(Cursor cursor) {
		ContentValues values = new ContentValues();
		int index;

		for (String key : TASK_KEYS) {
			index = cursor.getColumnIndex(key);
			if (index != -1) {
				if (key.equals(KEY_WHAT) || 
						key.equals(KEY_TASKS) ||
						key.equals(KEY_NOTES))
					values.put(key, cursor.getString(index));
				else if (key.equals(KEY_DUE_DATE) || 
						key.equals(KEY_SINGLE_ALERT_TIME) ||
						key.equals(KEY_RECURRING_ALERT_TIME)||
						key.equals(KEY_RECURRING_QUEUING_TIME) ||
						key.equals(KEY_SINGLE_QUEUING_TIME))
					values.put(key, cursor.getLong(index));
				else
					values.put(key, cursor.getInt(index));
			}
		}
		return new Task(values, mContext);
	}

	public Task getTask(int id) {
		try {
			Task task = null;
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(TASKS_TABLE, TASK_KEYS, _ID + " = " + id,
					null, null, null, null);
			if (cursor.moveToFirst())
				task = readTask(cursor);
			cursor.close();
			db.close();
			return task;
		} catch (Exception e){
			e.printStackTrace();
			return getTask(id);
		}
		
	}

	public List<Integer> update(Task[] tasks) {
		SQLiteDatabase db = getWritableDatabase();
		List<Integer> Ids = new LinkedList<Integer>();
		for (Task task : tasks) {
			if (task.needsDelete()) {
				try {
					db.delete(TASKS_TABLE, _ID + " = " + task.getId(), null);
					Ids.add(task.getId());
				} catch (Exception e) {
					e.printStackTrace();
					Ids.add(-task.getId());
				}
			} else {
				try {
					db.insert(TASKS_TABLE, null, task.getValues());
					Ids.add(task.getId());
				} catch (Exception e) {
					e.printStackTrace();
					Ids.add(-task.getId());
				}
			}
		}
		db.close();
		return Ids;
	}
}
