package android.taskninja.mobile.Queue;

import java.util.LinkedHashSet;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.taskninja.mobile.BaseActivity;
import android.taskninja.mobile.app.Constants;
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

public class QueueActivity extends BaseActivity implements Constants {

	// ----------------------------------------------------------------------------------------------------
	// Members
	// ----------------------------------------------------------------------------------------------------

	// Static
	private static final String TAG = "TasksPlannerActivity";

	// Views
	private EditText mNewTaskEditText;
	private ScrollView mScrollView;
	private TaskListView mTaskList;

	// Data Helpers/Interfaces
	private SharedPreferences mSettings;

	
	private LinkedHashSet<Task> mTempTasks;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.queue);

		// Initialize Data Helpers/Interfaces
		mSettings = getSharedPreferences(PREFS, 0);

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
		
	}

	@Override
	public void onResume() {
		super.onResume();

		mTaskList.removeAllViews();

		mTempTasks = QueueManager.getQueue();
		new AsyncLocalTaskView().execute((Void[])null);
	}

	@Override
	public void onPause() {
		super.onPause();
		
		if (mTempTasks.size() == 0){
			LinkedHashSet<Task> queue = new LinkedHashSet<Task>();
			int taskCount = mTaskList.getChildCount();
			for (int i = 0; i < taskCount; i++) {
				Task task = ((LocalTaskView) mTaskList.getChildAt(i)).mTask;
				queue.add(task);
			}
			QueueManager.setQueue(queue);
		}
		
	}

	
	private void createTask() {

		// If the edit text is blank do nothing
		if (mNewTaskEditText.getText().length() != 0) {
			CharSequence what = mNewTaskEditText.getText();

			// Each task comes in at the front of the list
			Task task = new Task(what);
			LocalTaskView taskView = new LocalTaskView(mTaskList, task);
			mTaskList.addView(taskView, 0);
			mTaskList.updatePositions();
			// Reset the editText to blank
			mNewTaskEditText.setText("");
		}
	}
	
	
	private class AsyncLocalTaskView extends AsyncTask<Void, Void, TaskView> {	
		@Override
		protected TaskView doInBackground(Void ... voids) {
			for (Task task: mTempTasks){
				if (task != null){
					mTempTasks.remove(task);
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
			
			mTaskHeader.mMoreButton.setVisibility(GONE);
			mTaskHeader.mSubsImageButton.setVisibility(GONE);
			if (mTask.getParent() != null){
				mTaskHeader.mParentTextView.setVisibility(VISIBLE);
			} 
		}
		
		@Override
		protected void onDelete() {
			this.mTaskList.removeView(this);
			mTaskList.updatePositions();
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
//		menu.removeItem(R.id.queue);
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
