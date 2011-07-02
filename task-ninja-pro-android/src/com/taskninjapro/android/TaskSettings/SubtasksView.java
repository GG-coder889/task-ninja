package com.taskninjapro.android.TaskSettings;

import java.util.LinkedHashSet;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taskninjapro.android.R;
import com.taskninjapro.android.Task.Task;
import com.taskninjapro.android.Task.TaskDatabase;
import com.taskninjapro.android.alarms.CurrentTaskWidget;
import com.taskninjapro.android.app.App;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.app.LifeCycleListener;
import com.taskninjapro.android.views.TaskListView;
import com.taskninjapro.android.views.TaskView;

public class SubtasksView extends LinearLayout implements LifeCycleListener, Constants {
	
	Task mTask;
	Activity mActivity;
	
	private TaskDatabase mTaskDatabase;
	
	private LinkedHashSet<Integer> mTempTaskIds;
	
	private EditText mEditText;
	private TaskListView mTaskList;

	public SubtasksView(Activity activity, Task task) {
		super(activity);
		
		mTask = task;
		mActivity = activity;
		
		mTaskDatabase = TaskDatabase.getInstance(getContext());
		
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.task_settings_subtasks, this);
		setOrientation(VERTICAL);
		
		setPadding(5, 5, 5, 5);
		
		mTaskList = new TaskListView(mActivity);
		addView(mTaskList);
		
		mTempTaskIds = mTask.getSubtaskIds();
		new AsyncLocalTaskView().execute((Void[]) null);
		
		mEditText = (EditText) findViewById(R.id.editText);
		mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView textView, int arg1,
					KeyEvent arg2) {
				createTask();
				return true;
			}
		});
		mEditText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createTask();
			}
		});
		mEditText.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		
			public void afterTextChanged(Editable editable) {
				int l = editable.length();
				if (l > MAX_TITLE_LENGTH) {
					editable.delete(l - 1, l);
				}
			}
		});

		
	}
	
	private void createTask() {

		// If the edit text is blank do nothing
		if (mEditText.getText().length() != 0) {
			CharSequence what = mEditText.getText();

			// Each task comes in at the front of the list
			Task subtask = new Task(what, App.getContext());
			subtask.put(KEY_PARENT, mTask.getAsInteger(_ID));
			LocalTaskView taskView = new LocalTaskView(mTaskList, subtask);
			mTaskList.addView(taskView, 0);
			updatePositions();
		
			// Reset the editText to blank
			mEditText.setText("");
		}
	}
	
	private void updatePositions(){
		for (int i = 0; i < mTaskList.getChildCount(); i++){
			((TaskView) mTaskList.getChildAt(i)).setPosition(i+1);
		}
	}

	public void onPause() {
		// Make a string of task ids in taskString separated by ','
		int taskCount = mTaskList.getChildCount();
		StringBuilder tasksString = new StringBuilder();
		for (int i = 0; i < taskCount; i++) {
			if (i != 0) {
				tasksString.append(',');
			}
			Task task = ((LocalTaskView) mTaskList.getChildAt(i)).mTask;
			tasksString.append(task.getId());
		}

		// Put and commit tasks string to the parent task
		mTask.put(KEY_TASKS, tasksString.toString());

		// Update current task widget
		Intent intent = new Intent(App.getContext(), CurrentTaskWidget.class);
		intent.putExtra(CurrentTaskWidget.WHAT, CurrentTaskWidget.UPDATE);
		mActivity.sendBroadcast(intent);
		
	}
	
	private class AsyncLocalTaskView extends AsyncTask<Void, Void, TaskView> {	
		@Override
		protected TaskView doInBackground(Void ... voids) {
			for (Integer id: mTempTaskIds){
				Task task = mTaskDatabase.getTask(id);
				if (task != null){
					mTempTaskIds.remove(id);
					return new LocalTaskView(mTaskList, task);
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(TaskView view) {
			if (view != null){
				mTaskList.addView(view);
				view.setPosition(mTaskList.indexOfChild(view)+1);
				new AsyncLocalTaskView().execute((Void[]) null);
			}
			
		}
	}
	
	private class LocalTaskView extends TaskView {
		
		public LocalTaskView(TaskListView taskList, Task task) {
			super(taskList, task);
			
			setPadding(0, 2, 0, 2);
			
			mTaskHeader.mMoreButton.setVisibility(GONE);
			mTaskHeader.mSubsImageButton.setVisibility(GONE);
			
			if (mTask.getParent() != null){
				TextView parentView = (TextView) findViewById(R.id.parentTextView);
				if (parentView!=null){
					parentView.setVisibility(GONE);
				}
			}
			
		}

	}
	

}
