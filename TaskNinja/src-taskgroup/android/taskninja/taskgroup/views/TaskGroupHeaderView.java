package android.taskninja.taskgroup.views;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.taskninja.R;
import android.taskninja.task.views.TaskDialogNew;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.taskgroup.TaskGroup_String;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskGroupHeaderView extends LinearLayout implements OnClickListener {
	
	private TaskGroup mGroup;
	private Activity mActivity;
	
	public static TaskGroupHeaderView getInstance(Activity activity, TaskGroup group){
		return new TaskGroupHeaderView(activity, group);
	}

	private TaskGroupHeaderView(Activity activity, TaskGroup group) {
		super(activity);
		
		mGroup = group;
		mActivity = activity;
		
		inflate(getContext(), R.layout.taskgroup_header, this);
		
		TextView primary = (TextView) findViewById(R.id.primaryText);
		primary.setText(mGroup.getString(TaskGroup_String.title));
		
		findViewById(R.id.addButton).setOnClickListener(this);
		findViewById(R.id.editButton).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addButton:
			onNewTask();
			break;
		case R.id.editButton:
			onEditGroup();
			break;
		}
		
	}
	
	private void onEditGroup() {
		FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
	    DialogFragment frag = TaskGroupEditDialog.getInstance(mGroup);
	    frag.show(ft, "dialog");
	}

	private void onNewTask(){
		FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
	    DialogFragment frag = TaskDialogNew.getInstance(mGroup);
	    frag.show(ft, "dialog");
	}


}
