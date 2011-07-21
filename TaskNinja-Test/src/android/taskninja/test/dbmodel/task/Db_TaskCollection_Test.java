package android.taskninja.test.dbmodel.task;

import android.taskninja.dbmodel.Db_Model;
import android.taskninja.taskcollection.dbtaskcollection.Db_TaskCollection;
import android.test.AndroidTestCase;

public class Db_TaskCollection_Test extends AndroidTestCase {
	
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
		Db_TaskCollection group = Db_TaskCollection.getInstance();
	}
}
