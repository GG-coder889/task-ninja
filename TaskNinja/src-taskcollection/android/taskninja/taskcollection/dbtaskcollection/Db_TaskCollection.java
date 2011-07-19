package android.taskninja.taskcollection.dbtaskcollection;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.dbmodel.Db_NullEnum;

public class Db_TaskCollection extends Db_Model {

	public static Db_TaskCollection get(String id) {
		return controller().get(id);
	}
	
	private Db_TaskCollection() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Context mContext;
	private static Db_Controller<Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum> mController;
	
	@Override
	protected Db_Controller <Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum> getController() {
		return controller();
	}
	
	private static Db_Controller<Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(Db_TaskCollection.class, App.getContext());
			} catch (Exception e1){
				try {
					mController = new LocalController(Db_TaskCollection.class, mContext);
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

	public Db_TaskCollection(JSONObject json) {
		super(json);
	}

	private static class LocalController extends Db_Controller<Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum> {
		
		public static Context mContext;
			
		protected LocalController(Class<Db_TaskCollection> dbModel, Context context) {
			super(dbModel, context);
		}

		@Override
		protected Db_TaskCollection getInstance(JSONObject json) {
			return new Db_TaskCollection(json);
		}

	}
	// ----------------------------------------------------------------------------------------------------



}
