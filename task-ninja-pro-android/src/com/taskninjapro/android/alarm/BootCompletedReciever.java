package com.taskninjapro.android.alarm;

import java.util.LinkedHashSet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.taskninjapro.android.app.App;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.queue.CurrentTaskWidget;

public class BootCompletedReciever extends BroadcastReceiver implements Constants {

	@Override
	public void onReceive(Context context, Intent intent) {
		LinkedHashSet<Alarm> alarms = Alarm.getAll();
		for (Alarm alarm: alarms){
			alarm.set();
			
			
		// Update queue task widget
		Intent i = new Intent(App.getContext(), CurrentTaskWidget.class);
		i.putExtra(CurrentTaskWidget.WHAT, CurrentTaskWidget.UPDATE);
		context.sendBroadcast(i);
		}
	}

}
