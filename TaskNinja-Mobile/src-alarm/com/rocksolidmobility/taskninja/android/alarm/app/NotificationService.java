package com.rocksolidmobility.taskninja.android.alarm.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.rocksolidmobility.android.rsmodel.RSModel;
import com.rocksolidmobility.taskninja.android.alarm.AlarmBool;
import com.rocksolidmobility.taskninja.android.alarm.AlarmLong;
import com.rocksolidmobility.taskninja.android.alarm.TnAlarm;
import com.rocksolidmobility.taskninja.android.task.Task;

public class NotificationService extends Service {
	
	private static final String TAG = "NotificationService";

	@Override
	public IBinder onBind(Intent arg0) { return null;}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "onStart");
		
		String id = intent.getStringExtra(RSModel.BuiltIn.ID.name());
		
		if (id != null){
			TnAlarm alarm = TnAlarm.get(id);
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

	private void makeNotification(TnAlarm alarm) {
		Log.d(TAG, "makeNotification");
		
		Task task = alarm.getTask();
		if (task != null){
//			Intent i = new Intent(getApplicationContext(), TaskGroupActivity.class);
//			i.putExtra(TaskGroupActivity.ExtraChoices.TaskId.name(), task.getId());
			
//			PendingIntent pi = PendingIntent.getActivity(
//					getApplicationContext(), 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);

//			Notification.Builder builder = new Notification.Builder(getApplicationContext());
//			builder.setContentIntent(pi);
//			builder.setWhen(System.currentTimeMillis());
//			builder.setTicker(task.toString());
//			builder.setContentInfo(task.toString());
//			builder.setContentText(task.toString());
//			builder.setDefaults(Notification.DEFAULT_ALL);
//			builder.setSmallIcon(R.drawable.ic_stat_notification);
//			
//			((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
//				.notify(task.getId(), 0, builder.getNotification());
		}
	}
	
	private void reset(TnAlarm alarm) {
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





















