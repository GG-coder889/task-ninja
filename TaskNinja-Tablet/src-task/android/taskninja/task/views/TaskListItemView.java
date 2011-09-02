package android.taskninja.task.views;

import android.app.Activity;
import android.taskninja.dbmodel.Db_Listener;
import android.taskninja.task.Task;
import android.taskninja.task.TaskString;
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
		
		activity.getLayoutInflater().inflate(com.rocksolidmobility.taskninja.android.R.layout.task_list_item, this);
		
		mPrimaryText = (TextView) findViewById(com.rocksolidmobility.taskninja.android.R.id.primaryText);
		mPrimaryText.setText(mTask.toString());
		mSecondaryText = (TextView) findViewById(com.rocksolidmobility.taskninja.android.R.id.secondaryText);
		mSecondaryText.setText(mTask.getString(TaskString.notes));
		mCompleteButton = (CheckBox) findViewById(com.rocksolidmobility.taskninja.android.R.id.completeButton);
		
		setOnClickListener(this);
		
		mTask.addListener(new Db_Listener() {
			@Override
			public void onChange(Enum key) {
				if (key.equals(TaskString.title)){
					mPrimaryText.setText(mTask.toString());
				}
				if (key.equals(TaskString.notes)){
					String text = mTask.getString(TaskString.notes);
					if (text == null || text.equals("")){
						mSecondaryText.setText(text);
						mSecondaryText.setVisibility(GONE);
					} else {
						mSecondaryText.setText(text);
						mSecondaryText.setVisibility(VISIBLE);
					}
					
				}
			}
		});
		
	}
	

	@Override
	public void onClick(View arg0) {
		mListener.onAction(mTask);
	}
}
