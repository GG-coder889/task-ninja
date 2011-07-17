package com.taskninjapro.android.alarm;

import java.util.LinkedHashSet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.taskninjapro.android.Queue.QueueManager;
import com.taskninjapro.android.Task.Task;
import com.taskninjapro.android.Task.TaskBool;
import com.taskninjapro.android.Task.TaskLong;
import com.taskninjapro.android.Task.TaskString;
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
		
		int alarmId = intent.getIntExtra(_ID, -1);
		Alarm alarm = Alarm.get(alarmId);
		
		int taskId = intent.getIntExtra(KEY_TASK_ID, -1); 
		Task task = Task.get(taskId);
		
		// TODO check that the alarm is going off when it should
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
		Log.d(TAG, "addToQueue: "+task.getString(TaskString.KEY_WHAT));
		
		if (task.getBool(TaskBool.KEY_SINGLE_QUEUING) || task.getBool(TaskBool.KEY_RECURRING_QUEUING)){
			
			LinkedHashSet<Task> queue = QueueManager.getQueue();
			LinkedHashSet<Task> newQueue = new LinkedHashSet<Task>();
			
			if (task.getBool(TaskBool.KEY_SINGLE_QUEUING)){
				Log.d(TAG, task.getString(TaskString.KEY_WHAT)+": has single queing");
				
				if (task.getBool(TaskBool.KEY_SINGLE_QUEUEING_FRONT)){
					newQueue.add(task);
				}
				
			} else if (task.getBool(TaskBool.KEY_RECURRING_QUEUING)){
				Log.d(TAG, task.getString(TaskString.KEY_WHAT)+": has recurring queing");
				
				task.put(TaskLong.KEY_COMPLETED, 0L);
				if (task.getBool(TaskBool.KEY_RECURRING_QUEUING_FRONT)){
					newQueue.add(task);
				}
			}
			
			for (Task t: queue){
				newQueue.add(t);
			}

			newQueue.add(task);
			QueueManager.setQueue(newQueue);
		}
	}
	
	
	private void resetAlarm(Alarm alarm){
		Log.d(TAG, "resetAlarm: "+alarm.getId());
	
		if (alarm.getBool(AlarmBool.KEY_RECURRING)){
			long when = alarm.getLong(AlarmLong.KEY_WHEN) + alarm.getLong(AlarmLong.KEY_RECURRING);
			alarm.put(AlarmLong.KEY_WHEN, when);
			alarm.set();
		} else {
			alarm.delete();
		}
	}



}
