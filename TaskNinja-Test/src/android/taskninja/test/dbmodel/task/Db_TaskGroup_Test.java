package android.taskninja.test.dbmodel.task;

import java.util.LinkedHashSet;

import android.taskninja.task.dbtask.Db_Task;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup;
import android.test.AndroidTestCase;

public class Db_TaskGroup_Test extends AndroidTestCase {
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 Db_TaskGroup.setContext(mContext);
    }
	
	@Override
	protected void tearDown() {
		assertTrue(mContext.getSharedPreferences("Db_TaskGroup", 4).edit().clear().commit());		
	}
	
	public void testGetInsance() {
		Db_TaskGroup list = Db_TaskGroup.getInstance("Title");
	}
	
	public void testGet(){
		Db_TaskGroup list1 = Db_TaskGroup.getInstance("Title1");
		Db_TaskGroup list2 = Db_TaskGroup.getInstance("Title2");
		
		assertSame(list1, Db_TaskGroup.get(list1.getId()));
		assertSame(list2, Db_TaskGroup.get(list2.getId()));
		assertNotSame(list1, Db_TaskGroup.get(list2.getId()));
		assertNotSame(list2, Db_TaskGroup.get(list1.getId()));
	}
	
	public void testGetAll(){
		Db_TaskGroup list1 = Db_TaskGroup.getInstance("Title1");
		Db_TaskGroup list2 = Db_TaskGroup.getInstance("Title2");
		
		LinkedHashSet<Db_TaskGroup> lists = Db_TaskGroup.getAll();
		
		assertTrue(lists.contains(list1));
		assertTrue(lists.contains(list2));
	}
	
	public void testToString(){
		String title = "Title";
		Db_TaskGroup list = Db_TaskGroup.getInstance(title);
		assertEquals(title, list.toString());
	}
	
	
}
