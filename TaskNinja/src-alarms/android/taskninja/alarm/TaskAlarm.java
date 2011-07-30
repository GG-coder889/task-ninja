package android.taskninja.alarm;

import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.taskninja.alarm.app.NotificationService;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.dbmodel.Db_NullEnum;
import android.taskninja.task.Task;
import android.taskninja.task.TaskBool;
import android.taskninja.task.TaskLong;
import android.util.Log;

public class TaskAlarm extends Db_Model<TaskAlarm, Db_NullEnum, AlarmLong, AlarmString, AlarmBool> {
	
	private static final String TAG = "TaskAlarm";

	public TaskAlarm() {
		super();
	}
	
	public static TaskAlarm get(String id){
		return controller().get(id);
	}
	
	public boolean isValid(){
		// TODO
		return true;
		
	}
	
	public Task getTask(){
		String id = getString(AlarmString.TaskId);
		Task task = Task.get(id);
		
		if (task == null) {
			this.delete();
		} 
		
		return task;
	}
	
	// ----------------------------------------------------------------------------------------------------
	// Set Alarm Methods
	// ----------------------------------------------------------------------------------------------------
	public static void setSingleNotification(Task task) {
		if (task.getBool(TaskBool.HasSingleNotification)
				&& task.getLong(TaskLong.SingleNotificationTime) != null
				&& task.getLong(TaskLong.SingleNotificationTime) > System.currentTimeMillis()
				){
			Log.d(TAG, "Setting single notification");
			
			TaskAlarm alarm = new TaskAlarm();
			alarm.put(AlarmBool.IsRecurring, false);
			alarm.put(AlarmLong.AlarmTime, task.getLong(TaskLong.SingleNotificationTime));
			alarm.put(AlarmBool.IsNotification, true);
			alarm.put(AlarmString.TaskId, task.getId());
			alarm.set();
		} else {
			Log.d(TAG, "failed to set single notification");
		}
	}
	
	
	public void set(){
		Log.d(TAG, "set");
		
		Intent i = null;
		Long when = getLong(AlarmLong.AlarmTime);
		
		if (getBool(AlarmBool.IsNotification)){
			Log.d(TAG, "is Notifiaction");
			i = new Intent(mContext, NotificationService.class);
		} else {
			Log.d(TAG, "is not Notifiaction");
		}
		
		if (	i != null 
				&& when != null  
				&& when > System.currentTimeMillis()) {
			
			when = when - when % TimeUnit.MINUTES.toMillis(1);
			
			i.putExtra(AlarmString.TaskId.name(), getString(AlarmString.TaskId));
			i.putExtra(BuiltIn.ID.name(), getId());
			
			PendingIntent pi = PendingIntent.getService(mContext, 0, i, 0);

			AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
			am.set(AlarmManager.RTC, when, pi);
			
			Log.d(TAG, "alarm has been set");
		} else {
			Log.d(TAG, "failed to set alarm");
		}
	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Db_Controller<TaskAlarm, Db_NullEnum, AlarmLong, AlarmString, AlarmBool> mController;
	
	@Override
	protected Db_Controller <TaskAlarm, Db_NullEnum, AlarmLong, AlarmString, AlarmBool> getController() {
		return controller();
	}
	
	private static Db_Controller<TaskAlarm, Db_NullEnum, AlarmLong, AlarmString, AlarmBool> controller(){
		if (mController == null){
			try {
				mController = new LocalController(TaskAlarm.class, mContext);
			}catch (Exception e2){
				e2.printStackTrace();
				return null;
			}
		}
		return mController;
	}
	
	private TaskAlarm(JSONObject JSONObject){
		super(JSONObject);
	}

	private static class LocalController extends Db_Controller<TaskAlarm, Db_NullEnum, AlarmLong, AlarmString, AlarmBool> {
			
		protected LocalController(Class<TaskAlarm> dbModel, Context context) {
			super(dbModel, context);
		}

		@Override
		protected TaskAlarm getInstance(JSONObject json) {
			return new TaskAlarm(json);
		}	
	}
	// ----------------------------------------------------------------------------------------------------
	
	
}













