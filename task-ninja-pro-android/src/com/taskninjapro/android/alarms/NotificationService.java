package com.taskninjapro.android.alarms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.taskninjapro.android.R;
import com.taskninjapro.android.Task.Task;
import com.taskninjapro.android.Task.TaskDatabase;
import com.taskninjapro.android.TaskSettings.TaskSettings;
import com.taskninjapro.android.app.Constants;

public class NotificationService extends Service implements Constants {

	@Override
	public IBinder onBind(Intent intent) {return null;}

	@Override
	public void onStart(Intent intent, int startId) {
		AlarmDatabase alarmDb = AlarmDatabase.getInstance(getApplicationContext());
		int alarmId = intent.getIntExtra(_ID, -1);
		Alarm alarm = alarmDb.getAlarm(alarmId);
		
		TaskDatabase taskDb = TaskDatabase.getInstance(getApplicationContext());
		int taskId = intent.getIntExtra(KEY_TASK_ID, -1); 
		Task task = taskDb.getTask(taskId);
		
		if (task != null){
			makeNotification(task);
			if (alarm != null)
				resetAlarm(alarm);
		} else {
			alarm.delete();
		}
		stopSelf();
	}
	
	private void makeNotification(Task task){
		int taskId = task.getAsInteger(_ID);
		Intent i = new Intent(getApplicationContext(), TaskSettings.class);
		i.putExtra(_ID, taskId);
		PendingIntent pi = PendingIntent.getActivity(
				getApplicationContext(), 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);
		int icon = R.drawable.ic_status_bar;
		long when = System.currentTimeMillis();
		Notification notification = 
			new Notification(icon, task.getAsString(KEY_WHAT), when);
		
		notification.setLatestEventInfo(getApplicationContext(), task.getAsString(KEY_WHAT),
				task.getAsString(KEY_NOTES), pi);
		
		notification.contentIntent = pi;
		notification.defaults = Notification.DEFAULT_ALL;
		notification.defaults |= Notification.DEFAULT_SOUND;

		NotificationManager notificationManager = 
			(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(taskId, notification);
	}
	
	private void resetAlarm(Alarm alarm){
		if (alarm.getAsBoolean(KEY_RECURRING)){
			long when = alarm.getAsLong(KEY_WHEN) + alarm.getAsLong(KEY_RECURRING);
			alarm.put(KEY_WHEN, when);
			alarm.set();
		} else {
			alarm.delete();
		}
	}

}
