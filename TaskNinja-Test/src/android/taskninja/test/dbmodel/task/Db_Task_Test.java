package android.taskninja.test.dbmodel.task;

import android.taskninja.task.dbtask.Db_Task;
import android.test.AndroidTestCase;



public class Db_Task_Test extends AndroidTestCase {
	
	private static final String TAG = "TaskTest";
	
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 Db_Task.setContext(mContext);
    }
	
	public void testGetInstance() {
		Db_Task task = Db_Task.getInstance("Title");
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






















