package taskninja.core.dbmodel;

import android.util.Log;

public class TableBuilder {
	
	private static final String TAG = "TableBuilder";
	
	private final StringBuilder mStringBuilder = new StringBuilder();
	
	public enum BuiltIn {
		ID, DELETED
	}
	
	public enum Prefix {
		INTEGER, STRING, LONG, INTEGER_LIST, BOOL, TABLE;
		
		public static String asInteger(String key){
			return prefix(INTEGER, key);
		}
		
		public static String asInteger(Enum key){
			return asInteger(key.name());
		}
		
		public static String asString(String key){
			return prefix(STRING, key);
		}
		
		public static String asString(Enum key){
			return asString(key.name());
		}
		
		public static String asLong(String key){
			return prefix(LONG, key);
		}
		
		public static String asLong(Enum key){
			return asLong(key.name());
		}
		
		public static String asIntegerList(String key){
			return prefix(INTEGER_LIST, key);
		}
		
		public static String asIntegerList(Enum key){
			return asIntegerList(key.name());
		}
		
		public static String asBool(String key){
			return prefix(BOOL, key);
		}
		
		public static String asBool(Enum key){
			return asBool(key.name());
		}
		
		public static String asTable(String key){
			return prefix(TABLE, key);
		}
		
		private static String prefix(Prefix prefix, String key){
			return prefix.name() + "_"+ key;
		}
	}

	public TableBuilder(String tableName) {
		mStringBuilder.append("CREATE TABLE IF NOT EXISTS ");
		mStringBuilder.append(tableName);
		mStringBuilder.append("(");
		mStringBuilder.append(BuiltIn.ID);
		mStringBuilder.append(" INTEGER PRIMARY KEY ON CONFLICT REPLACE, ");
		addNum(BuiltIn.DELETED.name());
	}

	public void addNum(String key) {
		mStringBuilder.append(key);
		mStringBuilder.append(" INTEGER, ");
		
	}

	public void addText(String key) {
		mStringBuilder.append(key);
		mStringBuilder.append(" TEXT, ");
		
	}


	public String buildCommand() {
		// replace the last ", " with ");"
		String r =  mStringBuilder.replace(mStringBuilder.length()-2, mStringBuilder.length(), ");").toString();
		Log.d(TAG, r);
		return r;
	}
		


}
