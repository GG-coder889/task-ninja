package com.rocksolidmobility.taskninja.android.test;

import java.util.LinkedHashSet;

import android.test.AndroidTestCase;

import com.rocksolidmobility.android.rsmodel.RSModel;
import com.rocksolidmobility.taskninja.android.task.Task;
import com.rocksolidmobility.taskninja.android.taskgroup.TaskGroup;
import com.rocksolidmobility.taskninja.android.taskgroup.TaskGroupString;

public class TaskGroupTest extends AndroidTestCase {
	
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 RSModel.setContext(mContext);
    }
	
	@Override
	protected void tearDown() {
		assertTrue(mContext.getSharedPreferences("Db_TaskGroup", 4).edit().clear().commit());
		assertTrue(mContext.getSharedPreferences("Db_TaskCollection", 4).edit().clear().commit());
		assertTrue(mContext.getSharedPreferences("Db_Task", 4).edit().clear().commit());
	}
	
	public void testGetInsance() {
		String title = "Title";
		TaskGroup group = TaskGroup.getInstance(title);
		assertEquals(title, group.getString(TaskGroupString.title));
	}
	
	public void testGet(){
		TaskGroup list1 = TaskGroup.getInstance("Title1");
		TaskGroup list2 = TaskGroup.getInstance("Title2");
		
		assertSame(list1, TaskGroup.get(list1.getId()));
		assertSame(list2, TaskGroup.get(list2.getId()));
		assertNotSame(list1, TaskGroup.get(list2.getId()));
		assertNotSame(list2, TaskGroup.get(list1.getId()));
	}
	
	public void testGetAll(){
		TaskGroup list1 = TaskGroup.getInstance("Title1");
		TaskGroup list2 = TaskGroup.getInstance("Title2");
		
		LinkedHashSet<TaskGroup> lists = TaskGroup.getAll();
		
		assertTrue(lists.contains(list1));
		assertTrue(lists.contains(list2));
	}
	
	public void testToString() {
		String title = "Title";
		TaskGroup list = TaskGroup.getInstance(title);
		assertEquals(title, list.toString());
	}
	
	public void testList(){
		String groupTitle = "Group Title";
		TaskGroup group = TaskGroup.getInstance(groupTitle);
		
		String taskTitle = "Task Title";
		Task task = Task.getInstance(taskTitle);
		
		assertTrue(group.isEmpty());
		assertTrue(group.add(task));
		assertTrue(group.contains(task));
		assertFalse(group.add(task));
		assertEquals(0, group.indexOf(task));
		assertFalse(group.isEmpty());
		assertEquals(1, group.size());
		
	}
	
	
}
