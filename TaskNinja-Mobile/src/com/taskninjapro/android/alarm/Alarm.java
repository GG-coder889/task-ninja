package com.taskninjapro.android.alarm;

import java.util.LinkedHashSet;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.taskninjapro.android.app.App;
import com.taskninjapro.android.dbmodel.DbController;
import com.taskninjapro.android.dbmodel.DbModel;
import com.taskninjapro.android.dbmodel.TableBuilder.BuiltIn;

public class Alarm extends DbModel<Alarm, AlarmInteger, AlarmLong, AlarmString, AlarmIntegerList, AlarmBool> {
	
	private static final String TAG = "Alarm";
	
	private static final DbController<Alarm, AlarmInteger, AlarmLong, AlarmString, AlarmIntegerList, AlarmBool> mController 
	= new LocalController(Alarm.class, App.getContext(), 1);

	@Override
	protected DbController <Alarm, AlarmInteger, AlarmLong, AlarmString, AlarmIntegerList, AlarmBool> getController() {
		return mController;
	}
	
	private Alarm(ContentValues values){
		super(values);
	}
	
	public Alarm(int taskId, long when, long recurring) {
		put(AlarmInteger.KEY_TASK_ID, taskId);
		put(AlarmLong.KEY_WHEN, when);
		put(AlarmLong.KEY_RECURRING, recurring);
	}

	public static Alarm get(int id){
		return mController.get(id);
	}
	
	private static class LocalController extends DbController<Alarm, AlarmInteger, AlarmLong, AlarmString, AlarmIntegerList, AlarmBool> {
		
		protected LocalController(Class<Alarm> dbModel, Context context, int version) {
			super(dbModel, context, version);
		}

		@Override
		protected AlarmIntegerList[] getIntegerListValues() {
			return AlarmIntegerList.values();
		}

		@Override
		protected AlarmInteger[] getIntegerValues() {
			return AlarmInteger.values();
		}

		@Override
		protected AlarmLong[] getLongValues() {
			return AlarmLong.values();
		}

		@Override
		protected Alarm getNewInstance(ContentValues arg0) {
			return new Alarm(arg0);
		}

		@Override
		protected AlarmString[] getStringValues() {
			return AlarmString.values();
		}

		@Override
		protected AlarmBool[] getBoolValues() {
			return AlarmBool.values();
		}
		
	}

	public void set() {
		Log.d(TAG, "set");

		Intent i = null;

		if (getBool(AlarmBool.KEY_QUEUING)){
			Log.d(TAG, "set queuing");
			i = new Intent(App.getContext(), QueuingService.class);

		} else if (getBool(AlarmBool.KEY_NOTIFICATION)) {
			Log.d(TAG, "set notifiaction");
			i = new Intent(App.getContext(), NotificationService.class);
		}

		if (i != null){
			i.putExtra(AlarmInteger.KEY_TASK_ID.name(), getInteger(AlarmInteger.KEY_TASK_ID));
			i.putExtra(BuiltIn.ID.name(), getId());
			PendingIntent pi = PendingIntent.getService(App.getContext(), 0, i, 0);

			AlarmManager am = (AlarmManager) App.getContext().getSystemService(Context.ALARM_SERVICE);
			am.set(AlarmManager.RTC, getLong(AlarmLong.KEY_WHEN), pi);
		}		
	}

	public static LinkedHashSet<Alarm> getAll() {
		return mController.getAll();
	}

}
