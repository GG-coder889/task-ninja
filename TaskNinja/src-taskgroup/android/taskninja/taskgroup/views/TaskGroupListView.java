package android.taskninja.taskgroup.views;

import android.app.Activity;
import android.taskninja.task.Task;
import android.taskninja.task.views.TaskListItemView;
import android.taskninja.taskgroup.TaskGroup;
import android.widget.LinearLayout;

public class TaskGroupListView extends LinearLayout {
	
	TaskGroup mGroup;
	
	LinearLayout mLinearLayout;

	public static TaskGroupListView getInstance(Activity activity, TaskGroup group) {
		return new TaskGroupListView(activity, group);
	}
	
	private TaskGroupListView(Activity activity, TaskGroup group) {
		super(activity);
		mGroup = group;
		
		activity.getLayoutInflater().inflate(android.taskninja.R.layout.taskgroup_list, this);
		mLinearLayout = (LinearLayout) findViewById(android.taskninja.R.id.linearLayout);
		
		for (Task task: mGroup){
			mLinearLayout.addView(TaskListItemView.getInstance(activity, task));
		}
	}



}
