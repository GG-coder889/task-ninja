package com.taskninjapro.android.test;

import android.test.AndroidTestCase;

import com.taskninjapro.android.app.Constants;

public class TaskDatabaseTest extends AndroidTestCase implements Constants {
	private static final String TAG = "DatabaseInterfaceTest";
	
//	private TaskSQLiteHelper mTaskSQLiteHelper;
//	TaskDatabase mTaskDatabase;
//	
//	SharedPreferences mSettings;
//	
//	@Override
//    protected void setUp() throws Exception {
//    	 super.setUp();
//    	 mTaskSQLiteHelper = TaskSQLiteHelper.getInstance(getContext());
//    	 SQLiteDatabase db = mTaskSQLiteHelper.getWritableDatabase();
//    	 db.delete(TASKS_TABLE, null, null);
//    	 db.close();
//
//    	 mTaskDatabase = TaskDatabase.getInstance(getContext());
//    	 TaskDatabase.mTasks = new HashMap<Integer, Task>();
//    	 TaskDatabase.mIds = new LinkedList<Integer>();	 
//    	 
//    	 mSettings = mContext.getSharedPreferences(PREFS, PREFS_MODE);
//    	 mSettings.edit().clear().commit();
//    }
//	
//	@Override
//	protected void tearDown(){
//		SQLiteDatabase db = mTaskSQLiteHelper.getWritableDatabase();
//		db.delete(TASKS_TABLE, null, null);
//   	 	db.close();
//   	 	mSettings.edit().clear().commit();
//	}
//	
//	public void testGetInstance(){
//		assertEquals(mTaskDatabase, TaskDatabase.getInstance(getContext()));
//	}
//	
//	public void testAdd(){
//		assertEquals(0, TaskDatabase.mTasks.size());
//		
//		ContentValues values = new ContentValues();
//		int id = 1;
//		values.put(_ID, id);
//		Task task = new Task(values, mContext);
//		mTaskDatabase.add(task);
//		assertEquals(1, TaskDatabase.mTasks.size());
//		
//		Task task2 = TaskDatabase.mTasks.get(id);
//		assertSame(task, task2);
//	}
//	
//	public void testGetNewID(){
//		assertEquals(1, mTaskDatabase.getNewID());
//		assertEquals(2, mTaskDatabase.getNewID());
//		assertEquals(3, mTaskDatabase.getNewID());
//		assertEquals(4, mTaskDatabase.getNewID());
//		
//		TaskDatabase.mIds.add(100);
//		
//		assertEquals(99, mTaskDatabase.getNewID());
//		assertEquals(98, mTaskDatabase.getNewID());
//		assertEquals(97, mTaskDatabase.getNewID());
//		assertEquals(96, mTaskDatabase.getNewID());
//	}
//	
//	public void testGetTask(){
//		Task task1 = new Task("Hello World 1", mContext);
//		Task task2 = new Task("Hello World 2", mContext);
//		Task task3 = new Task("Hello World 3", mContext);
//		Task task4 = new Task("Hello World 4", mContext);
//		
//		assertSame(task1, mTaskDatabase.getTask(task1.getAsInteger(_ID)));
//		assertSame(task2, mTaskDatabase.getTask(task2.getAsInteger(_ID)));
//		assertSame(task3, mTaskDatabase.getTask(task3.getAsInteger(_ID)));
//		assertSame(task4, mTaskDatabase.getTask(task4.getAsInteger(_ID)));
//		
//		assertNotSame(task4, mTaskDatabase.getTask(task1.getId()));
//	}
//	
//	public void testGetCurrentTask(){
//		Task task1 = new Task("Hello World 1", mContext);
//		Task task2 = new Task("Hello World 2", mContext);
//		Task task3 = new Task("Hello World 3", mContext);
//		Task task4 = new Task("Hello World 4", mContext);
//		
//		LinkedHashSet<Integer> inputIds = new LinkedHashSet<Integer>();
//		inputIds.add(task1.getAsInteger(_ID));
//		inputIds.add(task2.getAsInteger(_ID));
//		inputIds.add(task3.getAsInteger(_ID));
//		inputIds.add(task4.getAsInteger(_ID));
//		
//		mTaskDatabase.setQueue(inputIds);
//		
////		LinkedHashSet<Task> queue = new LinkedHashSet<Task>();
////		queue.add(task1);
////		queue.add(task2);
////		queue.add(task3);
////		queue.add(task4);
////		
////		mTaskDatabase.setQueue(queue);
//		
////		queue = mTaskDatabase.getQueue();
////		assertTrue(queue.contains(task1));
////		assertTrue(queue.contains(task2));
////		assertTrue(queue.contains(task3));
////		assertTrue(queue.contains(task4));
//		
//		assertSame(task1, mTaskDatabase.getCurrentTask());
//		
//		task1.put(KEY_COMPLETED, true);
//		assertSame(task2, mTaskDatabase.getCurrentTask());
//		
//		task2.put(KEY_COMPLETED, true);
//		assertSame(task3, mTaskDatabase.getCurrentTask());
//		
//		task3.put(KEY_COMPLETED, true);
//		assertSame(task4, mTaskDatabase.getCurrentTask());
//		
//		task4.put(KEY_COMPLETED, true);
//		assertSame(null, mTaskDatabase.getCurrentTask());
//		
//		task1.put(KEY_COMPLETED, false);
//		assertSame(task1, mTaskDatabase.getCurrentTask());
//		
//	}
//	
//	public void testGetQueue(){
//		Task task1 = new Task("Hello World 1", mContext);
//		Task task2 = new Task("Hello World 2", mContext);
//		Task task3 = new Task("Hello World 3", mContext);
//		Task task4 = new Task("Hello World 4", mContext);
//		
//		LinkedHashSet<Task> queue = new LinkedHashSet<Task>();
//		queue.add(task1);
//		queue.add(task2);
//		queue.add(task3);
//		queue.add(task4);
//		
////		mTaskDatabase.setQueue(queue);
////		
////		queue = mTaskDatabase.getQueue();
//		
//		assertEquals(queue.size(), 4);
//		assertTrue(queue.contains(task1));
//		assertTrue(queue.contains(task2));
//		assertTrue(queue.contains(task3));
//		assertTrue(queue.contains(task4));
//		
////		queue = mTaskDatabase.getQueue();
////		
////		
////		assertSame(task1, queue.remove(0));
////		assertSame(task2, queue.remove(0));
////		assertSame(task3, queue.remove(0));
////		assertSame(task4, queue.remove(0));
//	}
//	
//	public void testSetQueue(){
//		Task task1 = new Task("Hello World 1", mContext);
//		Task task2 = new Task("Hello World 2", mContext);
//		Task task3 = new Task("Hello World 3", mContext);
//		Task task4 = new Task("Hello World 4", mContext);
//		
//		String queueString = task1.getId()+","+task2.getId()+","+
//			task3.getId()+","+task4.getId();
//		
//		LinkedHashSet<Task> queue = new LinkedHashSet<Task>();
//		queue.add(task1);
//		queue.add(task2);
//		queue.add(task3);
//		queue.add(task4);
//		
////		mTaskDatabase.setQueue(queue);
////		
////		assertEquals(queueString, mSettings.getString(QUEUE, null));
////		
////		queue = mTaskDatabase.getQueue();
//		assertTrue(queue.contains(task1));
//		assertTrue(queue.contains(task2));
//		assertTrue(queue.contains(task3));
//		assertTrue(queue.contains(task4));
//	}
//	
//	public void testGetTasks(){
//		Task task1 = new Task("Hello World 1", mContext);
//		Task task2 = new Task("Hello World 2", mContext);
//		Task task3 = new Task("Hello World 3", mContext);
//		Task task4 = new Task("Hello World 4", mContext);
//		
//		List<Task> tasks = mTaskDatabase.getTasks();
//		
//		assertEquals(tasks.size(), 4);
//		assertSame(task1, tasks.remove(0));
//		assertSame(task2, tasks.remove(0));
//		assertSame(task3, tasks.remove(0));
//		assertSame(task4, tasks.remove(0));
//		
//		Task sub = new Task("Hello subtask", mContext);
//		task1.addSubtask(sub);
//		
//		tasks = mTaskDatabase.getTasks();
//		
//		assertEquals(tasks.size(), 4);
//		assertSame(task1, tasks.remove(0));
//		assertSame(task2, tasks.remove(0));
//		assertSame(task3, tasks.remove(0));
//		assertSame(task4, tasks.remove(0));
//		assertSame(sub, mTaskDatabase.getTask(sub.getId()));
//	}
	


}
