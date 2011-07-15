package com.taskninjapro.android.TaskSettings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.taskninjapro.android.R;
import com.taskninjapro.android.app.BaseActivity;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.app.LifeCycleListener;
import com.taskninjapro.android.task.Task;

public class TaskSettings extends BaseActivity implements Constants {
	
	public Task mTask;
	
	private SharedPreferences mSettings;
	
	private HashMap<String,View> mViews = new HashMap<String, View>();
	
	List<LifeCycleListener> mOnPauseListeners = new LinkedList<LifeCycleListener>();
	
	LinearLayout mLinearLayout;
	
	// What
	EditText mWhatEditText;
	
	// ----------------------------------------------------------------------------------------------------
	// Life Cycle
	// ----------------------------------------------------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mSettings = getSharedPreferences(PREFS, 0);
		
		int id = getIntent().getIntExtra(_ID, -1);
		Task task = Task.get(id);
		if (task != null){
			mTask = task;
		} else {
			mTask = new Task("");
		}
		
		setContentView(R.layout.task_settings);
		
		mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
	}

	@Override
	public void onResume() {
		super.onResume();
		View view;
		boolean on;
		
		
		// WhatView
		view = mViews.get(KEY_WHAT);
		if (view !=null){
			if (mLinearLayout.indexOfChild(view) == -1){
				mLinearLayout.addView(view);
			}
		} else {
			new AsyncWhatView().execute(this);
		}
		
		// DueDateView
		view = mViews.get(DUE_DATE);
		on = mSettings.getBoolean(DUE_DATE, DUE_DATE_DEFAULT);
		if (view !=null) {
			if (on){
				if (mLinearLayout.indexOfChild(view) == -1){
					mLinearLayout.addView(view);
				}
			} else {
				mLinearLayout.removeView(view);
			}
		} else {
			if (on)
				new AsyncDueDateView().execute(this);
		}
		
		// AlertsView
		view = mViews.get(ALERTS);
		on = mSettings.getBoolean(ALERTS, ALERTS_DEFAULT);
		if (view !=null) {
			if (on){
				if (mLinearLayout.indexOfChild(view) == -1){
					mLinearLayout.addView(view);
				}
			} else {
				mLinearLayout.removeView(view);
			}
		} else {
			if (on)
				new AsyncAlertsView().execute(this);
		}
		
		// QueuingView
		view = mViews.get(QUEUING);
		on = mSettings.getBoolean(QUEUING, QUEUING_DEFAULT);
		if (view !=null) {
			if (on){
				if (mLinearLayout.indexOfChild(view) == -1){
					mLinearLayout.addView(view);
				}
			} else {
				mLinearLayout.removeView(view);
			}
		} else {
			if (on)
				new AsyncQueuingView().execute(this);
		}
		
		// SubtaskingView
		view = mViews.get(SUBTASKING);
		on = mSettings.getBoolean(SUBTASKING, SUBTASKING_DEFAULT);
		if (view !=null) {
			if (on){
				if (mLinearLayout.indexOfChild(view) == -1){
					mLinearLayout.addView(view);
				}
			} else {
				mLinearLayout.removeView(view);
			}
		} else {
			if (on)
				new AsyncSubtaskingView().execute(this);
		}
		
		
	}
	
	
	// WhatView
	private class AsyncWhatView extends AsyncTask<Activity, Void, WhatView> {
		@Override
		protected WhatView doInBackground(Activity... activity) {
			return new WhatView(activity[0], mTask);
		}
		@Override
		protected void onPostExecute(WhatView view) {
			mViews.put(KEY_WHAT, view);
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}
	
	// DueDateView
	private class AsyncDueDateView extends AsyncTask<Activity, Void, DueDateView> {
		@Override
		protected DueDateView doInBackground(Activity... activity) {
			return new DueDateView(activity[0], mTask);
		}
		@Override
		protected void onPostExecute(DueDateView view) {
			mViews.put(DUE_DATE, view);
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}
	
	// AlertsView
	private class AsyncAlertsView extends AsyncTask<Activity, Void, AlertsView> {
		@Override
		protected AlertsView doInBackground(Activity... activity) {
			return new AlertsView(activity[0], mTask);
		}
		@Override
		protected void onPostExecute(AlertsView view) {
			mViews.put(ALERTS, view);
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}
	
	// QueuingView
	private class AsyncQueuingView extends AsyncTask<Activity, Void, QueuingView> {
		@Override
		protected QueuingView doInBackground(Activity... activity) {
			return new QueuingView(activity[0], mTask);
		}
		@Override
		protected void onPostExecute(QueuingView view) {
			mViews.put(QUEUING, view);
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}
	
	// SubtasksView
	private class AsyncSubtaskingView extends AsyncTask<Activity, Void, SubtasksView> {
		@Override
		protected SubtasksView doInBackground(Activity... activity) {
			return new SubtasksView(activity[0], mTask);
		}
		@Override
		protected void onPostExecute(SubtasksView view) {
			mViews.put(SUBTASKING, view);
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		for (LifeCycleListener opl : mOnPauseListeners){
			opl.onPause();
		}
	}

	// ----------------------------------------------------------------------------------------------------

	
	// ----------------------------------------------------------------------------------------------------
	// Options Menu
	// ----------------------------------------------------------------------------------------------------
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.options_menu, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.settings:
//			startActivity(new Intent(this, AppSettings.class));
//			return true;
//		case R.id.selector:
//			startActivity(new Intent(this, QueueSelector.class));
//			return true;
//		case R.id.master:
//			startActivity(new Intent(this, MasterList.class));
//			return true;
//		case R.id.queue:
//			startActivity(new Intent(this, Queue.class));
//			return true;
//		default:
//			return true;
//		}
//	}
	// ----------------------------------------------------------------------------------------------------




}
