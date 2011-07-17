package taskninja.core.dbmodel.tasklist;

import java.util.LinkedHashSet;

import taskninja.core.app.App;
import taskninja.core.dbmodel.DbController;
import taskninja.core.dbmodel.DbModel;
import taskninja.core.dbmodel.NullEnum;
import taskninja.core.dbmodel.task.Task;
import android.content.ContentValues;
import android.content.Context;

public class TaskList extends DbModel<TaskList, NullEnum, NullEnum, TaskListString, NullEnum, NullEnum> {

	
	public static TaskList get(int id){
		return controller().get(id);
	}
	
	public static LinkedHashSet<TaskList> getAll() {
		LinkedHashSet<TaskList> tasks = new LinkedHashSet<TaskList>();
		for (TaskList task: controller().getAll()) {
			tasks.add(task);
		}
		return tasks;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Context mContext;
	private static DbController<TaskList, NullEnum, NullEnum, TaskListString, NullEnum, NullEnum> mController;
	
	
	@Override
	protected DbController <TaskList, NullEnum, NullEnum, TaskListString, NullEnum, NullEnum> getController() {
		return controller();
	}
	
	private static DbController<TaskList, NullEnum, NullEnum, TaskListString, NullEnum, NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(TaskList.class, App.getContext(), 1);
			} catch (Exception e1){
				try {
					mController = new LocalController(TaskList.class, mContext, 1);
				}catch (Exception e2){
					e2.printStackTrace();
					return null;
				}
			}
		}
		return mController;
	}
	
	public static void setContext(Context context){
		LocalController.mContext = context;
	}
	
	private TaskList(ContentValues values){
		super(values);
	}
	
	private static class LocalController extends DbController<TaskList, NullEnum, NullEnum, TaskListString, NullEnum, NullEnum> {
		
		public static Context mContext;
			
		protected LocalController(Class<TaskList> dbModel, Context context, int version) {
			super(dbModel, context, version);
		}

		@Override
		protected NullEnum[] getIntegerValues() {
			return NullEnum.values();
		}

		@Override
		protected TaskListString[] getStringValues() {
			return TaskListString.values();
		}

		@Override
		protected NullEnum[] getLongValues() {
			return NullEnum.values();
		}

		@Override
		protected NullEnum[] getIntegerListValues() {
			return NullEnum.values();
		}

		@Override
		protected NullEnum[] getBoolValues() {
			return NullEnum.values();
		}

		@Override
		protected TaskList getNewInstance(ContentValues values) {
			return new TaskList(values);
		}
	}
	// ----------------------------------------------------------------------------------------------------
}