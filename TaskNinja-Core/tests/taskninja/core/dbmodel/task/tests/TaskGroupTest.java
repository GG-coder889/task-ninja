package taskninja.core.dbmodel.task.tests;

import taskninja.core.dbmodel.taskgroup.TaskGroup;
import android.test.AndroidTestCase;

public class TaskGroupTest extends AndroidTestCase {
	
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 TaskGroup.setContext(mContext);
    }
	
	public void testGetInstance() {
		TaskGroup group = TaskGroup.getInstance();
	}
}
