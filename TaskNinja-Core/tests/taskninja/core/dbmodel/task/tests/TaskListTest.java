package taskninja.core.dbmodel.task.tests;

import java.util.LinkedHashSet;

import android.taskninja.core.dbmodel.task.Task;
import android.taskninja.core.dbmodel.tasklist.TaskList;
import android.test.AndroidTestCase;

public class TaskListTest extends AndroidTestCase {
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 TaskList.setContext(mContext);
    }
	
	@Override
	protected void tearDown() {

		for (TaskList list: TaskList.getAll()){
			list.delete();
		}
		
	}
	
	public void testGetInsance() {
		TaskList list = TaskList.getInstance("Title");
	}
	
	public void testGet(){
		TaskList list1 = TaskList.getInstance("Title1");
		TaskList list2 = TaskList.getInstance("Title2");
		
		assertSame(list1, TaskList.get(list1.getId()));
		assertSame(list2, TaskList.get(list2.getId()));
		assertNotSame(list1, TaskList.get(list2.getId()));
		assertNotSame(list2, TaskList.get(list1.getId()));
	}
	
	public void testGetAll(){
		TaskList list1 = TaskList.getInstance("Title1");
		TaskList list2 = TaskList.getInstance("Title2");
		
		LinkedHashSet<TaskList> lists = TaskList.getAll();
		
		assertTrue(lists.contains(list1));
		assertTrue(lists.contains(list2));
	}
	
	public void testToString(){
		String title = "Title";
		TaskList list = TaskList.getInstance(title);
		assertEquals(title, list.toString());
	}
	
	
}
