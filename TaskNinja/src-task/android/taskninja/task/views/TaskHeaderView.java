package android.taskninja.task.views;

import android.app.Activity;
import android.taskninja.dbmodel.Db_Listener;
import android.taskninja.task.Task;
import android.taskninja.task.TaskString;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskHeaderView extends LinearLayout {
	
	private Activity mActivity;
	private Task mTask;
	
	private TextView mPrimaryText;
	private TextView mSecondaryText;
	
	public static TaskHeaderView getIntance(Activity activity, Task task){
		return new TaskHeaderView(activity, task);
	}

	private TaskHeaderView(Activity activity, Task task) {
		super(activity);
		
		mActivity = activity;
		mTask = task;
		
		mActivity.getLayoutInflater().inflate(android.taskninja.R.layout.header, this);
		mPrimaryText = (TextView) findViewById(android.taskninja.R.id.primaryText);
		mSecondaryText = (TextView) findViewById(android.taskninja.R.id.secondaryText);
		
		mPrimaryText.setText(mTask.toString());
		mSecondaryText.setText(mTask.getString(TaskString.notes));
		
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

}
