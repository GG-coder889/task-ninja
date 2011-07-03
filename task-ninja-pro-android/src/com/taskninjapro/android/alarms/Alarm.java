package com.taskninjapro.android.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.taskninjapro.android.app.Constants;

public class Alarm implements Constants {
	
	private static final String TAG = "Alarm";
	
	ContentValues mValues;
	Context mContext;
	boolean mNeedsUpdate = false;
	boolean mNeedsDelete = false;
	private AlarmDatabase mAlarmDatabase;
	
	public Alarm(ContentValues values, Context context){
		mAlarmDatabase = AlarmDatabase.getInstance(context);
		mContext = context;
		mValues = values;
	}

	public Alarm(int taskId, long when, long recurring, Context context) {
		mAlarmDatabase = AlarmDatabase.getInstance(context);
		mContext = context;
		mValues = new ContentValues();
		mValues.put(KEY_TASK_ID, taskId);
		mValues.put(KEY_WHEN, when);
		mValues.put(KEY_RECURRING, recurring);
		mValues.put(_ID, mAlarmDatabase.getNewId());
		onChange();
	}
	
	private void onChange() {
		mNeedsUpdate = true;
		mAlarmDatabase.add(this);
	}
	
	public ContentValues getValues() {
		return mValues;
	}

	public void put(String key, int value) {
		mValues.put(key, value);
		onChange();
	}
	
	public void put(String key, boolean value) {
		if (value)
			mValues.put(key, 1);
		else
			mValues.put(key, 0);
		onChange();
	}
	
	public void put(String key, long value) {
		mValues.put(key, value);
		onChange();
	}
	
	public boolean getAsBoolean(String key){
		Integer i = mValues.getAsInteger(key);
		if (i == null || i < 1){
			return false;
		} else {
			return true;
		}
	}
	
	public long getAsLong(String key){
		Long l = mValues.getAsLong(key);
		if (l == null){
			return 0;
		} else {
			return l;
		}
	}
	
	public int getAsInteger(String key){
		Integer value = mValues.getAsInteger(key);
		if (value == null){
			return 0;
		} else {
			return value;
		}
	}

	public void set() {
		Log.d(TAG, "set");
		
		Intent i = null;
		
		if (getAsBoolean(KEY_QUEUING)){
			Log.d(TAG, "set queuing");
			i = new Intent(mContext, QueuingService.class);
			
		} else if (getAsBoolean(KEY_NOTIFICATION)) {
			Log.d(TAG, "set notifiaction");
			i = new Intent(mContext, NotificationService.class);
		}
		
		if (i != null){
			i.putExtra(KEY_TASK_ID, getAsInteger(KEY_TASK_ID));
			i.putExtra(_ID, getAsInteger(_ID));
			PendingIntent pi = PendingIntent.getService(mContext, 0, i, 0);
			
			AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
			am.set(AlarmManager.RTC, getAsLong(KEY_WHEN), pi);
		}
	}

	public boolean needsDelete() {
		return mNeedsDelete;
	}
	
	public void delete() {
		mNeedsDelete = true;
		onChange();
	}

	public void setNeedsUpdate(boolean b) {
		mNeedsUpdate = b;
		mAlarmDatabase.add(this);
	}

	public boolean needsUpdate() {
		return mNeedsUpdate;
	}
	
	public int getId(){
		return mValues.getAsInteger(_ID);
	}


}
