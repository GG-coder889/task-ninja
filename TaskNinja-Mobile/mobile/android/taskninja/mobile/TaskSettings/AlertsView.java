package android.taskninja.mobile.TaskSettings;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.taskninja.mobile.alarm.Alarm;
import android.taskninja.mobile.alarm.AlarmBool;
import android.taskninja.mobile.app.Constants;
import android.taskninja.mobile.app.LifeCycleListener;
import android.taskninja.mobile.task.Task;
import android.text.format.DateUtils;
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
import com.taskninjapro.android.task.TaskBool;
import com.taskninjapro.android.task.TaskInteger;
import com.taskninjapro.android.task.TaskLong;

public class AlertsView extends LinearLayout implements OnClickListener, LifeCycleListener, Constants, OnCheckedChangeListener {
	
	Task mTask;
	Activity mActivity;
	private boolean mSingleDateHasChanged = false;
	Calendar mSingleDate = Calendar.getInstance();
	private boolean mmRecurringDateHasChanged = false;
	Calendar mRecurringDate = Calendar.getInstance();
	
	// Single Alert
	ToggleButton mSingleToggleButton;
	LinearLayout mSingleLinearLayout;
	Button mSingleAlertButton;
	
	// Recurring Alert
	ToggleButton mRecurringToggleButton;
	LinearLayout mRecurringLinearLayout;
	Button mRecurringAlertButton;
	
	// Recurring Alert Day Toggle Buttons
	ToggleButton mMondayToggleButton;
	ToggleButton mTuesdayToggleButton;
	ToggleButton mWednesdayToggleButton;
	ToggleButton mThursdayToggleButton;
	ToggleButton mFridayToggleButton;
	ToggleButton mSaturdayToggleButton;
	ToggleButton mSundayToggleButton;

	public AlertsView(Activity activity, Task task) {
		super(activity);
		
		mTask = task;
		mActivity = activity;
		
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.task_settings_alert, this);
		
		// Single Alert
		mSingleToggleButton = (ToggleButton) findViewById(R.id.singleToggleButton);
		mSingleToggleButton.setOnCheckedChangeListener(this);
		mSingleToggleButton.setOnClickListener(this);
		mSingleLinearLayout = (LinearLayout) findViewById(R.id.singleLinearLayout);
		if (mTask.getBool(TaskBool.KEY_SINGLE_ALERT)) {
			mSingleToggleButton.setChecked(true);
			mSingleLinearLayout.setVisibility(VISIBLE);
		} else {
			mSingleToggleButton.setChecked(false);
			mSingleLinearLayout.setVisibility(GONE);
		}
		mSingleAlertButton = (Button) findViewById(R.id.singleAlertButton);
		mSingleAlertButton.setOnClickListener(this);
		mSingleAlertButton.setSelected(true);
		
		if (mTask.getLong(TaskLong.KEY_SINGLE_ALERT_TIME) > 0){
			mSingleDate.setTimeInMillis(mTask.getLong(TaskLong.KEY_SINGLE_ALERT_TIME));
		}
		
		mSingleAlertButton.setText(getSingleText());
		
		
		
		// Recurring Alert
		mRecurringToggleButton = (ToggleButton) findViewById(R.id.recurringToggleButton);
		mRecurringToggleButton.setOnCheckedChangeListener(this);
		mRecurringToggleButton.setOnClickListener(this);
		mRecurringLinearLayout = (LinearLayout) findViewById(R.id.recurringLinearLayout);
		if (mTask.getBool(TaskBool.KEY_RECURRING_ALERT)){
			mRecurringToggleButton.setChecked(true);
			mRecurringLinearLayout.setVisibility(VISIBLE);
		} else {
			mRecurringToggleButton.setChecked(false);
			mRecurringLinearLayout.setVisibility(GONE);
		}
		mRecurringAlertButton = (Button) findViewById(R.id.recurringAlertButton);
		mRecurringAlertButton.setSelected(true);
		mRecurringAlertButton.setOnClickListener(this);
		
		if (mTask.getLong(TaskLong.KEY_RECURRING_ALERT_TIME) > 0){
			mRecurringDate.setTimeInMillis(mTask.getLong(TaskLong.KEY_RECURRING_ALERT_TIME));
		}
		mRecurringAlertButton.setText(getRecurringText());
		
