package com.taskninjapro.android.test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.taskninjapro.android.Task.Task;
import com.taskninjapro.android.Task.TaskDatabase;
import com.taskninjapro.android.Task.TaskSQLiteHelper;
import com.taskninjapro.android.app.Constants;

public class TaskTest extends AndroidTestCase implements Constants {
	
	private static final String TAG = "TaskTest";
	
	private TaskSQLiteHelper mTaskSQLiteHelper;
	TaskDatabase mTaskDatabase;
	
	SharedPreferences mSettings;

	
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 
    	 mTaskSQLiteHelper = TaskSQLiteHelper.getInstance(getContext());
    	 SQLiteDatabase db = mTaskSQLiteHelper.getWritableDatabase();
    	 db.delete(TASKS_TABLE, null, null);
    	 db.close();

    	 mTaskDatabase = TaskDatabase.getInstance(getContext());
    	 TaskDatabase.mTasks = new HashMap<Integer, Task>();
    	 TaskDatabase.mIds = new LinkedList<Integer>();	 
    	 
    	 mSettings = mContext.getSharedPreferences(PREFS, PREFS_MODE);
    	 mSettings.edit().clear().commit();
    }
	
	@Override
	protected void tearDown() {
		SQLiteDatabase db = mTaskSQLiteHelper.getWritableDatabase();
		db.delete(TASKS_TABLE, null, null);
   	 	db.close();
   	 	mSettings.edit().clear().commit();
	}
	
	public void testWhatConstructor(){
		Task task = new Task("What", mContext);
		
		assertEquals(true, task.needsUpdate());
		assertEquals(false, task.needsDelete());
		assertFalse(0 == task.getId());
	}
	
	public void testGetWhat(){
		String what = "what";
		Task task = new Task(what, mContext);
		
		assertEquals(what, task.getWhat());
	}
	
	public void testGetId(){
		Task task = new Task("What", mContext);
		assertEquals(1, task.getId());
	}
	
	public void testNeedsUpdate(){
		Task task = new Task("What", mContext);
		assertEquals(true, task.needsUpdate());
		task.setNeedsUpdate(false);
		assertEquals(false, task.needsUpdate());
		task.setNeedsUpdate(true);
		assertEquals(true, task.needsUpdate());
	}
	
	public void testOnChange(){
		ContentValues values = new ContentValues();
		values.put(_ID, 1);
		Task task = new Task(values, mContext);
		
		assertEquals(0, mTaskDatabase.mTasks.size());
		assertEquals(false, task.needsUpdate());
		assertEquals(false, task.needsDelete());
		
		task.put(KEY_COMPLETED, true);
		
		assertEquals(1, mTaskDatabase.mTasks.size());
		assertEquals(true, task.needsUpdate());
		assertEquals(false, task.needsDelete());
		
	}
	
	public void testNeedsDelete(){
		Task task = new Task("What", mContext);
		assertEquals(false, task.needsDelete());
		task.delete();
		assertEquals(true, task.needsDelete());
	}
	
	public void testDelete(){
		Task parent = new Task("What", mContext);
		Task task = new Task("What", mContext);
		Task sub = new Task("What", mContext);
		
		task.setParent(parent);
		task.addSubtask(sub);
		
		task.delete();
		
		assertTrue(task.needsDelete());
		assertEquals(null, task.getParent());
		assertEquals(0, task.getSubtaskIds().size());
		
		assertTrue(sub.needsDelete());
		assertEquals(null, sub.getParent());
		
		assertEquals(0, parent.getSubtaskIds().size());
		assertFalse(parent.needsDelete());
	}
	
	public void testAddSubtask(){
		Task task = new Task("What", mContext);
		task.setNeedsUpdate(false);
		Task sub1 = new Task("What", mContext);
		Task sub2 = new Task("What", mContext);
		
		assertEquals(0, task.getSubtaskIds().size());
		
		task.addSubtask(sub1);
		assertEquals(1, task.getSubtaskIds().size());
		assertSame(task, sub1.getParent());
		
		task.addSubtask(sub1);
		assertEquals(1, task.getSubtaskIds().size());
		
		task.addSubtask(sub2);
		assertEquals(2, task.getSubtaskIds().size());
		assertSame(task, sub2.getParent());
		
//		LinkedHashSet<Task> subtasks = task.getSubtaskIds();
//		assertTrue(subtasks.contains(sub1));
//		assertTrue(subtasks.contains(sub2));
		
		assertEquals(true, task.needsUpdate());
		assertEquals(false, task.needsDelete());
		
	}
	
	public void testSetSubtasks(){
		Task task = new Task("What", mContext);
		task.setNeedsUpdate(false);
		Task sub1 = new Task("What", mContext);
		Task sub2 = new Task("What", mContext);
		
		LinkedHashSet<Task> subtasks = new LinkedHashSet<Task>();
		subtasks.add(sub1);
		subtasks.add(sub2);
//
//		task.setSubtaskIds(subtasks);
//		assertEquals(2, task.getSubtaskIds().size());
//		assertSame(task, sub1.getParent());
//		assertSame(task, sub2.getParent());
//		
//		subtasks = task.getSubtaskIds();
		assertTrue(subtasks.contains(sub1));
		assertTrue(subtasks.contains(sub2));
		
		assertEquals(true, task.needsUpdate());
		assertEquals(false, task.needsDelete());
	}
	
	public void testRemoveSubtask(){
		Task task = new Task("What", mContext);
		task.setNeedsUpdate(false);
		
		Task sub1 = new Task("What", mContext);
		Task sub2 = new Task("What", mContext);
		task.addSubtask(sub1);
		task.addSubtask(sub2);
		
		task.removeSubtask(sub2);
		
//		LinkedHashSet<Task> subtasks = task.getSubtaskIds();
//		assertEquals(1, subtasks.size());
//		assertFalse(subtasks.contains(sub2));
		
		assertEquals(true, task.needsUpdate());
		assertEquals(false, task.needsDelete());
	}
	
	public void testGetSubtasks() {
		Task task = new Task("What", mContext);
		Task sub1 = new Task("What", mContext);
		Task sub2 = new Task("What", mContext);
		
		StringBuffer sb = new StringBuffer();
		sb.append(sub1.getId()).append(',').append(sub2.getId());
		task.put(KEY_TASKS, sb.toString());
		
//		LinkedHashSet<Task> subtasks = task.getSubtaskIds();
//		assertTrue(subtasks.contains(sub1));
//		assertTrue(subtasks.contains(sub2));
	}
	
	public void testSetParent(){
		Task task = new Task("What", mContext);
		Task sub = new Task("What", mContext);
		sub.setNeedsUpdate(false);
		
		sub.setParent(task);
		assertEquals(task, sub.getParent());
		
		assertEquals(true, sub.needsUpdate());
		assertEquals(false, sub.needsDelete());
		
		assertTrue(task.getSubtaskIds().contains(sub));
	}
	
	public void testGetParent(){
		Task task = new Task("What", mContext);
		Task sub = new Task("What", mContext);
		sub.put(KEY_PARENT, task.getId());
		assertEquals(task, sub.getParent());
	}
	
	public void testPutInt(){
		Task task = new Task("What", mContext);
		int i = 100;
		task.put(KEY_COMPLETED, i);
		assertEquals(i, task.getAsInteger(KEY_COMPLETED));
		
		task.setNeedsUpdate(false);
		task.put(KEY_COMPLETED, i);
		
		assertEquals(true, task.needsUpdate());
		assertEquals(false, task.needsDelete());
	}
	
	public void testPutString(){
		Task task = new Task("What", mContext);
		String notes = "hello notes";
		task.put(KEY_NOTES, notes);
		assertEquals(notes, task.getAsString(KEY_NOTES));
		
		task.setNeedsUpdate(false);
		task.put(KEY_NOTES, notes);
		
		assertEquals(true, task.needsUpdate());
		assertEquals(false, task.needsDelete());
	}
	
	public void testPutBool(){
		Task task = new Task("What", mContext);
		
		assertEquals(0, task.getAsInteger(KEY_COMPLETED));
		task.put(KEY_COMPLETED, true);
		assertEquals(1, task.getAsInteger(KEY_COMPLETED));
		
		task.setNeedsUpdate(false);
		task.put(KEY_COMPLETED, true);
		
		assertEquals(true, task.needsUpdate());
		assertEquals(false, task.needsDelete());
	}
	
	public void testPutLong(){
		Task task = new Task("What", mContext);
		long time = System.currentTimeMillis();
		task.put(KEY_DUE_DATE, time);
		assertEquals(time, task.getAsLong(KEY_DUE_DATE));
		
		task.setNeedsUpdate(false);
		task.put(KEY_DUE_DATE, time);
		
		assertEquals(true, task.needsUpdate());
		assertEquals(false, task.needsDelete());
	}
	
	public void testGetAsBoolean(){
		Task task = new Task("What", mContext);
		
		assertEquals(true, task.getAsBoolean(_ID));
		assertEquals(true, task.getAsBoolean(KEY_WHAT));
		assertEquals(false, task.getAsBoolean(KEY_NOTES));
		assertEquals(false, task.getAsBoolean(KEY_COMPLETED));
		
		task.put(KEY_COMPLETED, -1000);
		assertEquals(false, task.getAsBoolean(KEY_COMPLETED));
		
		task.put(KEY_COMPLETED, -10000000000000l);
		assertEquals(false, task.getAsBoolean(KEY_COMPLETED));
		
		task.put(KEY_COMPLETED, 1000);
		assertEquals(true, task.getAsBoolean(KEY_COMPLETED));
		
		task.put(KEY_COMPLETED, 10000000000000l);
		assertEquals(true, task.getAsBoolean(KEY_COMPLETED));
	}
	
	public void testGetAsLong(){
		Task task = new Task("What", mContext);
		
		assertEquals(1, task.getAsLong(_ID));
		
		assertEquals(0, task.getAsLong(KEY_WHAT));
		assertEquals(0, task.getAsLong(KEY_NOTES));
		assertEquals(0, task.getAsLong(KEY_TASKS));
		assertEquals(0, task.getAsLong(KEY_COMPLETED));
		
		assertEquals(0, task.getAsLong(KEY_DUE_DATE));
		long time = System.currentTimeMillis();
		task.put(KEY_DUE_DATE, time);
		assertEquals(time, task.getAsLong(KEY_DUE_DATE));
		
	}
	
	public void testGetAsString(){
		String what = "what";
		Task task = new Task(what, mContext);
		
		assertEquals(what, task.getAsString(KEY_WHAT));
		assertEquals("", task.getAsString(KEY_NOTES));
		assertEquals("1", task.getAsString(_ID));
		assertEquals("", task.getAsString(KEY_COMPLETED));
	}
	
	public void testGetAsInteger(){
		Task task = new Task("What", mContext);
		
		assertEquals(1, task.getAsInteger(_ID));
		
		assertEquals(0, task.getAsInteger(KEY_WHAT));
		assertEquals(0, task.getAsInteger(KEY_NOTES));
		assertEquals(0, task.getAsInteger(KEY_TASKS));
		assertEquals(0, task.getAsInteger(KEY_COMPLETED));
		
		assertEquals(0, task.getAsLong(KEY_DUE_DATE));
		long l = -System.currentTimeMillis();
		task.put(KEY_DUE_DATE, l);
		assertTrue(task.getAsLong(KEY_DUE_DATE) < 1);
		

	}
	
}























