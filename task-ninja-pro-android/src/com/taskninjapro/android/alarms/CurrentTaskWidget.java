package com.taskninjapro.android.alarms;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.taskninjapro.android.R;
import com.taskninjapro.android.Task.Task;
import com.taskninjapro.android.Task.TaskDatabase;
import com.taskninjapro.android.app.App;
import com.taskninjapro.android.app.Constants;

public class CurrentTaskWidget extends AppWidgetProvider implements Constants {

	public static final int UPDATE = 100;
	private static final int COMPLETE = 200;
	private static final String TASK_ID = "task_id";
	public static final String WHAT = "what";
	private static final String TAG = "WidgetProvider";

	TaskDatabase mDatabaseHelper = TaskDatabase.getInstance(App.getContext());

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d(TAG, "onUpdate");
		update(context);
	}

	public void update(Context context){
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
		
		update(context, appWidgetIds);
	}
	public void update(Context context, int[] appWidgetIds) {
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.current_task_widget);
		
		Intent intent = new Intent(context, CurrentTaskWidget.class);
		intent.putExtra(WHAT, COMPLETE);

		Task task = mDatabaseHelper.getCurrentTask();
		if (task != null) {
			
			intent.putExtra(TASK_ID, task.getId());
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
					intent, PendingIntent.FLAG_CANCEL_CURRENT);
			
			int length = task.getWhat().length();
			if (length < 30) {
				views.setTextViewText(R.id.taskButton_BigText, task.getWhat());
				views.setViewVisibility(R.id.taskButton_BigText, View.VISIBLE);
				views.setViewVisibility(R.id.taskButton_MediumText, View.GONE);
				views.setViewVisibility(R.id.taskButton_SmallText, View.GONE);
				views.setOnClickPendingIntent(R.id.taskButton_BigText, pendingIntent);
			} else if (length < 50) {
				views.setTextViewText(R.id.taskButton_MediumText, task.getWhat());
				views.setViewVisibility(R.id.taskButton_BigText, View.GONE);
				views.setViewVisibility(R.id.taskButton_MediumText, View.VISIBLE);
				views.setViewVisibility(R.id.taskButton_SmallText, View.GONE);
				views.setOnClickPendingIntent(R.id.taskButton_MediumText, pendingIntent);
			} else {
				views.setTextViewText(R.id.taskButton_SmallText, task.getWhat());
				views.setViewVisibility(R.id.taskButton_BigText, View.GONE);
				views.setViewVisibility(R.id.taskButton_MediumText, View.GONE);
				views.setViewVisibility(R.id.taskButton_SmallText, View.VISIBLE);
				views.setOnClickPendingIntent(R.id.taskButton_SmallText, pendingIntent);
			}

			if (task.getParent() != null){
				views.setTextViewText(R.id.parentTextView, task.getParent().getWhat());
			} else {
				views.setTextViewText(R.id.parentTextView, "");
			}
			
			switch (task.getAsInteger(KEY_PRIORITY)) {
			case 1:
				views.setTextViewText(R.id.priorityTextView, "!");
				break;
			case 2:
				views.setTextViewText(R.id.priorityTextView, "!!");
				break;
			case 3:
				views.setTextViewText(R.id.priorityTextView, "!!!");
				break;
			default:
				views.setTextViewText(R.id.priorityTextView, "");
			}
			
			if (task.getAsLong(KEY_DUE_DATE) > 0){
				views.setTextViewText(R.id.dueDateTextView, 
						DateUtils.formatDateTime(context, task.getAsLong(KEY_DUE_DATE), 
								DateUtils.FORMAT_SHOW_YEAR ));
			} else {
				views.setTextViewText(R.id.dueDateTextView, "");
			}
			
			if (task.getAsLong(KEY_DUE_DATE) > 0
					|| task.getParent() != null
					|| task.getAsInteger(KEY_PRIORITY) == (1|2|3) ) {
				views.setViewVisibility(R.id.infoLinearLayout, View.VISIBLE);
			} else {
				views.setViewVisibility(R.id.infoLinearLayout, View.GONE);
			}
		} else {
			views.setTextViewText(R.id.taskButton_BigText, context.getResources()
					.getText(R.string.no_tasks));
			views.setViewVisibility(R.id.taskButton_BigText, View.VISIBLE);
			views.setViewVisibility(R.id.taskButton_MediumText, View.GONE);
			views.setViewVisibility(R.id.taskButton_SmallText, View.GONE);
			
			views.setViewVisibility(R.id.infoLinearLayout, View.GONE);
		}

		
		
		for (int id : appWidgetIds) {
			appWidgetManager.updateAppWidget(id, views);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		Log.d(TAG, "onReceive");
		switch (intent.getIntExtra(WHAT, 0)) {
		case COMPLETE:
			int id = intent.getIntExtra(TASK_ID, -1);
			Task task = mDatabaseHelper.getTask(id);
			if (task != null) {
				task.put(KEY_COMPLETED, true);
			}
		case UPDATE:
			update(context);
		}

	}

}
