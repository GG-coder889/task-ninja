package android.taskninja.mobile.TaskSettings;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.taskninja.mobile.app.App;
import android.taskninja.mobile.app.Constants;
import android.taskninja.mobile.app.LifeCycleListener;
import android.taskninja.mobile.queue.CurrentTaskWidget;
import android.taskninja.mobile.task.Task;
import android.taskninja.mobile.views.TaskListView;
import android.taskninja.mobile.views.TaskView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taskninjapro.android.R;
import com.taskninjapro.android.task.TaskInteger;
import com.taskninjapro.android.task.TaskIntegerList;

public class SubtasksView extends LinearLayout implements LifeCycleListener, Constants {
	
	Task mTask;
	Activity mActivity;
	
	private List<Integer> mTempTaskIds;
	
	private EditText mEditText;
	private TaskListView mTaskList;

	public SubtasksView(Activity activity, Task task) {
		super(activity);
		
		mTask = task;
		mActivity = activity;
		
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.task_settings_subtasks, this);
		setOrientation(VERTICAL);
		
		setPadding(5, 5, 5, 5);
		
		mTaskList = new TaskListView(mActivity);
		addView(mTaskList);
		
		mTempTaskIds = mTask.getIntegerList(TaskIntegerList.KEY_TASKS);
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
			Task subtask = new Task(what);
			subtask.put(TaskInteger.KEY_PARENT, mTask.getId());
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
		List<Integer> list = new LinkedList<Integer>();
		
		int taskCount = mTaskList.getChildCount();
		for (int i = 0; i < taskCount; i++) {
			Task task = ((LocalTaskView) mTaskList.getChildAt(i)).mTask;
			list.add(task.getId());
		}

		// Put and commit tasks string to the parent task
		mTask.put(TaskIntegerList.KEY_TASKS, list);

		// Update current task widget
		Intent intent = new Intent(App.getContext(), CurrentTaskWidget.class);
		intent.putExtra(CurrentTaskWidget.WHAT, CurrentTaskWidget.UPDATE);
		mActivity.sendBroadcast(intent);
		
	}
	
	private class AsyncLocalTaskView extends AsyncTask<Void, Void, TaskView> {	
		@Override
		protected TaskView doInBackground(Void ... voids) {
			for (Integer id: mTempTaskIds){
				Task task = Task.get(id);
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
		
		@Override
		protected void onDelete() {
			super.onDelete();
			mTaskList.updatePositions();
		}

	}
	

}
