package com.rocksolidmobility.taskninja.android.test;

import android.test.AndroidTestCase;

import com.rocksolidmobility.taskninja.android.task.Task;



public class TaskTest extends AndroidTestCase {
	
	private static final String TAG = "TaskTest";
	
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 Task.setContext(mContext);
    }
	
	@Override
	protected void tearDown() {
		assertTrue(mContext.getSharedPreferences("TnTaskGroup", 4).edit().clear().commit());
		assertTrue(mContext.getSharedPreferences("TnTaskCollection", 4).edit().clear().commit());
		assertTrue(mContext.getSharedPreferences("TnTask", 4).edit().clear().commit());
	}
	
	public void testGetInstance() {
		Task task = Task.getInstance("Title");
	}
	
//	public void testParentChild(){
//		Task parent = new Task(WHAT);
//		Task child1 = new Task(WHAT);
//		Task child2 = new Task(WHAT);
//		
//		parent.addChild(child1);
//		parent.addChild(child2);
//		
//		LinkedHashSet<Task> children = parent.getChildren();
//		
//		assertEquals(2, children.size());
//		assertTrue(children.contains(child1));
//		assertTrue(children.contains(child2));
//		assertEquals(parent, child1.getParent());
//		
//		parent.removeChild(child1);
//		
//		assertFalse(children.contains(child1));
//		assertTrue(children.contains(child2));
//		
//		child2.delete();
//		
//		assertFalse(children.contains(child2));
//		
//	}
//	
//	public void testEquals(){
//		
//	}
	
}























