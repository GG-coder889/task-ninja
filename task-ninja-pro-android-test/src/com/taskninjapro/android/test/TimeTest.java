package com.taskninjapro.android.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import android.os.Environment;
import android.os.SystemClock;
import android.test.AndroidTestCase;
import android.util.Log;

public class TimeTest extends AndroidTestCase {
	
	private static final String TAG = "TimeTest";
	
	public void testChangeTime(){
		Long currentTime = System.currentTimeMillis();
		Log.d(TAG, "startTime: "+currentTime.toString());
		
		// Do something to change the time to 0
		// ------------------------------------------------------------
		
		
		
		
		Runtime runtime = Runtime.getRuntime();
		try {
			readLines(runtime.exec("su"));
//			readLines(runtime.exec("echo hello-hello"));
//			runtime.exec("su");
			runtime.exec("date 0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<String> command = new LinkedList<String>();
//		command.add("pwd");
//		command.add("/echo");
//		command.add("hello-world");
//		command.add("date");
//		command.add("10000");
//		ProcessBuilder pb = new ProcessBuilder(command);
//		pb.directory(Environment.getRootDirectory());
//		pb.redirectErrorStream(true);
//		
//		Log.d(TAG, command.toString());
//		Log.d(TAG, pb.environment().entrySet().toString());
//		Log.d(TAG, pb.directory().toString());
//		
//		try {
//			readLines(pb.start());
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
//		assertTrue(SystemClock.setCurrentTimeMillis(0));
		// ------------------------------------------------------------
		// Check that time was changed
		
		Long newTime = System.currentTimeMillis();
		Log.d(TAG, "newTime: "+newTime.toString());
		Log.d(TAG, "Time Change : "+(newTime - currentTime));
		assertTrue(newTime < currentTime);
		assertEquals(Long.valueOf(0), newTime);
	}
	
	
	
	
	private static void readLines(Process process){
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;

		try {
			while((line = input.readLine()) != null){
				System.out.println(TAG+" "+line);
				Log.d(TAG, line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		line = null;

		try {
			while((line = err.readLine()) != null){
				System.out.println(TAG+" "+line);
				Log.d(TAG, line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		process.destroy();

	}

}
