package android.taskninja.mobile.MasterList;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.taskninja.mobile.app.App;
import android.taskninja.mobile.app.BaseActivity;
import android.taskninja.mobile.app.Constants;
import android.taskninja.mobile.queue.CurrentTaskWidget;
import android.taskninja.mobile.task.Task;
import android.taskninja.mobile.views.TaskListView;
import android.taskninja.mobile.views.TaskView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.taskninjapro.android.R;
import com.taskninjapro.android.task.TaskBool;
import com.taskninjapro.android.task.TaskLong;

public class MasterList extends BaseActivity implements Constants {

	// ----------------------------------------------------------------------------------------------------
	// Members
	// ----------------------------------------------------------------------------------------------------
	private SharedPreferences mSetting;
	
	private EditText mNewTaskEditText;
	private ScrollView mScrollView;
	private TaskListView mTaskList;
	
	private LinkedHashSet<Task> mTempTasks;

	// ----------------------------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------------------------
	// Life Cycle
	// ----------------------------------------------------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.master_list);

		mSetting = getSharedPreferences(PREFS, PREFS_MODE);
		
		mScrollView = (ScrollView) findViewById(R.id.scrollView);
		mTaskList = new TaskListView(this);
		mScrollView.addView(mTaskList);

		// Setup mNewTaskEditText
		mNewTaskEditText = (EditText) findViewById(R.id.newTaskEditText);
		mNewTaskEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		mNewTaskEditText
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					public boolean onEditorAction(TextView textView, int arg1,
							KeyEvent arg2) {
						createTask();
						return true;
					}
				});
		mNewTaskEditText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createTask();
			}
		});
		mNewTaskEditText.addTextChangedListener(new TextWatcher() {
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
		
//		if (getLastNonConfigurationInstance() == null){
//			
//			
//		}
//		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY|InputMethodManager.);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		mTaskList.removeAllViews();
		
		mTempTasks = Task.getAll();
		for (Task task: mTempTasks){
			new AsyncLocalTaskView().execute(task);
			mTempTasks.remove(task);
			break;
		}
		
	}

	@Override
	public void onPause() {
		super.onPause();

		// Update current task widget
		Intent intent = new Intent(App.getContext(), CurrentTaskWidget.class);
		intent.putExtra(CurrentTaskWidget.WHAT, CurrentTaskWidget.UPDATE);
		sendBroadcast(intent);
	}

	// ----------------------------------------------------------------------------------------------------

	private void createTask() {
		// If the edit text is blank do nothing
		if (mNewTaskEditText.getText().length() != 0) {
			CharSequence what = mNewTaskEditText.getText();
			// Each task comes in at the front of the list
			Task task = new Task(what);
			LocalTaskView taskView = new LocalTaskView(mTaskList, task);
			mTaskList.addView(taskView, 0);
			// Reset the editText to blank
			mNewTaskEditText.setText("");
		}
	}
	
	private class AsyncLocalTaskView extends AsyncTask<Task, Void, List<TaskView>> {	
		@Override
		protected List<TaskView> doInBackground(Task ... tasks) {
			List<TaskView> views = new LinkedList<TaskView>();
			for (Task task: tasks){
				views.add(new LocalTaskView(mTaskList, task));
			}
			return views;
		}
		@Override
		protected void onPostExecute(List<TaskView> views) {
			
			for (TaskView view: views) {
				mTaskList.addView(view);
			}
			
			for (Task task: mTempTasks){
				new AsyncLocalTaskView().execute(task);
				mTempTasks.remove(task);
				break;
			}
			
			
		}
	}
	
	private class LocalTaskView extends TaskView {
//
//		private ImageButton mDeleteButton;
//		private ImageButton mMoreButton;
		
		public LocalTaskView(TaskListView taskList, Task task) {
			super(taskList, task);
			
			this.mTaskHeader.mUpButton.setVisibility(GONE);
			this.mTaskHeader.mDownButton.setVisibility(GONE);
		}
		
		@Override
		protected TaskView getSubtaskView(TaskListView taskList, Task t) {
			return new LocalSubtaskView(taskList, t);
		}
		
//		public void onCheckedChanged(CompoundButton button, boolean checked) {
//			switch (button.getId()){
//			case R.id.subsToggleButton:
//				setSubtasksShown(checked);
//				if (checked){
//					mTask.put(KEY_COMPLETED, false);
//					update();
//				}
//			}
//			
//		}
		
		private class LocalSubtaskView extends TaskView {

			public LocalSubtaskView(TaskListView taskList, Task task) {
				super(taskList, task);
				
				this.mTaskHeader.mCompleteButton.setVisibility(GONE);
				this.mTaskHeader.mTaskToggleButton.setOnCheckedChangeListener(null); 
				this.mTaskHeader.mTaskToggleButton.setOnClickListener(this);
			}

			@Override
			public void onClick(View v){
				switch (v.getId()){
				case R.id.taskToggleButton:
					
					boolean completed = mTask.getBool(TaskBool.KEY_COMPLETED);
					if (completed){
						this.mTask.put(TaskLong.KEY_COMPLETED, 0L);
					} else {
						this.mTask.put(TaskLong.KEY_COMPLETED, System.currentTimeMillis());
					}
					
					this.mTaskHeader.onCompleted(mTask.getBool(TaskBool.KEY_COMPLETED));
					
//					v.setId(R.id.completeButton);
//					this.mTaskHeader.onClick(v);
					break;
				default:
					super.onClick(v);
				}
			}
			
		}
		
	}
	
	// ----------------------------------------------------------------------------------------------------
	// Options Menu
	// ----------------------------------------------------------------------------------------------------
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.options_menu, menu);
//		menu.removeItem(R.id.master);
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
