package com.taskninjapro.android.TaskSettings;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.taskninjapro.android.R;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.app.LifeCycleListener;
import com.taskninjapro.android.task.Task;
import com.taskninjapro.android.task.TaskLong;

public class DueDateView extends LinearLayout implements OnClickListener, LifeCycleListener, Constants {
	
	Task mTask;
	Activity mActivity;
	
	Button mButton;

	private Calendar mCalendar = Calendar.getInstance();
	private boolean mDueDateHasChanged = false;
	
	public DueDateView(Activity activity, Task task) {
		super(activity);
		
		mTask = task;
		mActivity = activity;
		
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.task_settings_due_date, this);
		
		mButton = (Button) findViewById(R.id.button);
		mButton.setOnClickListener(this);
		mButton.setSelected(true);
		
		if (mTask.getLong(TaskLong.KEY_DUE_DATE) > 0){
			long millis = mTask.getLong(TaskLong.KEY_DUE_DATE);
			mCalendar.setTimeInMillis(millis);
			mButton.setText(getDateText());
		} 
		
	}
	
	private String getDateText(){
		long millis = mCalendar.getTimeInMillis();
		String text =DateUtils.formatDateTime(mActivity, millis, 
				DateUtils.FORMAT_SHOW_YEAR );
		return text;
	}
	
	private DatePickerDialog.OnDateSetListener mDueDateSetListener = 
		new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            	mCalendar.set(year, monthOfYear, dayOfMonth);
            	mButton.setText(getDateText());
            	mDueDateHasChanged = true;
            }
        };

	public void onClick(View v) {
		Dialog dialog = 
			new DatePickerDialog(mActivity, 
					mDueDateSetListener, 
					mCalendar.get(Calendar.YEAR), 
					mCalendar.get(Calendar.MONTH), 
					mCalendar.get(Calendar.DAY_OF_MONTH)
			);
		
		dialog.show();
	}

	public void onPause() {
		if (mDueDateHasChanged)
			mTask.put(TaskLong.KEY_DUE_DATE, mCalendar.getTimeInMillis());
		
	}

}
