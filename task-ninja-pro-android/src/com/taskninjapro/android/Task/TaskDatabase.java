package com.taskninjapro.android.Task;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.taskninjapro.android.alarms.CurrentTaskWidget;
import com.taskninjapro.android.app.App;
import com.taskninjapro.android.app.Constants;

public class TaskDatabase implements Constants {

	private static final String TAG = "TaskDatabase";
	public static HashMap<Integer, Task> mTasks;
	public static List<Integer> mIds;
	public static TaskDatabase mDatabaseHelper;

	public TaskSQLiteHelper mSQLiteHelper;
	private SharedPreferences mSettings;
	private Context mContext;

	private TaskDatabase(Context context) {
		mContext = context;

		mSettings = context.getSharedPreferences(PREFS, PREFS_MODE);

		mSQLiteHelper = TaskSQLiteHelper.getInstance(context);
		if (mIds == null) {
			mIds = mSQLiteHelper.getIds();
		}
		if (mTasks == null) {
			mTasks = new HashMap<Integer, Task>();
		}

	}
	
	public static TaskDatabase getInstance(Context context) {
		if (mDatabaseHelper == null) {
			mDatabaseHelper = new TaskDatabase(context);
		}
		return mDatabaseHelper;
	}

	public void add(Task task) {
		mTasks.put(task.getId(), task);
		writeCycle();
	}

	public int getNewID() {
		Integer newId = 1;
		for (int i = 0; i < mIds.size(); i++) {
			Integer id = mIds.get(i);
			if (id - newId > 0) {
				newId = id - 1;
				mIds.add(i, newId);
				return newId;
			} else {
				newId = id + 1;
			}
		}
		mIds.add(newId);
		return newId;
	}

	public Task getTask(int id) {
		Log.d(TAG, "getting task: id=" + id);
		if (id < 1) {
			return null;
		}
		Task task = mTasks.get(id);
		if (task == null)
			task = mSQLiteHelper.getTask(id);
		if (task == null) {
			Log.d(TAG, "failed to get task: id=" + id);
		} else {
			Log.d(TAG, "got task: id="+task.getId()+" what="+task.getAsString(KEY_WHAT));
		}
		return task;
	}

	public Task getCurrentTask() {
		LinkedHashSet<Integer> queue = getQueue();
		for (Integer id : queue) {
			Task task = getTask(id);
			if (task != null && !task.getAsBoolean(KEY_COMPLETED))
				return task;
		}
		return null;
	}

	public LinkedHashSet<Integer> getQueue() {
		LinkedHashSet<Integer> queue = new LinkedHashSet<Integer>();
		String queueString = mSettings.getString(QUEUE, "");
		if (queueString.equals("")) {
			return queue;
		}
		String[] queueArray = queueString.split(",");
		for (String s : queueArray) {
			try {
				Task task = getTask(Integer.valueOf(s));
				if (task != null && !task.needsDelete()) {
					queue.add(task.getId());
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return queue;
	}
	
	public void setQueue(LinkedHashSet<Integer> queue) {
		StringBuilder queueString = new StringBuilder();

		for (Integer id : queue) {
			Task task = getTask(id);
			if (task != null)
				queueString.append(',').append(task.getId());
		}
		queueString.replace(0, 1, "");
		
		mSettings.edit().putString(QUEUE, queueString.toString()).commit();
		
		// Update current task widget
		Intent intent = new Intent(App.getContext(), CurrentTaskWidget.class);
		intent.putExtra(CurrentTaskWidget.WHAT, CurrentTaskWidget.UPDATE);
		mContext.sendBroadcast(intent);
	}

	boolean mWriting = false;
	
	public void writeCycle() {
		if (!mWriting) {
			Set<Integer> keys = mTasks.keySet();
			for (Integer key : keys) {
				Task task = mTasks.get(key);
				if (task.needsUpdate()) {
					mWriting = true;
					task.setNeedsUpdate(false);
					new AsyncUpdate().execute(task);
					break;
				}
			}
		}
	}

	private class AsyncUpdate extends AsyncTask<Task, Void, List<Integer>> {
		@Override
		protected List<Integer> doInBackground(Task... tasks) {
			Log.d(TAG, "AsyncInsert Start: tasks");
			return mSQLiteHelper.update(tasks);
		}

		@Override
		protected void onPostExecute(List<Integer> ids) {
			Log.d(TAG, "AsyncInsert End: " + ids);
			for (Integer id : ids) {
				if (id < 0) {
					Task task = mTasks.get(-id);
					if (task != null) {
						task.setNeedsUpdate(true);
					}
				}
			}
			mWriting = false;
			writeCycle();
		}
	}

	public List<Task> getTasks() {
		List<Task> tasks = new LinkedList<Task>();
		for (int id : mIds) {
			Task task = getTask(id);
			if (	task != null && 
					!task.getAsBoolean(KEY_PARENT) && 
					!task.needsDelete()) {
				tasks.add(task);
			}
		}
		return tasks;
	}


	
//	public void setQueue(LinkedHashSet<Task> queue) {
//		StringBuilder queueString = new StringBuilder();
//
//		for (Task task : queue) {
//			queueString.append(',').append(task.getId());
//		}
//		queueString.replace(0, 1, "");
//		
//		mSettings.edit().putString(QUEUE, queueString.toString()).commit();
//		
//		// Update current task widget
//		Intent intent = new Intent(App.getContext(), CurrentTaskWidget.class);
//		intent.putExtra(CurrentTaskWidget.WHAT, CurrentTaskWidget.UPDATE);
//		mContext.sendBroadcast(intent);
//	}



}
