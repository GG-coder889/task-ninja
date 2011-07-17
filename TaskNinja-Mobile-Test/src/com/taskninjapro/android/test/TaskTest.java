package com.taskninjapro.android.test;

import java.util.LinkedHashSet;

import android.test.AndroidTestCase;

import com.taskninjapro.android.Task.Task;
import com.taskninjapro.android.app.Constants;



public class TaskTest extends AndroidTestCase implements Constants {
	
	private static final String TAG = "TaskTest";
	
	private static final String WHAT = "Hello what";
	
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 Task.setContext(mContext);
    }
	
	public void testConstructor() {
		String what = "Hello What";
		Task task = new Task(what);
		assertEquals(what, task.getWhat());
	}
	
	public void testParentChild(){
		Task parent = new Task(WHAT);
		Task child1 = new Task(WHAT);
		Task child2 = new Task(WHAT);
		
		parent.addChild(child1);
		parent.addChild(child2);
		
		LinkedHashSet<Task> children = parent.getChildren();
		
		assertEquals(2, children.size());
		assertTrue(children.contains(child1));
		assertTrue(children.contains(child2));
		assertEquals(parent, child1.getParent());
		
		parent.removeChild(child1);
		
		assertFalse(children.contains(child1));
		assertTrue(children.contains(child2));
		
		child2.delete();
		
		assertFalse(children.contains(child2));
		
	}
	
	public void testEquals(){
		
	}
	
}























