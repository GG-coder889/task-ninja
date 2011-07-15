package com.taskninjapro.android.test;

import android.content.SharedPreferences;
import android.test.AndroidTestCase;

import com.taskninjapro.android.app.Constants;

public class AlarmTest extends AndroidTestCase implements Constants {
	
	SharedPreferences mSettings;

	
	@Override
    protected void setUp() throws Exception {
    	super.setUp();
    	 
//    	mTaskSQLiteHelper = TaskSQLiteHelper.getInstance(getContext());
//    	SQLiteDatabase db = mTaskSQLiteHelper.getWritableDatabase();
//    	db.delete(TASKS_TABLE, null, null);
//    	db.close();
//
//    	mTaskDatabase = TaskDatabase.getInstance(getContext());
//    	TaskDatabase.mTasks = new HashMap<Integer, Task>();
//    	TaskDatabase.mIds = new LinkedList<Integer>();
//    	 
//    	mAlarmDatabase = AlarmDatabase.getInstance(getContext());
//    	AlarmDatabase.mAlarms = new HashMap<Integer, Alarm>();
//    	AlarmDatabase.mIds = new LinkedList<Integer>();
//    	db = mAlarmDatabase.getWritableDatabase();
//    	db.delete(ALARMS_TABLE, null, null);
//    	db.close();
//    	 
//    	mSettings = mContext.getSharedPreferences(PREFS, PREFS_MODE);
//    	mSettings.edit().clear().commit();
    }
	
//	@Override
//	protected void tearDown() {
//		SQLiteDatabase db = mTaskSQLiteHelper.getWritableDatabase();
//		db.delete(TASKS_TABLE, null, null);
//	 	db.close();
//	 	
//		db = mAlarmDatabase.getWritableDatabase();
//		db.delete(ALARMS_TABLE, null, null);
//		db.close();
//	 
//	 
//	 	mSettings.edit().clear().commit();
//	}
	
//	public void testNewAlarmConstructor(){
//		fail("Test Unwritten");
//	}
//	
//	public void testValuesConstructor(){
//		fail("Test Unwritten");
//	}
	
//	public void testSet(){
//		fail("Test Unwritten");
//	}
	
//	public void testNeedsUpdate(){
//		Alarm alarm = new Alarm(0, 0, 0, mContext);
//		assertEquals(true, alarm.needsUpdate());
//		alarm.setNeedsUpdate(false);
//		assertEquals(false, alarm.needsUpdate());
//		alarm.setNeedsUpdate(true);
//		assertEquals(true, alarm.needsUpdate());
//	}
//	
//	public void testOnChange(){
//		ContentValues values = new ContentValues();
//		values.put(_ID, 1);
//		Alarm alarm = new Alarm(values, mContext);
//		
//		assertEquals(0, mAlarmDatabase.mAlarms.size());
//		assertEquals(false, alarm.needsUpdate());
//		assertEquals(false, alarm.needsDelete());
//		
//		alarm.put(KEY_WHEN, 1);
//		
//		assertEquals(1, mAlarmDatabase.mAlarms.size());
//		assertEquals(true, alarm.needsUpdate());
//		assertEquals(false, alarm.needsDelete());
//	}
//	
//	public void testNeedsDelete(){
//		Alarm alarm = new Alarm(0, 0, 0, mContext);
//		assertEquals(false, alarm.needsDelete());
//		alarm.delete();
//		assertEquals(true, alarm.needsDelete());
//	}
//	
////	public void delete(){
////		fail("Test Unwritten");
////	}
//	
//	
//	public void testPutInt(){
//		Alarm alarm = new Alarm(0, 0, 0, mContext);
//		int i = 100;
//		alarm.put(KEY_WHEN, i);
//		assertEquals(i, alarm.getAsInteger(KEY_WHEN));
//		
//		alarm.setNeedsUpdate(false);
//		alarm.put(KEY_WHEN, i);
//		
//		assertEquals(true, alarm.needsUpdate());
//		assertEquals(false, alarm.needsDelete());
//	}
//	
//	public void testPutBool(){
//		Alarm alarm = new Alarm(0, 0, 0, mContext);
//		
//		assertEquals(0, alarm.getAsInteger(KEY_WHEN));
//		alarm.put(KEY_WHEN, true);
//		assertEquals(1, alarm.getAsInteger(KEY_WHEN));
//		
//		alarm.setNeedsUpdate(false);
//		alarm.put(KEY_WHEN, true);
//		
//		assertEquals(true, alarm.needsUpdate());
//		assertEquals(false, alarm.needsDelete());
//	}
//	
//	public void testPutLong(){
//		Alarm alarm = new Alarm(0, 0, 0, mContext);
//		
//		long time = System.currentTimeMillis();
//		alarm.put(KEY_WHEN, time);
//		assertEquals(time, alarm.getAsLong(KEY_WHEN));
//		
//		alarm.setNeedsUpdate(false);
//		alarm.put(KEY_WHEN, time);
//		
//		assertEquals(true, alarm.needsUpdate());
//		assertEquals(false, alarm.needsDelete());
//	}
//	
//	public void testGetAsBoolean(){
//		Alarm alarm = new Alarm(0, 0, 0, mContext);
//		
//		assertEquals(true, alarm.getAsBoolean(_ID));
//		
//		alarm.put(KEY_WHEN, -1000);
//		assertEquals(false, alarm.getAsBoolean(KEY_WHEN));
//		
//		alarm.put(KEY_WHEN, -10000000000000l);
//		assertEquals(false, alarm.getAsBoolean(KEY_WHEN));
//		
//		alarm.put(KEY_WHEN, 1000);
//		assertEquals(true, alarm.getAsBoolean(KEY_WHEN));
//		
//		alarm.put(KEY_WHEN, 10000000000000l);
//		assertEquals(true, alarm.getAsBoolean(KEY_WHEN));
//	}
//	
//	public void testGetAsLong(){
//		Alarm alarm = new Alarm(0, 0, 0, mContext);
//		
//		assertEquals(1, alarm.getAsLong(_ID));
//		
//		assertEquals(0, alarm.getAsLong(KEY_WHEN));
//		long time = System.currentTimeMillis();
//		alarm.put(KEY_WHEN, time);
//		assertEquals(time, alarm.getAsLong(KEY_WHEN));
//	}
//	
//	public void testGetAsInteger(){
//		Alarm alarm = new Alarm(0, 0, 0, mContext);
//		
//		assertEquals(1, alarm.getAsInteger(_ID));
//		
//		assertEquals(0, alarm.getAsLong(KEY_WHEN));
//		long l = -System.currentTimeMillis();
//		alarm.put(KEY_WHEN, l);
//		assertTrue(alarm.getAsLong(KEY_WHEN) < 1);
//	}
	


}
