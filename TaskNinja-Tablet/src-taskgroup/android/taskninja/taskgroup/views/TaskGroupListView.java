package android.taskninja.taskgroup.views;

import android.app.Activity;
import android.taskninja.task.Task;
import android.taskninja.task.views.TaskListItemView;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.tools.OnActionListener;
import android.widget.LinearLayout;

public class TaskGroupListView extends LinearLayout implements OnActionListener<Task> {
	
	TaskGroup mGroup;
	OnActionListener<Task> mListener;
	
	LinearLayout mLinearLayout;

	public static TaskGroupListView getInstance(Activity activity, TaskGroup group, OnActionListener<Task> listener) {
		return new TaskGroupListView(activity, group, listener);
	}
	
	private TaskGroupListView(Activity activity, TaskGroup group, OnActionListener<Task> listener) {
		super(activity);
		mGroup = group;
		mListener = listener;
		
		activity.getLayoutInflater().inflate(com.rocksolidmobility.taskninja.android.R.layout.taskgroup_list, this);
		mLinearLayout = (LinearLayout) findViewById(com.rocksolidmobility.taskninja.android.R.id.linearLayout);
		
		for (Task task: mGroup){
			mLinearLayout.addView(TaskListItemView.getInstance(activity, task, this));
		}
	}

	@Override
	public void onAction(Task action) {
		mListener.onAction(action);
	}



}
