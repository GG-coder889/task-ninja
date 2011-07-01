package com.taskninjapro.android.alarms;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.taskninjapro.android.app.App;
import com.taskninjapro.android.app.Constants;

public class BootCompletedReciever extends BroadcastReceiver implements Constants {

	@Override
	public void onReceive(Context context, Intent intent) {
//		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		AlarmDatabase adb = AlarmDatabase.getInstance(context);
		List<Alarm> alarms = adb.getAlarms();
		for (Alarm alarm: alarms){
			alarm.set();
//			am.set(AlarmManager.RTC_WAKEUP, alarm.getAsLong(KEY_WHEN), alarm.getPendingIntent());
			
			
			
		// Update queue task widget
		Intent i = new Intent(App.getContext(), CurrentTaskWidget.class);
		i.putExtra(CurrentTaskWidget.WHAT, CurrentTaskWidget.UPDATE);
		context.sendBroadcast(i);
		}
	}

}
