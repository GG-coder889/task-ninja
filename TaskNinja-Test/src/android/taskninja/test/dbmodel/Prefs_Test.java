package android.taskninja.test.dbmodel;

import android.content.SharedPreferences;
import android.test.AndroidTestCase;

public class Prefs_Test extends AndroidTestCase {
	
	public void testPrefs(){
		SharedPreferences prefs = mContext.getSharedPreferences("TEST", 1);
	}

}
