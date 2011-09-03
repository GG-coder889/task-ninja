package com.rocksolidmobility.android.rsmodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

@SuppressWarnings("rawtypes")
public abstract class RSController
	<MODEL extends RSModel,
	INTEGER extends Enum<INTEGER>,
	LONG extends Enum<LONG>,
	STRING extends Enum<STRING>,
	BOOL extends Enum<BOOL>
	>{
	
	
	protected abstract MODEL getInstance(JSONObject json);
	
	private String mLocalTag;
	
	private SharedPreferences db;
//	private Context mContext;
	private String mModelName;
	
	public RSController (Class<MODEL> modelClass, Context context) {
		mModelName = modelClass.getSimpleName();
		mLocalTag = mModelName;
		db = context.getSharedPreferences(mModelName, Context.MODE_PRIVATE);
		loadIds();
	}

	private enum Local {
		IDS
	}
	
	// ----------------------------------------------------------------------------------------------------
	// Update
	// ----------------------------------------------------------------------------------------------------
	private AsyncUpdate mAsyncUpdate = null;
	private Set<String> mDeleteKeys = new HashSet<String>();
	private HashMap<String, String> mUpdatePool = new HashMap<String, String>();
	
	public void update(MODEL model){
		RSLog.d(this.getClass(), model.getData().toString(), mLocalTag, "update");
		
		String key = model.getId().toString();
		String value = model.getData();
		
		if (model.isDeleted()){
			delete(key);
		} else {
			update(key, value);
		}
	}
	
	private void delete(String key){
		RSLog.d(this.getClass(), key, mLocalTag, "delete");
		
		mDeleteKeys.add(key);
		update();
	}
	
	private void update(String key, String value){
		RSLog.d(this.getClass(), key+" "+value, mLocalTag, "update");
		
		mUpdatePool.put(key, value);
		update();
	}
	
	private synchronized void update() {
		RSLog.d(this.getClass(), "", mLocalTag, "update");
		
		if (mAsyncUpdate == null  && mUpdatePool.size() + mDeleteKeys.size() > 0){
			RSLog.d(getClass(), "update allowed", mLocalTag, "update");
			
			UpdatePackage pkg = new UpdatePackage();
			
			pkg.mTaskDeleteKeys = mDeleteKeys;
			pkg.mUpdateTaskPool = mUpdatePool;
			
			mUpdatePool = new HashMap<String, String>();
			mDeleteKeys = new HashSet<String>();
			
			AsyncUpdate mAsyncUpdate = new AsyncUpdate();
			mAsyncUpdate.execute(pkg);
			
		} else {
			RSLog.d(getClass(), "update denied", mLocalTag, "update");
		}
			

	}
	
	private class UpdatePackage {
		HashMap<String, String> mUpdateTaskPool; 
		Set<String> mTaskDeleteKeys;
		SharedPreferences.Editor mEditor = db.edit();
	}
	
	private class AsyncUpdate extends AsyncTask<UpdatePackage, Void, UpdatePackage> {

		@Override
		protected UpdatePackage doInBackground(UpdatePackage... updatePackages) {
			RSLog.d(getClass(), "start", mLocalTag, "UpdateTask", "run");
			
			if (updatePackages.length != 0){
				UpdatePackage pkg = updatePackages[0];
						
				for (String key: pkg.mUpdateTaskPool.keySet()){
					String value = pkg.mUpdateTaskPool.get(key);
					pkg.mEditor.putString(key, value);
				}
				
				for (String key: pkg.mTaskDeleteKeys){
					pkg.mEditor.remove(key);
				}
				
				pkg.mEditor.commit();
				
				return pkg;
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(UpdatePackage pkg) {
			RSLog.d(getClass(), "complete", mLocalTag, "UpdateTask", "run");
			
			mAsyncUpdate =  null;
			update();
		}
		
	}
	// ----------------------------------------------------------------------------------------------------

	
	
	// ----------------------------------------------------------------------------------------------------
	// Get
	// ----------------------------------------------------------------------------------------------------
	public MODEL get(String id){
		RSLog.d(getClass(), "id="+id, mLocalTag, "get");
		
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
		RSLog.d(getClass(), "", mLocalTag, "getAll");
		
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
		RSLog.d(getClass(), "model="+model.getData(), mLocalTag, "add");
		
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
		RSLog.d(getClass(), "model="+model.getData(), mLocalTag, "remove");
		
		if (model != null) {
			mModelPool.remove(model.getId());
		}
	}
	
	public int getNewId() {
		RSLog.d(getClass(), "", mLocalTag, "getNewId");
		
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
		RSLog.d(getClass(), "", mLocalTag, "loadIds");
		
		String string = db.getString(Local.IDS.name(), "");
		String[] strings = string.split(",");
		for (String s: strings ){
			if (s.length() != 0){
				mIdPool.add(s);
			}
		}
		
		RSLog.d(getClass(), " ids="+mIdPool.toArray().toString(), mLocalTag, "loadIds");
	}
	
	private void updateIds(){
		RSLog.d(getClass(), "", mLocalTag, "updateIds");
		
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






















