package com.rocksolidmobility.android.rsmodel;

import android.util.Log;

public class RSLog {
	
	public static void d(Class c, String message, String... tags) {
		Log.d(c.getSimpleName(), makeMessage(message, tags));
	}
	
	private static String makeMessage(String message, String[] tags){
		StringBuffer response = new StringBuffer();
		for(String tag: tags){
			response.append(tagify(tag));
		}
		return response.append(message).toString();
	}
	
	private static String tagify(String s){
		return "[ "+s+" ] ";
	}

}
