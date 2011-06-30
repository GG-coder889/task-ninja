package com.taskninjapro.android.test;

import java.io.IOException;

import android.os.SystemClock;
import android.test.AndroidTestCase;
import android.util.Log;

public class TimeTest extends AndroidTestCase {
	
	private static final String TAG = "TimeTest";
	
	public void testChangeTime(){
		Log.d(TAG, String.valueOf(System.currentTimeMillis()));
		
		
		Runtime rt = Runtime.getRuntime();
		try {
//			rt.exec("$ adb shell date $(date --date='1970-06-11 12:10:10' +%s.0)");
//			date "$((10800 + $(date +%s)))"
//			date "$('1970-06-11 12:10:10' +%s)))"
//			date "time in seconds"
//			rt.exec("adb shell");
//			rt.exec("su");
			rt.exec("#date 0");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(SystemClock.setCurrentTimeMillis(0));
		Log.d(TAG, String.valueOf(System.currentTimeMillis()));
	}

}
