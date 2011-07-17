package android.taskninja.core.dbmodel.taskgroup;

import android.content.ContentValues;
import android.content.Context;
import android.taskninja.core.app.App;
import android.taskninja.core.dbmodel.DbController;
import android.taskninja.core.dbmodel.DbModel;
import android.taskninja.core.dbmodel.NullEnum;

public class TaskGroup extends DbModel {

	public static TaskGroup get(int id) {
		return controller().get(id);
	}
	
	public static TaskGroup getInstance(){
		return new TaskGroup();
	}
	
	private TaskGroup() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Context mContext;
	private static DbController<TaskGroup, NullEnum, NullEnum, TaskGroupString, NullEnum, NullEnum> mController;
	
	@Override
	protected DbController <TaskGroup, NullEnum, NullEnum, TaskGroupString, NullEnum, NullEnum> getController() {
		return controller();
	}
	
	private static DbController<TaskGroup, NullEnum, NullEnum, TaskGroupString, NullEnum, NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(TaskGroup.class, App.getContext(), 1);
			} catch (Exception e1){
				try {
					mController = new LocalController(TaskGroup.class, mContext, 1);
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
	
	private TaskGroup(ContentValues values){
		super(values);
	}

	private static class LocalController extends DbController<TaskGroup, NullEnum, NullEnum, TaskGroupString, NullEnum, NullEnum> {
		
		public static Context mContext;
			
		protected LocalController(Class<TaskGroup> dbModel, Context context, int version) {
			super(dbModel, context, version);
		}

		@Override
		protected NullEnum[] getIntegerValues() {
			return NullEnum.values();
		}

		@Override
		protected TaskGroupString[] getStringValues() {
			return TaskGroupString.values();
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
		protected TaskGroup getNewInstance(ContentValues values) {
			return new TaskGroup(values);
		}


	
	}
	// ----------------------------------------------------------------------------------------------------



}
