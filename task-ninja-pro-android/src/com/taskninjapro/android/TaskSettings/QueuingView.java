package com.taskninjapro.android.TaskSettings;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.taskninjapro.android.R;
import com.taskninjapro.android.alarm.Alarm;
import com.taskninjapro.android.alarm.AlarmBool;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.app.LifeCycleListener;
import com.taskninjapro.android.task.Task;
import com.taskninjapro.android.task.TaskBool;
import com.taskninjapro.android.task.TaskLong;

public class QueuingView extends LinearLayout implements OnClickListener, LifeCycleListener, Constants, OnCheckedChangeListener {
	
	private static final String TAG = "QueuingView";
	
	Task mTask;
	Activity mActivity;
	private boolean mSingleDateHasChanged = false;
	Calendar mSingleDate = Calendar.getInstance();
	private boolean mmRecurringDateHasChanged = false;
	Calendar mRecurringDate = Calendar.getInstance();
	
	// Single 
	ToggleButton mSingleToggleButton;
	LinearLayout mSingleLinearLayout;
	Button mSingleDateButton;
	ToggleButton mFrontSingleToggleButton;
	ToggleButton mBackSingleToggleButton;
	
	// Recurring 
	ToggleButton mRecurringToggleButton;
	LinearLayout mRecurringLinearLayout;
	Button mRecurringTimeButton;
	
	// Recurring Day Toggle Buttons
	ToggleButton mMondayToggleButton;
	ToggleButton mTuesdayToggleButton;
	ToggleButton mWednesdayToggleButton;
	ToggleButton mThursdayToggleButton;
	ToggleButton mFridayToggleButton;
	ToggleButton mSaturdayToggleButton;
	ToggleButton mSundayToggleButton;
	
	// Recurring Front/Back toggle buttons
	ToggleButton mFrontRecurringToggleButton;
	ToggleButton mBackRecurringToggleButton;
	

	public QueuingView(Activity activity, Task task) {
		super(activity);
		
		mTask = task;
		mActivity = activity;
		
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.task_settings_queuing, this);
		
		// Single Queuing
		mSingleToggleButton = (ToggleButton) findViewById(R.id.singleToggleButton);
		mSingleToggleButton.setOnCheckedChangeListener(this);
		mSingleToggleButton.setOnClickListener(this);
		mSingleLinearLayout = (LinearLayout) findViewById(R.id.singleLinearLayout);
		if (mTask.getBool(TaskBool.KEY_SINGLE_QUEUING)) {
			mSingleToggleButton.setChecked(true);
			mSingleLinearLayout.setVisibility(VISIBLE);
		}
		mSingleDateButton = (Button) findViewById(R.id.singleDateButton);
		mSingleDateButton.setOnClickListener(this);
		mSingleDateButton.setSelected(true);
		
		if (mTask.getLong(TaskLong.KEY_SINGLE_QUEUING_TIME) > 0){
			mSingleDate.setTimeInMillis(mTask.getLong(TaskLong.KEY_SINGLE_QUEUING_TIME));
		}
		mSingleDateButton.setText(getSingleText());
		
		mFrontSingleToggleButton = (ToggleButton) findViewById(R.id.frontSingleToggleButton);
		mFrontSingleToggleButton.setOnCheckedChangeListener(this);
		mFrontSingleToggleButton.setOnClickListener(this);
		mBackSingleToggleButton = (ToggleButton) findViewById(R.id.backSingleToggleButton);
		mBackSingleToggleButton.setOnCheckedChangeListener(this);
		mBackSingleToggleButton.setOnClickListener(this);
		if (mTask.getBool(TaskBool.KEY_SINGLE_QUEUEING_FRONT)){
			mFrontSingleToggleButton.setChecked(true);
		} else {
			mBackSingleToggleButton.setChecked(true);
		}
		
		// Recurring Queuing
		mRecurringToggleButton = (ToggleButton) findViewById(R.id.recurringToggleButton);
		mRecurringToggleButton.setOnCheckedChangeListener(this);
		mRecurringToggleButton.setOnClickListener(this);
		mRecurringLinearLayout = (LinearLayout) findViewById(R.id.recurringLinearLayout);
		if (mTask.getBool(TaskBool.KEY_RECURRING_QUEUING)){
			mRecurringToggleButton.setChecked(true);
			mRecurringLinearLayout.setVisibility(View.VISIBLE);
		}
		mRecurringTimeButton = (Button) findViewById(R.id.recurringTimeButton);
		mRecurringTimeButton.setSelected(true);
		mRecurringTimeButton.setOnClickListener(this);
		
		if (mTask.getLong(TaskLong.KEY_RECURRING_QUEUING_TIME) > 0){
			mRecurringDate.setTimeInMillis(mTask.getLong(TaskLong.KEY_RECURRING_QUEUING_TIME));
		}
		mRecurringTimeButton.setText(getRecurringText());
		
