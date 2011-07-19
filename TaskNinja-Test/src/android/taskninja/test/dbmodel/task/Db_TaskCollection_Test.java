package android.taskninja.test.dbmodel.task;

import android.taskninja.taskcollection.dbtaskcollection.Db_TaskCollection;
import android.test.AndroidTestCase;

public class Db_TaskCollection_Test extends AndroidTestCase {
	
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 Db_TaskCollection.setContext(mContext);
    }
	
//	public void testGetInstance() {
//		Db_TaskCollection group = Db_TaskCollection.getInstance();
//	}
}
