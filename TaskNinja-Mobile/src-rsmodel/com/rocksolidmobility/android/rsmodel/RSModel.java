package com.rocksolidmobility.android.rsmodel;

import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;

import com.rocksolidmobility.taskninja.android.taskgroup.TaskGroup;
import com.rocksolidmobility.taskninja.android.taskgroup.TaskGroupString;

import android.content.Context;


public abstract class RSModel<
	MODEL extends RSModel,
	INTEGER extends Enum<INTEGER>,
	LONG extends Enum<LONG>,
	STRING extends Enum<STRING>,
	BOOL extends Enum<BOOL>
	> {
	
	private static final String TAG = "Z_Model";
	protected static Context mContext;
	
	public static void setContext(Context context){
		mContext = context;
	}
	
	protected abstract RSController<MODEL, INTEGER, LONG, STRING, BOOL> instanceGetController();
	
	private JSONObject data;
	
	public RSModel(){
		data = new JSONObject();
		
		try { data.put(BuiltIn.ID.name(), instanceGetController().getNewId());
		} catch (JSONException e) {e.printStackTrace();}
		
		instanceGetController().update(MODEL(this));
		instanceGetController().add(MODEL(this));
	}
	
	protected RSModel(JSONObject data) {
		this.data = data;
		instanceGetController().add(MODEL(this));
	}
	
	private MODEL MODEL(RSModel model){
		MODEL result = null;
		try {
			result = (MODEL) model;
		} catch (Exception e){
			// TODO
			e.printStackTrace();
		}
		return result;
	}
	
	public String getId() {
		try { 
			return data.getString(BuiltIn.ID.name());
		} catch (JSONException e) { 
			e.printStackTrace(); 
			return null;
		}
	}
	
	public enum BuiltIn {
		ID, DELETED,
	}
	
	// -----------------------------------------------------------------------------
	// Update
	// ----------------------------------------------------------------------------- 
	private final HashSet<RSListener> mListeners = new HashSet<RSListener>();
	
	public void addListener(RSListener listener){
		mListeners.add(listener);
	}
	
	public void removeListener(RSListener listener){
		mListeners.remove(listener);
	}
	
	public String getData() {
		return data.toString();
	}
	
	private void onChange(Enum e) {
		for (RSListener listener: mListeners){
			listener.onChange(e);
		}
		instanceGetController().update(MODEL(this));
	}
	// ----------------------------------------------------------------------------- 
	
	
	
	// -----------------------------------------------------------------------------
	// Delete
	// -----------------------------------------------------------------------------
	public void delete(){
		try { data.put(BuiltIn.DELETED.name(), true);
		} catch (JSONException e) { e.printStackTrace();}
		instanceGetController().remove(MODEL(this));
		onChange(BuiltIn.DELETED);
	}
	
	public  boolean isDeleted() {
		boolean value = false;
		String key = BuiltIn.DELETED.name();
		if (data.has(key)){
			try { value = data.getBoolean(key);
			} catch (JSONException e) { e.printStackTrace(); }
		}
		return value;
	}
	// -----------------------------------------------------------------------------
	
	
	
	// -----------------------------------------------------------------------------
	// Integer
	// -----------------------------------------------------------------------------
	public Integer getInteger(INTEGER key) {
		
		Integer result = null;
		String localKey = RSPrefix.asInteger(key);
		
		if (data.has(localKey)){
			try { 
				result = data.getInt(localKey);
			} catch (JSONException e) { 
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void put(INTEGER key, int value) {
		try {
			data.put(RSPrefix.asInteger(key), value);
			onChange(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// -----------------------------------------------------------------------------
	
	
	// -----------------------------------------------------------------------------
	// Long
	// -----------------------------------------------------------------------------
	public Long getLong(LONG key) {
		try {
			return data.getLong(RSPrefix.asLong(key));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void put(LONG key, Long value) {
		try {
			data.put(RSPrefix.asLong(key), value);
			onChange(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// -----------------------------------------------------------------------------
	
	
	
	// -----------------------------------------------------------------------------
	// String
	// -----------------------------------------------------------------------------
	public String getString(STRING key) {
		try {
			return data.getString(RSPrefix.asString(key));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}			
	}
	
	public void put(STRING key, String value) {
		try {
			data.put(RSPrefix.asString(key), value);
			onChange(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// -----------------------------------------------------------------------------
	
	
	
	// -----------------------------------------------------------------------------
	// Boolean
	// -----------------------------------------------------------------------------
	public boolean getBool(BOOL key) {
		try {
			return data.getBoolean(RSPrefix.asBool(key));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void put(BOOL key, boolean value) {
		try {
			data.put(RSPrefix.asBool(key), value);
			onChange(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// -----------------------------------------------------------------------------
}