		// Recurring Queuing Day Toggle Buttons
		mMondayToggleButton = (ToggleButton) findViewById(R.id.mondayToggleButton);
		mMondayToggleButton.setOnCheckedChangeListener(this);
		mMondayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_QUEUING_MONDAY));
		
		mTuesdayToggleButton = (ToggleButton) findViewById(R.id.tuesdayToggleButton);
		mTuesdayToggleButton.setOnCheckedChangeListener(this);
		mTuesdayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_QUEUING_TUESDAY));
		
		mWednesdayToggleButton = (ToggleButton) findViewById(R.id.wednesdayToggleButton);
		mWednesdayToggleButton.setOnCheckedChangeListener(this);
		mWednesdayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_QUEUING_WEDNESDAY));
		
		mThursdayToggleButton = (ToggleButton) findViewById(R.id.thursdayToggleButton);
		mThursdayToggleButton.setOnCheckedChangeListener(this);
		mThursdayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_QUEUING_THURSDAY));
		
		mFridayToggleButton = (ToggleButton) findViewById(R.id.fridayToggleButton);
		mFridayToggleButton.setOnCheckedChangeListener(this);
		mFridayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_QUEUING_FRIDAY));
		
		mSaturdayToggleButton = (ToggleButton) findViewById(R.id.saturdayToggleButton);
		mSaturdayToggleButton.setOnCheckedChangeListener(this);
		mSaturdayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_QUEUING_SATURDAY));
		
		mSundayToggleButton = (ToggleButton) findViewById(R.id.sundayToggleButton);
		mSundayToggleButton.setOnCheckedChangeListener(this);
		mSundayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_QUEUING_SUNDAY));
		
		// Recurring Front/Back toggle buttons
		mFrontRecurringToggleButton = (ToggleButton) findViewById(R.id.frontRecurringToggleButton);
		mFrontRecurringToggleButton.setOnCheckedChangeListener(this);
		mFrontRecurringToggleButton.setOnClickListener(this);
		mBackRecurringToggleButton = (ToggleButton) findViewById(R.id.backRecurringToggleButton);
		mBackRecurringToggleButton.setOnCheckedChangeListener(this);
		mBackRecurringToggleButton.setOnClickListener(this);
		if (mTask.getBool(TaskBool.KEY_RECURRING_QUEUING_FRONT)){
			mFrontRecurringToggleButton.setChecked(true);
		} else {
			mBackRecurringToggleButton.setChecked(true);
		}
	}
	
	private String getSingleText(){
		long millis = mSingleDate.getTimeInMillis();
		String singleText = DateUtils.formatDateTime(mActivity, millis,
				DateUtils.FORMAT_SHOW_TIME
                | DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_SHOW_YEAR);
		return singleText;
	}
	
	private String getRecurringText(){
		long millis = mRecurringDate.getTimeInMillis();
		String recurringText = DateUtils.formatDateTime(mActivity, millis,
				DateUtils.FORMAT_SHOW_TIME);
		return recurringText;
	}
	
	// Single Alert Time Picker
    private TimePickerDialog.OnTimeSetListener mSingleTimeSetListener =
    	new TimePickerDialog.OnTimeSetListener() {	
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				mSingleDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mSingleDate.set(Calendar.MINUTE, minute);
				mSingleDateButton.setText(getSingleText());
				
			}
		};
	
	// Single Alert Date Picker
	private DatePickerDialog.OnDateSetListener mSingleDateSetListener = 
		new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            	mSingleDate.set(year, monthOfYear, dayOfMonth);
            	mSingleDateButton.setText(getSingleText());
            	mSingleDateHasChanged = true;
            	
            	new TimePickerDialog(mActivity, 
            			mSingleTimeSetListener, 
            			mSingleDate.get(Calendar.HOUR_OF_DAY), 
            			mSingleDate.get(Calendar.MINUTE), false).show();
            }
        };
    
    // Recurring Alert Time Picker
	private TimePickerDialog.OnTimeSetListener mRecurringTimeSetListener =
    	new TimePickerDialog.OnTimeSetListener() {	
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				mRecurringDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mRecurringDate.set(Calendar.MINUTE, minute);
				mRecurringTimeButton.setText(getRecurringText());
				mmRecurringDateHasChanged = true;
			}
		};


	public void onClick(View v) {
		switch (v.getId()){
		
		case R.id.singleToggleButton:
			mRecurringToggleButton.setChecked(false);
			mRecurringLinearLayout.setVisibility(View.GONE);
			if (mSingleToggleButton.isChecked()){
				mSingleLinearLayout.setVisibility(View.VISIBLE);
			} else {
				mSingleLinearLayout.setVisibility(View.GONE);
			}
			break;
			
		case R.id.singleDateButton:
			new DatePickerDialog(mActivity, 
					mSingleDateSetListener, 
					mSingleDate.get(Calendar.YEAR), 
					mSingleDate.get(Calendar.MONTH), 
					mSingleDate.get(Calendar.DAY_OF_MONTH)
			).show();
			break;
		
		case R.id.frontSingleToggleButton:
			mFrontSingleToggleButton.setChecked(true);
			mBackSingleToggleButton.setChecked(false);
			break;
		case R.id.backSingleToggleButton:
			mFrontSingleToggleButton.setChecked(false);
			mBackSingleToggleButton.setChecked(true);
			break;
			
		case R.id.recurringToggleButton:
			mSingleToggleButton.setChecked(false);
			mSingleLinearLayout.setVisibility(View.GONE);
			if (mRecurringToggleButton.isChecked()){
				mRecurringLinearLayout.setVisibility(View.VISIBLE);
			} else {
				mRecurringLinearLayout.setVisibility(View.GONE);
			}
			break;
			
		case R.id.recurringTimeButton:
        	new TimePickerDialog(mActivity, 
        			mRecurringTimeSetListener, 
        			mRecurringDate.get(Calendar.HOUR_OF_DAY), 
        			mRecurringDate.get(Calendar.MINUTE), false).show();
			break;
			
		case R.id.frontRecurringToggleButton:
			mFrontRecurringToggleButton.setChecked(true);
			mBackRecurringToggleButton.setChecked(false);
			break;
		case R.id.backRecurringToggleButton:
			mFrontRecurringToggleButton.setChecked(false);
			mBackRecurringToggleButton.setChecked(true);
			break;
			
		}
		
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		buttonView.setSelected(isChecked);
	}

	public void onPause() {
		if (mSingleDateHasChanged){
			mTask.put(TaskLong.KEY_SINGLE_QUEUING_TIME, mSingleDate.getTimeInMillis());
			if (mSingleToggleButton.isChecked()){
				setAlarm(mSingleDate.getTimeInMillis(), 0);
			}
		}
		mTask.put(TaskBool.KEY_SINGLE_QUEUING, mSingleToggleButton.isChecked());
		mTask.put(TaskBool.KEY_SINGLE_QUEUEING_FRONT, mFrontSingleToggleButton.isChecked());
		

		
		mTask.put(TaskBool.KEY_RECURRING_QUEUING, mRecurringToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_QUEUING_MONDAY, mMondayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_QUEUING_TUESDAY, mTuesdayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_QUEUING_WEDNESDAY, mWednesdayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_QUEUING_THURSDAY, mThursdayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_QUEUING_FRIDAY, mFridayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_QUEUING_SATURDAY, mSaturdayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_QUEUING_SUNDAY, mSundayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_QUEUING_FRONT, mFrontRecurringToggleButton.isChecked());
		
		if (mmRecurringDateHasChanged){
			mTask.put(TaskLong.KEY_RECURRING_QUEUING_TIME, mRecurringDate.getTimeInMillis());
			if (mRecurringToggleButton.isChecked()){
				setRecurringAlarms();
			}
		}
		
	}
	
	private void setAlarm(long when, long recurring) {
		Log.d(TAG, "setAlarm: "+new Date(when).toString()+" recurring: "+recurring);
		when = when - when % DateUtils.MINUTE_IN_MILLIS;
		
		if (when < System.currentTimeMillis()){
			when = when + recurring;
		}
		
		if (when > System.currentTimeMillis()){
			int taskId = mTask.getId();
			Alarm alarm = new Alarm(taskId, when, recurring);
			alarm.put(AlarmBool.KEY_QUEUING, true);
			alarm.set();
		}
		
	}
	
	private void setRecurringAlarms(){
		Calendar c;
		
		if (mMondayToggleButton.isChecked()){
			c = (Calendar) mRecurringDate.clone();
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			setAlarm(c.getTimeInMillis(), DateUtils.WEEK_IN_MILLIS);
		}
		if (mTuesdayToggleButton.isChecked()){
			c = (Calendar) mRecurringDate.clone();
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			setAlarm(c.getTimeInMillis(), DateUtils.WEEK_IN_MILLIS);
		}
		if (mWednesdayToggleButton.isChecked()){
			c = (Calendar) mRecurringDate.clone();
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			setAlarm(c.getTimeInMillis(), DateUtils.WEEK_IN_MILLIS);
		}
		if (mThursdayToggleButton.isChecked()){
			c = (Calendar) mRecurringDate.clone();
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			setAlarm(c.getTimeInMillis(), DateUtils.WEEK_IN_MILLIS);
		}
		if (mFridayToggleButton.isChecked()){
			c = (Calendar) mRecurringDate.clone();
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			setAlarm(c.getTimeInMillis(), DateUtils.WEEK_IN_MILLIS);
		}
		if (mSaturdayToggleButton.isChecked()){
			c = (Calendar) mRecurringDate.clone();
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			setAlarm(c.getTimeInMillis(), DateUtils.WEEK_IN_MILLIS);
		}
		if (mSundayToggleButton.isChecked()){
			c = (Calendar) mRecurringDate.clone();
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			setAlarm(c.getTimeInMillis(), DateUtils.WEEK_IN_MILLIS);
		}
	}
}

