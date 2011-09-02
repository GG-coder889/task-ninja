package com.rocksolidmobility.taskninja.android.dbmodel;


public enum Db_Prefix {
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
	
	public static String asBool(String key){
		return prefix(BOOL, key);
	}
	
	public static String asBool(Enum key){
		return asBool(key.name());
	}
	
	public static String asTable(String key){
		return prefix(TABLE, key);
	}
	
	private static String prefix(Db_Prefix prefix, String key){
		return prefix.name() + "_"+ key;
	}
}
