package com.taskninjapro.android.test;

import android.test.AndroidTestCase;

import com.taskninjapro.android.app.Constants;

public class AlarmDatabaseTest extends AndroidTestCase implements Constants {
//	
//	private TaskSQLiteHelper mTaskSQLiteHelper;
//	private TaskDatabase mTaskDatabase;
//	private AlarmDatabase mAlarmDatabase;
//	
//	SharedPreferences mSettings;
//
//	
//	@Override
//    protected void setUp() throws Exception {
//    	super.setUp();
//    	 
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
//    }
//	
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
//	
//	public void testGetInstance(){
//		assertEquals(mAlarmDatabase, AlarmDatabase.getInstance(getContext()));
//	}
//	
//	public void testGetIds(){
//		assertEquals(0, mAlarmDatabase.getIds().size());
//		Alarm alarm = new Alarm(0, 0, 0, mContext);
//		mAlarmDatabase.update(alarm);
//		assertEquals(1, mAlarmDatabase.getIds().size());
//		assertTrue(mAlarmDatabase.getIds().contains(alarm.getId()));
//	}
//	
//	public void testGetAlarms(){
//		Alarm alarm1 = new Alarm(0, 0, 0, mContext);
//		Alarm alarm2 = new Alarm(0, 0, 0, mContext);
//		Alarm alarm3 = new Alarm(0, 0, 0, mContext);
//		Alarm alarm4 = new Alarm(0, 0, 0, mContext);
//		
//		List<Alarm> alarms = mAlarmDatabase.getAlarms();
//		
//		assertEquals(alarms.size(), 4);
//		assertSame(alarm1, alarms.remove(0));
//		assertSame(alarm2, alarms.remove(0));
//		assertSame(alarm3, alarms.remove(0));
//		assertSame(alarm4, alarms.remove(0));
//	}
//	
//	public void testAdd(){
//		assertEquals(0, AlarmDatabase.mAlarms.size());
//		
//		ContentValues values = new ContentValues();
//		int id = 1;
//		values.put(_ID, id);
//		Alarm alarm = new Alarm(values, mContext);
//		mAlarmDatabase.add(alarm);
//		assertEquals(1, AlarmDatabase.mAlarms.size());
//		
//		Alarm alarm2 = AlarmDatabase.mAlarms.get(id);
//		assertSame(alarm, alarm2);
//	}
//
//	public void testGetNewId(){
//		assertEquals(1, mAlarmDatabase.getNewId());
//		assertEquals(2, mAlarmDatabase.getNewId());
//		assertEquals(3, mAlarmDatabase.getNewId());
//		assertEquals(4, mAlarmDatabase.getNewId());
//		
//		AlarmDatabase.mIds.add(100);
//		
//		assertEquals(99, mAlarmDatabase.getNewId());
//		assertEquals(98, mAlarmDatabase.getNewId());
//		assertEquals(97, mAlarmDatabase.getNewId());
//		assertEquals(96, mAlarmDatabase.getNewId());
//	}
//	
////	public void testUpdate(){
////		fail("Test Unwritten");
////	}
}
























