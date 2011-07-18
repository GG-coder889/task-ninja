package android.taskninja.taskcollection.dbtaskcollection;

import android.content.ContentValues;
import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.dbmodel.Db_NullEnum;

public class Db_TaskCollection extends Db_Model {

	public static Db_TaskCollection get(int id) {
		return controller().get(id);
	}
	
	public static Db_TaskCollection getInstance(){
		return new Db_TaskCollection();
	}
	
	private Db_TaskCollection() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Context mContext;
	private static Db_Controller<Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum, Db_NullEnum> mController;
	
	@Override
	protected Db_Controller <Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum, Db_NullEnum> getController() {
		return controller();
	}
	
	private static Db_Controller<Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum, Db_NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(Db_TaskCollection.class, App.getContext(), 1);
			} catch (Exception e1){
				try {
					mController = new LocalController(Db_TaskCollection.class, mContext, 1);
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
	
	private Db_TaskCollection(ContentValues values){
		super(values);
	}

	private static class LocalController extends Db_Controller<Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum, Db_NullEnum> {
		
		public static Context mContext;
			
		protected LocalController(Class<Db_TaskCollection> dbModel, Context context, int version) {
			super(dbModel, context, version);
		}

		@Override
		protected Db_NullEnum[] getIntegerValues() {
			return Db_NullEnum.values();
		}

		@Override
		protected Db_TaskCollection_String[] getStringValues() {
			return Db_TaskCollection_String.values();
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
		protected Db_TaskCollection getNewInstance(ContentValues values) {
			return new Db_TaskCollection(values);
		}


	
	}
	// ----------------------------------------------------------------------------------------------------



}
