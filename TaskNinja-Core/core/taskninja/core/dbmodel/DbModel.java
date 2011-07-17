package taskninja.core.dbmodel;

import java.util.LinkedList;
import java.util.List;

import taskninja.core.dbmodel.TableBuilder.BuiltIn;
import taskninja.core.dbmodel.TableBuilder.Prefix;
import android.content.ContentValues;
import android.os.SystemClock;
import android.util.Log;

public abstract class DbModel<MODEL extends DbModel,
	INTEGER extends Enum<INTEGER>,
	LONG extends Enum<LONG>,
	STRING extends Enum<STRING>,
	INTEGER_LIST extends Enum<INTEGER_LIST>,
	BOOL extends Enum<BOOL>> {
	
	private static final String TAG = "DbModel";
	
	private ContentValues mValues;
	
	protected abstract DbController<MODEL, INTEGER, LONG, STRING, INTEGER_LIST, BOOL> getController();
	
	public DbModel(){
		mValues = new ContentValues();
		mValues.put(BuiltIn.ID.name(), getController().getNewId());
		getController().watch((MODEL) this);
		getController().register(this);
	}
	
	public DbModel(ContentValues values){
		mValues = values;
		getController().watch((MODEL) this);
	}
	
	public int getId(){
		return mValues.getAsInteger(BuiltIn.ID.name());
	}
	
	public ContentValues getValues() {
		return new ContentValues(mValues);
	}
	
	private void onChange() {
		getController().register(this);
	}
	
	// -----------------------------------------------------------------------------
	// Delete
	// -----------------------------------------------------------------------------
	public void delete(){
		mValues.put(BuiltIn.DELETED.name(), SystemClock.currentThreadTimeMillis());
		onChange();
	}
	
	public boolean isDeleted() {
		Integer i = mValues.getAsInteger(BuiltIn.DELETED.name());
		if (i != null && i != 0){
			return true;
		} else {
			return false;
		}
	}
	
	// -----------------------------------------------------------------------------
	
	
	
	// -----------------------------------------------------------------------------
	// Integer
	// -----------------------------------------------------------------------------
	public Integer getInteger(INTEGER key) {
		return mValues.getAsInteger(Prefix.asInteger(key.name()));
	}
	
	public void put(INTEGER key, int value) {
		mValues.put(Prefix.asInteger(key.name()), value);
		onChange();
	}
	// -----------------------------------------------------------------------------

	
	
	// -----------------------------------------------------------------------------
	// Long
	// -----------------------------------------------------------------------------
	public Long getLong(LONG key) {
		return mValues.getAsLong(Prefix.asLong(key.name()));
	}
	
	public void put(LONG key, Long value) {
		mValues.put(Prefix.asLong(key.name()), value);
		onChange();
	}
	// -----------------------------------------------------------------------------
	
	
	
	// -----------------------------------------------------------------------------
	// Integer List
	// -----------------------------------------------------------------------------
	
	public List<Integer> getIntegerList(INTEGER_LIST key) {
		List<Integer> list = new LinkedList<Integer>();
		String listString = mValues.getAsString(Prefix.asIntegerList(key));
		if (listString != null){
			String[] arr = listString.split(",");
			for (String value: arr){
				try {
					list.add(Integer.valueOf(value));
				} catch (NumberFormatException e){
					Log.d(TAG, "Integer List has a non-integer entry");
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public void put(INTEGER_LIST key, List<Integer> value){
		StringBuilder sb = new StringBuilder();
		for (Integer i: value){
			sb.append(i);
			sb.append(',');
		}
		if (sb.length() != 0){
			mValues.put(Prefix.asIntegerList(key), sb.substring(0, sb.length()-1));
		} else {
			mValues.put(Prefix.asIntegerList(key), "");
		}
		onChange();
	}
	// -----------------------------------------------------------------------------
	
	
	
	// -----------------------------------------------------------------------------
	// String
	// -----------------------------------------------------------------------------
	public String getString(STRING key) {
		String s = mValues.getAsString(Prefix.asString(key.name()));
		if (s != null){
			return s;
		} else {
			return "";
		}
		
	}
	
	public void put(STRING key, String value) {
		mValues.put(Prefix.asString(key), value);
		onChange();
	}
	// -----------------------------------------------------------------------------
	
	
	
	// -----------------------------------------------------------------------------
	// Boolean
	// -----------------------------------------------------------------------------
	public boolean getBool(BOOL key) {
		Integer i = mValues.getAsInteger(Prefix.asBool(key));	
		if (i != null && i != 0 ){	
			return true;
		} else {
			return false;
		}
	}
	
	public void put(BOOL key, boolean value) {
		if (value){
			mValues.put(Prefix.asBool(key), 1);
		} else {
			mValues.put(Prefix.asBool(key), 0);
		}
		
		onChange();
	}
	// -----------------------------------------------------------------------------
	
	
	

	
	
	

	

	

	
	
	

}
