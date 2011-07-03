package com.taskninjapro.android.alarms;

import java.util.LinkedHashSet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.taskninjapro.android.Task.Task;
import com.taskninjapro.android.Task.TaskDatabase;
import com.taskninjapro.android.app.Constants;

public class QueuingService extends Service implements Constants {
	
	private static final String TAG = "QueuingService";
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart");
		
		AlarmDatabase alarmDb = AlarmDatabase.getInstance(getApplicationContext());
		int alarmId = intent.getIntExtra(_ID, -1);
		Alarm alarm = alarmDb.getAlarm(alarmId);
		
		TaskDatabase taskDb = TaskDatabase.getInstance(getApplicationContext());
		int taskId = intent.getIntExtra(KEY_TASK_ID, -1); 
		Task task = taskDb.getTask(taskId);
		
		// TODO add check that alarm is going off when it should
		if (task != null){
			addToQueue(task);
			if (alarm != null)
				resetAlarm(alarm);
		} else {
			alarm.delete();
		}
		stopSelf();
	}
	
	
	private void addToQueue(Task task){
		Log.d(TAG, "addToQueue: "+task.getWhat());
		
		if (task.getAsBoolean(KEY_SINGLE_QUEUING) || task.getAsBoolean(KEY_RECURRING_QUEUING)){
			
			TaskDatabase taskDb = TaskDatabase.getInstance(getApplicationContext());
			LinkedHashSet<Integer> queue = taskDb.getQueue();
			LinkedHashSet<Integer> newQueue = new LinkedHashSet<Integer>();
			
			if (task.getAsBoolean(KEY_SINGLE_QUEUING)){
				Log.d(TAG, task.getWhat()+": has single queing");
				
				if (task.getAsBoolean(KEY_SINGLE_QUEUEING_FRONT)){
					newQueue.add(task.getId());
				}
				
			} else if (task.getAsBoolean(KEY_RECURRING_QUEUING)){
				Log.d(TAG, task.getWhat()+": has recurring queing");
				
				task.put(KEY_COMPLETED, false);
				if (task.getAsBoolean(KEY_RECURRING_QUEUING_FRONT)){
					newQueue.add(task.getId());
				}
			}
			
			for (Integer id: queue){
				newQueue.add(id);
			}
			
			newQueue.add(task.getId());
			taskDb.setQueue(newQueue);
		}
	}
	
	
	private void resetAlarm(Alarm alarm){
		Log.d(TAG, "resetAlarm: "+alarm.getId());
		
		if (alarm.getAsBoolean(KEY_RECURRING)){
			long when = alarm.getAsLong(KEY_WHEN) + alarm.getAsLong(KEY_RECURRING);
			alarm.put(KEY_WHEN, when);
			alarm.set();
		} else {
			alarm.delete();
		}
	}



}
