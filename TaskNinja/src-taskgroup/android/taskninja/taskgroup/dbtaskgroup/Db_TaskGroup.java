package android.taskninja.taskgroup.dbtaskgroup;

import java.util.LinkedHashSet;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.dbmodel.Db_NullEnum;

public class Db_TaskGroup extends Db_Model<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum> {

	
	public static Db_TaskGroup getInstance(String title){
		return new Db_TaskGroup(title);
	}
	
	private Db_TaskGroup(String title){
		super();
		put(Db_TaskGroup_String.title, title);
	}
	
	public static Db_TaskGroup get(String id){
		return controller().get(id);
	}
	
	public static LinkedHashSet<Db_TaskGroup> getAll() {
		return controller().getAll();
	}
	
	@Override
	public String toString(){
		return getString(Db_TaskGroup_String.title);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Context mContext;
	private static Db_Controller<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum> mController;
	
	
	@Override
	protected Db_Controller <Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum> getController() {
		return controller();
	}
	
	private static Db_Controller<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(Db_TaskGroup.class, App.getContext());
			} catch (Exception e1){
				try {
					mController = new LocalController(Db_TaskGroup.class, mContext);
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
	
	public Db_TaskGroup(JSONObject json) {
		super(json);
	}

	private static class LocalController extends Db_Controller<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum> {
		
		public static Context mContext;
			
		protected LocalController(Class<Db_TaskGroup> dbModel, Context context) {
			super(dbModel, context);
		}

		@Override
		protected Db_TaskGroup getInstance(JSONObject json) {
			return new Db_TaskGroup(json);
		}

	}
	// ----------------------------------------------------------------------------------------------------
}
