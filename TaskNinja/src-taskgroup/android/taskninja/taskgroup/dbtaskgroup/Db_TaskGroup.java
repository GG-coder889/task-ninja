package android.taskninja.taskgroup.dbtaskgroup;

import java.util.LinkedHashSet;

import android.content.ContentValues;
import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.dbmodel.Db_NullEnum;

public class Db_TaskGroup extends Db_Model<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum, Db_NullEnum> {

	
	public static Db_TaskGroup getInstance(String title){
		return new Db_TaskGroup(title);
	}
	
	private Db_TaskGroup(String title){
		super();
		put(Db_TaskGroup_String.title, title);
	}
	
	public static Db_TaskGroup get(int id){
		return controller().get(id);
	}
	
	public static LinkedHashSet<Db_TaskGroup> getAll() {
		LinkedHashSet<Db_TaskGroup> tasks = new LinkedHashSet<Db_TaskGroup>();
		for (Db_TaskGroup task: controller().getAll()) {
			tasks.add(task);
		}
		return tasks;
	}
	
	@Override
	public String toString(){
		return getString(Db_TaskGroup_String.title);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Context mContext;
	private static Db_Controller<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum, Db_NullEnum> mController;
	
	
	@Override
	protected Db_Controller <Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum, Db_NullEnum> getController() {
		return controller();
	}
	
	private static Db_Controller<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum, Db_NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(Db_TaskGroup.class, App.getContext(), 1);
			} catch (Exception e1){
				try {
					mController = new LocalController(Db_TaskGroup.class, mContext, 1);
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
	
	private Db_TaskGroup(ContentValues values){
		super(values);
	}
	
	private static class LocalController extends Db_Controller<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum, Db_NullEnum> {
		
		public static Context mContext;
			
		protected LocalController(Class<Db_TaskGroup> dbModel, Context context, int version) {
			super(dbModel, context, version);
		}

		@Override
		protected Db_NullEnum[] getIntegerValues() {
			return Db_NullEnum.values();
		}

		@Override
		protected Db_TaskGroup_String[] getStringValues() {
			return Db_TaskGroup_String.values();
		}

		@Override
		protected Db_NullEnum[] getLongValues() {
			return Db_NullEnum.values();
		}

		@Override
		protected Db_NullEnum[] getIntegerListValues() {
			return Db_NullEnum.values();
		}

		@Override
		protected Db_NullEnum[] getBoolValues() {
			return Db_NullEnum.values();
		}

		@Override
		protected Db_TaskGroup getNewInstance(ContentValues values) {
			return new Db_TaskGroup(values);
		}
	}
	// ----------------------------------------------------------------------------------------------------
}
