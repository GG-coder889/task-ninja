package taskninja.core.dbmodel.task.tests;

import taskninja.core.dbmodel.task.Task;
import taskninja.core.dbmodel.tasklist.TaskList;
import android.test.AndroidTestCase;

public class TaskListTest extends AndroidTestCase {
	@Override
    protected void setUp() throws Exception {
    	 super.setUp();
    	 TaskList.setContext(mContext);
    }
	
	public void testGetInsance() {
		TaskList list = TaskList.getInstance("Title");
	}
}
