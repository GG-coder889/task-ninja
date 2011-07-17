package android.taskninja.mobile.Queue;

import java.util.LinkedHashSet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.taskninja.mobile.app.App;
import android.taskninja.mobile.task.Task;


public class QueueManager {
	
	private static final String QUEUE = "QueueManager.QUEUE";
	
	private static final SharedPreferences mSettings = App.getContext().getSharedPreferences(App.PREFS, Context.MODE_PRIVATE);

	public static LinkedHashSet<Task> getQueue() {
		
		LinkedHashSet<Task> queue = new LinkedHashSet<Task>();
		String queueString = mSettings.getString(QUEUE, "");
		
		if (queueString.equals("")) {
			return queue;
		}
		
		String[] queueArray = queueString.split(",");
		for (String s : queueArray) {
			try {
				Task task = Task.get(Integer.valueOf(s));
				
				if (task != null && !task.isDeleted()) {
					queue.add(task);
				}
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return queue;
	}

	public static void setQueue(LinkedHashSet<Task> newQueue) {
		StringBuilder queueString = new StringBuilder();

		for (Task task : newQueue) {
			if (task != null)
				queueString.append(',').append(task.getId());
		}
		queueString.replace(0, 1, "");

		mSettings.edit().putString(QUEUE, queueString.toString()).commit();

		// Update current task widget
		Intent intent = new Intent(App.getContext(), CurrentTaskWidget.class);
		intent.putExtra(CurrentTaskWidget.WHAT, CurrentTaskWidget.UPDATE);
		App.getContext().sendBroadcast(intent);
	}
	
	public static Task getCurrentTask() {
		LinkedHashSet<Task> queue = getQueue();
		for (Task task : queue) {
			if (task != null && !task.completed())
				return task;
		}
		return null;

	}
}
