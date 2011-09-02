package android.taskninja.taskgroup.views;

import android.app.Activity;
import android.content.Context;
import android.taskninja.taskgroup.TaskGroup;
import android.widget.LinearLayout;

public class TaskGroupFooterView extends LinearLayout {

	TaskGroup mGroup;

	public static TaskGroupFooterView getInstance(Activity activity, TaskGroup group) {
		return new TaskGroupFooterView(activity, group);
	}
	
	private TaskGroupFooterView(Activity activity, TaskGroup group) {
		super(activity);
		mGroup = group;
	}

}
