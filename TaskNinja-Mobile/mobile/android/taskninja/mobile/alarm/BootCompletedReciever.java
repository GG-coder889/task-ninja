package android.taskninja.mobile.alarm;

import java.util.LinkedHashSet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.taskninja.mobile.Queue.CurrentTaskWidget;
import android.taskninja.mobile.app.App;
import android.taskninja.mobile.app.Constants;


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
