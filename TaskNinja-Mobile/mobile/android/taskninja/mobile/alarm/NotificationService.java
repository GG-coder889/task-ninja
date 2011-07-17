package android.taskninja.mobile.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.taskninja.mobile.Task.Task;
import android.taskninja.mobile.Task.TaskString;
import android.taskninja.mobile.TaskSettings.TaskSettings;
import android.taskninja.mobile.app.Constants;

import com.taskninjapro.android.R;

public class NotificationService extends Service implements Constants {

	@Override
	public IBinder onBind(Intent intent) {return null;}

	@Override
	public void onStart(Intent intent, int startId) {
		int alarmId = intent.getIntExtra(_ID, -1);
		Alarm alarm = Alarm.get(alarmId);
		
		int taskId = intent.getIntExtra(KEY_TASK_ID, -1); 
		Task task = Task.get(taskId);
		
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
		int taskId = task.getId();
		Intent i = new Intent(getApplicationContext(), TaskSettings.class);
		i.putExtra(_ID, taskId);
		PendingIntent pi = PendingIntent.getActivity(
				getApplicationContext(), 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);
		int icon = R.drawable.ic_status_bar;
		long when = System.currentTimeMillis();
		Notification notification = 
			new Notification(icon, task.getString(TaskString.KEY_WHAT), when);
		
		notification.setLatestEventInfo(getApplicationContext(), task.getString(TaskString.KEY_WHAT),
				task.getString(TaskString.KEY_NOTES), pi);
		
		notification.contentIntent = pi;
		notification.defaults = Notification.DEFAULT_ALL;
		notification.defaults |= Notification.DEFAULT_SOUND;

		NotificationManager notificationManager = 
			(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(taskId, notification);
	}
	
	private void resetAlarm(Alarm alarm){
		if (alarm.getLong(AlarmLong.KEY_RECURRING) != null){
			long when = alarm.getLong(AlarmLong.KEY_WHEN) + alarm.getLong(AlarmLong.KEY_RECURRING);
			alarm.put(AlarmLong.KEY_WHEN, when);
			alarm.set();
		} else {
			alarm.delete();
		}
	}

}