		// Recurring Alert Day Toggle Buttons
		mMondayToggleButton = (ToggleButton) findViewById(R.id.mondayToggleButton);
		mMondayToggleButton.setOnCheckedChangeListener(this);
		mMondayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_ALERT_MONDAY));
		
		mTuesdayToggleButton = (ToggleButton) findViewById(R.id.tuesdayToggleButton);
		mTuesdayToggleButton.setOnCheckedChangeListener(this);
		mTuesdayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_ALERT_TUESDAY));
		
		mWednesdayToggleButton = (ToggleButton) findViewById(R.id.wednesdayToggleButton);
		mWednesdayToggleButton.setOnCheckedChangeListener(this);
		mWednesdayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_ALERT_WEDNESDAY));
		
		mThursdayToggleButton = (ToggleButton) findViewById(R.id.thursdayToggleButton);
		mThursdayToggleButton.setOnCheckedChangeListener(this);
		mThursdayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_ALERT_THURSDAY));
		
		mFridayToggleButton = (ToggleButton) findViewById(R.id.fridayToggleButton);
		mFridayToggleButton.setOnCheckedChangeListener(this);
		mFridayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_ALERT_FRIDAY));
		
		mSaturdayToggleButton = (ToggleButton) findViewById(R.id.saturdayToggleButton);
		mSaturdayToggleButton.setOnCheckedChangeListener(this);
		mSaturdayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_ALERT_SATURDAY));
		
		mSundayToggleButton = (ToggleButton) findViewById(R.id.sundayToggleButton);
		mSundayToggleButton.setOnCheckedChangeListener(this);
		mSundayToggleButton.setChecked(mTask.getBool(TaskBool.KEY_RECURRING_ALERT_SUNDAY));
		
		
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
				mSingleAlertButton.setText(getSingleText());
			}
		};
	
	// Single Alert Date Picker
	private DatePickerDialog.OnDateSetListener mSingleDateSetListener = 
		new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            	mSingleDate.set(year, monthOfYear, dayOfMonth);
            	mSingleAlertButton.setText(getSingleText());
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
				mRecurringAlertButton.setText(getRecurringText());
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
			
		case R.id.singleAlertButton:
			new DatePickerDialog(mActivity, 
					mSingleDateSetListener, 
					mSingleDate.get(Calendar.YEAR), 
					mSingleDate.get(Calendar.MONTH), 
					mSingleDate.get(Calendar.DAY_OF_MONTH)
			).show();
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
			
		case R.id.recurringAlertButton:
        	new TimePickerDialog(mActivity, 
        			mRecurringTimeSetListener, 
        			mRecurringDate.get(Calendar.HOUR_OF_DAY), 
        			mRecurringDate.get(Calendar.MINUTE), false).show();
			break;
		}
		
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		buttonView.setSelected(isChecked);
	}

	public void onPause() {
		if (mSingleDateHasChanged){
			mTask.put(TaskLong.KEY_SINGLE_ALERT_TIME, mSingleDate.getTimeInMillis());
			if (mSingleToggleButton.isChecked()){
				setAlarm(mSingleDate.getTimeInMillis(), 0);
			}
		}
		
		mTask.put(TaskBool.KEY_SINGLE_ALERT, mSingleToggleButton.isChecked());

		mTask.put(TaskBool.KEY_RECURRING_ALERT, mRecurringToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_ALERT_MONDAY, mMondayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_ALERT_TUESDAY, mTuesdayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_ALERT_WEDNESDAY, mWednesdayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_ALERT_THURSDAY, mThursdayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_ALERT_FRIDAY, mFridayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_ALERT_SATURDAY, mSaturdayToggleButton.isChecked());
		mTask.put(TaskBool.KEY_RECURRING_ALERT_SUNDAY, mSundayToggleButton.isChecked());
		
		if (mmRecurringDateHasChanged){
			mTask.put(TaskLong.KEY_RECURRING_ALERT_TIME, mRecurringDate.getTimeInMillis());;
			if (mRecurringToggleButton.isChecked()){
				setRecurringAlarms();
			}
		}
		
	}
	
	private void setAlarm(long when, long recurring) {
		when = when - when % DateUtils.MINUTE_IN_MILLIS;
		
		if (when < System.currentTimeMillis()){
			when = when + recurring;
		}
		
		if (when > System.currentTimeMillis()){
			int taskId = mTask.getId();
			Alarm alarm = new Alarm(taskId, when, recurring);
			alarm.put(AlarmBool.KEY_NOTIFICATION, true);
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
