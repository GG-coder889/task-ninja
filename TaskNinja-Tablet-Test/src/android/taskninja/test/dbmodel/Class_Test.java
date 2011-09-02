package android.taskninja.test.dbmodel;

import java.lang.reflect.Constructor;

import android.test.AndroidTestCase;
import android.util.Log;

public class Class_Test extends AndroidTestCase {
	
	private static final String TAG = "ClassTest";
	
	public void testNames(){
		Class hello = Hello.class;
		Log.d(TAG, "getName = " + hello.getName());
		Log.d(TAG, "getCanonicalName = "+hello.getCanonicalName());
		Log.d(TAG, "isMemberClass = "+hello.isMemberClass());
		try {
			Constructor c = hello.getConstructor();
			Log.d(TAG, "constructor = "+c.getName());
		} catch (SecurityException e) {
			Log.d(TAG, "constructor = SecurityException");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			Log.d(TAG, "constructor = NoSuchMethodException");
			e.printStackTrace();
		}
		
		String name = hello.getName().split("\\$")[1];
		Log.d(TAG, "Name = "+name);
		assertEquals("Hello", name);
		

	}
	
	private static class Hello {
		public Hello(){
			
		}
	}

}
