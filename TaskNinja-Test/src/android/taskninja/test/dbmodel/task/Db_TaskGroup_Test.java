package android.taskninja.test.dbmodel.task;

import java.util.LinkedHashSet;

import android.taskninja.dbmodel.Db_Model;
import android.taskninja.task.dbtask.Db_Task;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup_String;
import android.test.AndroidTestCase;

public class Db_TaskGroup_Test extends AndroidTestCase {
	
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 Db_Model.setContext(mContext);
    }
	
	@Override
	protected void tearDown() {
		assertTrue(mContext.getSharedPreferences("Db_TaskGroup", 4).edit().clear().commit());
		assertTrue(mContext.getSharedPreferences("Db_TaskCollection", 4).edit().clear().commit());
		assertTrue(mContext.getSharedPreferences("Db_Task", 4).edit().clear().commit());
	}
	
	public void testGetInsance() {
		String title = "Title";
		Db_TaskGroup group = Db_TaskGroup.getInstance(title);
		assertEquals(title, group.getString(Db_TaskGroup_String.title));
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
	
	public void testToString() {
		String title = "Title";
		Db_TaskGroup list = Db_TaskGroup.getInstance(title);
		assertEquals(title, list.toString());
	}
	
	
}
