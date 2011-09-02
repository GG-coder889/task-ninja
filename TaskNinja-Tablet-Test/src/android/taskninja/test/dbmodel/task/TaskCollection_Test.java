package android.taskninja.test.dbmodel.task;

import android.taskninja.dbmodel.Db_Model;
import android.taskninja.taskcollection.TaskCollection;
import android.test.AndroidTestCase;

public class TaskCollection_Test extends AndroidTestCase {
	
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
	
	public void testGetInstance() {
		TaskCollection group = TaskCollection.getInstance();
	}
}
