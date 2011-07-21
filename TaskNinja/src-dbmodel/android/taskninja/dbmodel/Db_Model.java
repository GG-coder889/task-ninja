package android.taskninja.dbmodel;

import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup_String;


public abstract class Db_Model<
	MODEL extends Db_Model,
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
	
	protected abstract Db_Controller<MODEL, INTEGER, LONG, STRING, BOOL> getController();
	
	private JSONObject data;
	
	public Db_Model(){
		data = new JSONObject();
		
		try { data.put(BuiltIn.ID.name(), getController().getNewId());
		} catch (JSONException e) {e.printStackTrace();}
		
		getController().update(MODEL(this));
		getController().add(MODEL(this));
	}
	
	protected Db_Model(JSONObject data) {
		this.data = data;
		getController().add(MODEL(this));
	}
	
	private MODEL MODEL(Db_Model model){
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
	private final HashSet<Db_Listener> mListeners = new HashSet<Db_Listener>();
	
	public void addListener(Db_Listener listener){
		mListeners.add(listener);
	}
	
	public void removeListener(Db_Listener listener){
		mListeners.remove(listener);
	}
	
	public String getData() {
		return data.toString();
	}
	
	private void onChange(Enum e) {
		for (Db_Listener listener: mListeners){
			listener.onChange(e);
		}
		getController().update(MODEL(this));
	}
	// ----------------------------------------------------------------------------- 
	
	
	
	// -----------------------------------------------------------------------------
	// Delete
	// -----------------------------------------------------------------------------
	public void delete(){
		try { data.put(BuiltIn.DELETED.name(), true);
		} catch (JSONException e) { e.printStackTrace();}
		getController().remove(MODEL(this));
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
		String localKey = Db_Prefix.asInteger(key);
		
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
			data.put(Db_Prefix.asInteger(key), value);
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
			return data.getLong(Db_Prefix.asLong(key));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void put(LONG key, Long value) {
		try {
			data.put(Db_Prefix.asLong(key), value);
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
				return data.getString(Db_Prefix.asString(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}			
		}
		
		public void put(STRING key, String value) {
			try {
				data.put(Db_Prefix.asString(key), value);
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
				return data.getBoolean(Db_Prefix.asBool(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		public void put(BOOL key, boolean value) {
			try {
				data.put(Db_Prefix.asBool(key), value);
				onChange(key);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// -----------------------------------------------------------------------------
}
