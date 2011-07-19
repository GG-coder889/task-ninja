package android.taskninja.dbmodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public abstract class Db_Controller
	<MODEL extends Db_Model,
	INTEGER extends Enum<INTEGER>,
	LONG extends Enum<LONG>,
	STRING extends Enum<STRING>,
	BOOL extends Enum<BOOL>
	>{
	
	private static final String TAG = "Db_Controller";
	
	protected abstract MODEL getInstance(JSONObject json);
	
	private String mLocalTag;
	
	private SharedPreferences db;
	private Context mContext;
	private String mModelName;
	
	public Db_Controller (Class<MODEL> modelClass, Context context) {
		mModelName = modelClass.getSimpleName();
		mLocalTag = tagify(mModelName);
		db = context.getSharedPreferences(mModelName, Context.MODE_MULTI_PROCESS);
		loadIds();
	}

	private enum Local {
		IDS
	}
	
	public static String tagify(String s){
		return "[ "+s+" ] ";
	}
	
	// ----------------------------------------------------------------------------------------------------
	// Update
	// ----------------------------------------------------------------------------------------------------
	private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
	private Set<String> mDeleteKeys = new HashSet<String>();
	private HashMap<String, String> mUpdatePool = new HashMap<String, String>();
	
	public void update(MODEL model){
		Log.d(TAG, mLocalTag+tagify("update")+model.getData());
		
		String key = model.getId().toString();
		String value = model.getData();
		
		if (model.isDeleted()){
			delete(key);
		} else {
			update(key, value);
		}
	}
	
	private void delete(String key){
		Log.d(TAG, mLocalTag+tagify("delete")+key);
		
		mDeleteKeys.add(key);
		update(false);
	}
	
	private void update(String key, String value){
		Log.d(TAG, mLocalTag+tagify("update")+key+" "+value);
		
		mUpdatePool.put(key, value);
		update(false);
	}
	
	private synchronized void update(boolean force) {
		Log.d(TAG, mLocalTag+tagify("update")+"force="+force);
		
//		if (mExecutor.isShutdown() || force) {
			if (!(mUpdatePool.size() + mDeleteKeys.size() == 0)){
				Log.d(TAG, mLocalTag+tagify("update")+"update allowed");
				
				UpdateTask task = new UpdateTask(mUpdatePool, mDeleteKeys);
				mUpdatePool = new HashMap<String, String>();
				mDeleteKeys = new HashSet<String>();
				mExecutor.execute(task);
			} else {
				Log.d(TAG, mLocalTag+tagify("update")+"update not allowed");
			}
//		} else {
//			Log.d(TAG, mLocalTag+tagify("update")+"update not allowed");
//		}
	}
	
	private class UpdateTask implements Runnable {
		
		private SharedPreferences.Editor mEditor = db.edit();
		private HashMap<String, String> mUpdateTaskPool;
		private Set<String> mTaskDeleteKeys;
		
		private UpdateTask(HashMap<String, String> updatePool, Set<String> deleteKeys) {
			Log.d(TAG, mLocalTag+tagify("UpdateTask"));
			
			this.mUpdateTaskPool = updatePool;
			this.mTaskDeleteKeys = deleteKeys;
		}

		@Override
		public void run() {
			Log.d(TAG, mLocalTag+tagify("UpdateTask")+tagify("run")+"start");
			
			for (String key: this.mUpdateTaskPool.keySet()){
				String value = this.mUpdateTaskPool.get(key);
				this.mEditor.putString(key, value);
			}
			
			for (String key: this.mTaskDeleteKeys){
				this.mEditor.remove(key);
			}
			
			this.mEditor.apply();
			
			Log.d(TAG, mLocalTag+tagify("UpdateTask")+tagify("run")+"complete");
			
			update(true);
			
		}
		
	}
	// ----------------------------------------------------------------------------------------------------

	
	
	// ----------------------------------------------------------------------------------------------------
	// Get
	// ----------------------------------------------------------------------------------------------------
	public MODEL get(String id){
		Log.d(TAG, tagify("get")+"id="+id);
		
		if (id == null) {
			return null;
		}
		
		MODEL result = null;
		
		result = mModelPool.get(id);
		
		if (result == null && db.contains(id.toString())){
			String json = db.getString(id.toString(), null);
			try {
				result = getInstance(new JSONObject(json));
				add(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (result == null) {
			mIdPool.remove(id);
		} else if (result.isDeleted()) {
			result = null;
		}
		
		return result;
	}
	
	public LinkedHashSet<MODEL> getAll(){
		Log.d(TAG, "[ getAll ]");
		
		LinkedHashSet<MODEL> list = new LinkedHashSet<MODEL>();
		for (String id: mIdPool){
			MODEL model = get(id);
			if (model != null && !model.isDeleted()){
				list.add(model);
			}
		}
		
		return list;
	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	// ----------------------------------------------------------------------------------------------------
	// Model Pool Management
	// ----------------------------------------------------------------------------------------------------
	private final HashMap<String, MODEL> mModelPool = new HashMap<String, MODEL>();
	private final Set<String> mIdPool = new HashSet<String>();
	
	public void add(MODEL model){
		Log.d(TAG, "[ add ] model="+model.getData());
		
		if (model != null){
			if (!model.isDeleted()){
				mModelPool.put(model.getId(), model);
			} else {
				mModelPool.remove(model.getId());
				update(model);
			}
		}
	}
	
	public void remove(MODEL model){
		Log.d(TAG, "[ remove ] model="+model.getData());
		
		if (model != null) {
			mModelPool.remove(model.getId());
		}
	}
	
	public int getNewId() {
		Log.d(TAG, "[ getNewId ]");
		
		if (mIdPool.size() == 0){
			loadIds();
		}
		Integer i = 1;
		while (!mIdPool.add(i.toString())){
			i++;
		}
		updateIds();
		return i;
	}
	
	
	private void loadIds(){
		Log.d(TAG, mLocalTag+tagify("loadIds"));
		
		String string = db.getString(Local.IDS.name(), "");
		String[] strings = string.split(",");
		for (String s: strings ){
			if (! s.isEmpty()){
				mIdPool.add(s);
			}
		}
		
		Log.d(TAG, mLocalTag+tagify("loadIds")+" ids="+mIdPool.toArray().toString());
	}
	
	private void updateIds(){
		Log.d(TAG, mLocalTag+tagify("updateIds"));
		
		StringBuilder sb = new StringBuilder();
		
		for (String id: mIdPool){
			sb.append(id);
			sb.append(',');
		}
		
		if (sb.length() != 0){
			String key = Local.IDS.toString();
			String value = sb.substring(0, sb.length()-1);
			
			update(key, value);
		}
		
	}
	// ----------------------------------------------------------------------------------------------------
}






















