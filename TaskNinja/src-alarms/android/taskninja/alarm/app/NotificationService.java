package android.taskninja.alarm.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.taskninja.R;
import android.taskninja.alarm.AlarmBool;
import android.taskninja.alarm.AlarmLong;
import android.taskninja.alarm.AlarmString;
import android.taskninja.alarm.TaskAlarm;
import android.taskninja.app.TaskGroupActivity;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.task.Task;
import android.taskninja.task.TaskString;
import android.util.Log;

public class NotificationService extends Service {
	
	private static final String TAG = "NotificationService";

	@Override
	public IBinder onBind(Intent arg0) { return null;}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart");
		
		String id = intent.getStringExtra(Db_Model.BuiltIn.ID.name());
		
		if (id != null){
			TaskAlarm alarm = TaskAlarm.get(id);
			if (alarm != null){
				if (alarm.isValid()){
					makeNotification(alarm);
					if (alarm.getBool(AlarmBool.IsRecurring)){
						reset(alarm);
					} else {
						alarm.delete();
					}
				} else {
					alarm.delete();
				}
			}
		}
		stopSelf();
	}

	private void makeNotification(TaskAlarm alarm) {
		Log.d(TAG, "makeNotification");
		
		Task task = alarm.getTask();
		if (task != null){
			Intent i = new Intent(getApplicationContext(), TaskGroupActivity.class);
			i.putExtra(TaskGroupActivity.ExtraChoices.TaskId.name(), task.getId());
			
			PendingIntent pi = PendingIntent.getActivity(
					getApplicationContext(), 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);

			Notification.Builder builder = new Notification.Builder(getApplicationContext());
			builder.setContentIntent(pi);
			builder.setWhen(System.currentTimeMillis());
			builder.setTicker(task.toString());
//			builder.setContentInfo(task.toString());
			builder.setContentText(task.toString());
			builder.setDefaults(Notification.DEFAULT_ALL);
			builder.setSmallIcon(R.drawable.ic_stat_notification);
			
			((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
				.notify(task.getId(), 0, builder.getNotification());
		}
	}
	
	private void reset(TaskAlarm alarm) {
		Log.d(TAG, "reset");
		
		Long recurring = alarm.getLong(AlarmLong.RecurringTime);
		Long time = alarm.getLong(AlarmLong.AlarmTime);
		if (recurring != null && time != null){
			alarm.put(AlarmLong.AlarmTime, time + recurring);
			alarm.set();
		} else {
			alarm.delete();
		}
		
	}

	

}





















