package com.taskninjapro.android.QueueSelector;

import java.util.LinkedHashSet;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.taskninjapro.android.R;
import com.taskninjapro.android.AppSettings.AppSettings;
import com.taskninjapro.android.MasterList.MasterList;
import com.taskninjapro.android.Queue.Queue;
import com.taskninjapro.android.Task.Task;
import com.taskninjapro.android.Task.TaskDatabase;
import com.taskninjapro.android.app.App;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.views.TaskListView;
import com.taskninjapro.android.views.TaskView;

public class QueueSelector extends Activity implements Constants {


	// Static
	private static final String TAG = "QueueSelector";

	// Data Helpers/Interfaces
	private TaskDatabase mDatabaseInterface;
	private SharedPreferences mSettings;
	
	LinkedHashSet<Integer> mQueue;
	
	// Views
	private TaskListView mTaskList ;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.queue_selector);

		// Initialize Data Helpers/Interfaces
		mDatabaseInterface = TaskDatabase.getInstance(App.getContext());
		mSettings = getSharedPreferences(PREFS, 0);
		
		// Setup The linear layout of the task scroll view
		ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
		mTaskList = new TaskListView(this);
		sv.addView(mTaskList);
		
	}

	@Override
	public void onResume() {
		super.onResume();

		mQueue = mDatabaseInterface.getQueue();
		
		mTaskList.removeAllViews();

		List<Task> tasks = mDatabaseInterface.getTasks();
		for (Task task : tasks) {
			if (!task.getAsBoolean(KEY_COMPLETED) && task.getParent() == null) {
				new AsyncTaskView().execute(task);
			}
		}
		
	}

	

	@Override
	public void onPause() {
		super.onPause();
		
		for (int i = 0; i < mTaskList.getChildCount(); i++){
			
			TaskView taskView = (TaskView) mTaskList.getChildAt(i);
			Task task = taskView.mTask;
			
			if (taskView.isSelected()){
				LinkedHashSet<Integer> subtaskIds = task.getSubtaskIds();
				for (int id: subtaskIds){
					Task subtask = mDatabaseInterface.getTask(id);
					if (subtask != null){
						mQueue.add(subtask.getId());
					}
				}
				mQueue.add(task.getId());
			} else {
				mQueue.remove(task.getId());
				
				if (taskView.mSubtaskList != null){
					for (int subtaskIndex = 0; subtaskIndex < taskView.mSubtaskList.getChildCount(); subtaskIndex++){
						TaskView subtaskView = (TaskView) taskView.mSubtaskList.getChildAt(subtaskIndex);
						Task subtask = subtaskView.mTask;
						if (subtaskView.isSelected()){
							mQueue.add(subtask.getId());
						} else {
							mQueue.remove(subtask.getId());
						}
					}
				}				
			}
		}
		
		mDatabaseInterface.setQueue(mQueue);
		
	}


	private class AsyncTaskView extends AsyncTask<Task, Void, LocalTaskView> {
		@Override
		protected LocalTaskView doInBackground(Task... tasks) {
			return new LocalTaskView(mTaskList, tasks[0]);
		}
		@Override
		protected void onPostExecute(LocalTaskView view) {
			mTaskList.addView(view);
			view.setSelected(mQueue.contains(view.mTask.getId()));
		}
	}
	
	private class LocalTaskView extends TaskView {

		public LocalTaskView(TaskListView taskList, Task task) {
			super(taskList, task);
			
			this.mTaskHeader.mCompleteButton.setVisibility(GONE);
			this.mTaskHeader.mTaskToggleButton.setOnCheckedChangeListener(null); 
			this.mTaskHeader.mTaskToggleButton.setOnClickListener(this);
			if (this.mTask.getSubtaskIds().size() != 0){
				this.setSubtasksShown(true);
			}
			
			this.setSelected(mQueue.contains(this.mTask.getId()));
		}
		
		
		@Override
		protected TaskView getSubtaskView(TaskListView taskList, Task t) {
			if (t.getAsBoolean(KEY_COMPLETED)){
				return null;
			}
			return new LocalTaskView(taskList, t);
		}
		
		@Override
		public void onClick(View v){
			this.setSelected(!this.isSelected());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		menu.removeItem(R.id.selector);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, AppSettings.class));
			return true;
		case R.id.selector:
			startActivity(new Intent(this, QueueSelector.class));
			return true;
		case R.id.master:
			startActivity(new Intent(this, MasterList.class));
			return true;
		case R.id.queue:
			startActivity(new Intent(this, Queue.class));
			return true;
		default:
			return true;
		}
	}
	// ----------------------------------------------------------------------------------------------------

}
