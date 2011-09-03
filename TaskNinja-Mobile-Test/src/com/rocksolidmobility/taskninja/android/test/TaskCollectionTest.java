package com.rocksolidmobility.taskninja.android.test;

import android.test.AndroidTestCase;

import com.rocksolidmobility.android.rsmodel.RSModel;
import com.rocksolidmobility.taskninja.android.taskcollection.TaskCollection;

public class TaskCollectionTest extends AndroidTestCase {
	
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
	
	public void testGetInstance() {
		TaskCollection group = TaskCollection.getInstance();
	}
}
