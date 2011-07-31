package android.taskninja.task.views;

import android.app.Activity;
import android.taskninja.task.Task;
import android.taskninja.tools.OnActionListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskListItemView extends LinearLayout implements OnClickListener {
	
	Task mTask;
	OnActionListener<Task> mListener;
	TextView mPrimaryText;
	TextView mSecondaryText;
	CheckBox mCompleteButton;

	public static TaskListItemView getInstance(Activity activity, Task task, OnActionListener<Task> listener) {
		return new TaskListItemView(activity, task, listener);
	}
	
	private TaskListItemView(Activity activity, Task task, OnActionListener<Task> listener) {
		super(activity);
		mTask = task;
		mListener = listener;
		
		activity.getLayoutInflater().inflate(android.taskninja.R.layout.task_list_item, this);
		
		mPrimaryText = (TextView) findViewById(android.taskninja.R.id.primaryText);
		mPrimaryText.setText(mTask.toString());
		mSecondaryText = (TextView) findViewById(android.taskninja.R.id.secondaryText);
		mCompleteButton = (CheckBox) findViewById(android.taskninja.R.id.completeButton);
		
		setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}



}
