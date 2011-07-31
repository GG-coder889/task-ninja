package android.taskninja.taskgroup.views;

import android.app.Activity;
import android.content.Context;
import android.taskninja.R;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.taskgroup.TaskGroup_String;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskGroupHeaderView extends LinearLayout {
	
	private TaskGroup mGroup;
	
	public static TaskGroupHeaderView getInstance(Activity activity, TaskGroup group){
		return new TaskGroupHeaderView(activity, group);
	}

	private TaskGroupHeaderView(Activity activity, TaskGroup group) {
		super(activity);
		
		mGroup = group;
		
		inflate(getContext(), R.layout.taskgroup_header, this);
		
		TextView primary = (TextView) findViewById(R.id.primaryText);
		primary.setText(mGroup.getString(TaskGroup_String.title));

	}


}
