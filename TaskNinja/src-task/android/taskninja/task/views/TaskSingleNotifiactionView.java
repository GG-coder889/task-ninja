package android.taskninja.task.views;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.taskninja.R;
import android.taskninja.alarm.TaskAlarm;
import android.taskninja.dbmodel.Db_Listener;
import android.taskninja.task.Task;
import android.taskninja.task.TaskBool;
import android.taskninja.task.TaskLong;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class TaskSingleNotifiactionView extends LinearLayout implements OnClickListener {
	
	private static final String TAG = "TaskSingleNotifiactionView";
	
	private Task mTask;
	private Activity mActivity;
	private TextView mSecondaryText;
	private Calendar mDate = Calendar.getInstance();
	private LinearLayout mHiddenView;
	private Button mDateButton;
	
	public static TaskSingleNotifiactionView getInstance(Activity activity, Task task) {
		Log.d(TAG, "getInstance");
		return new TaskSingleNotifiactionView(activity, task);
	}
	
	public TaskSingleNotifiactionView(Activity activity, Task task) {
		super(activity);
		Log.d(TAG, "TaskSingleNotifiactionView");
		
		mActivity = activity;
		mTask = task;
		
		setOrientation(VERTICAL);
		
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 5, 0, 5);
		setLayoutParams(layoutParams);
		
		setBackgroundResource(android.R.drawable.screen_background_dark_transparent);
		
		setupHeader();
		setupHiddenView();
		addDateChooser();
		setDbListener();
	}
	
	private void setDbListener() {
		Log.d(TAG, "setDbListener");
		
		mTask.addListener(new Db_Listener() {
			@Override
			public void onChange(Enum key) {
				if (key.equals(TaskLong.SingleNotificationTime)){
					Long time = mTask.getLong(TaskLong.SingleNotificationTime);
					
					if (time != null){
						mDate.setTimeInMillis(time);
						mSecondaryText.setText(getSingleText());
						mDateButton.setText(getSingleText());
					}
				}
			}
		});
	}

	private void setupHeader() {
		Log.d(TAG, "setupHeader");
		
		mActivity.getLayoutInflater().inflate(R.layout.header, this);
		findViewById(R.id.header).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (mHiddenView.getVisibility()){
				case INVISIBLE:
				case VISIBLE:
					mHiddenView.setVisibility(GONE);
					break;
				case GONE:
					mHiddenView.setVisibility(VISIBLE);
					break;
				}
			}
		});
		
		TextView primaryText = (TextView) findViewById(R.id.primaryText);
		primaryText.setText("Single Notification");
		
		mSecondaryText = (TextView) findViewById(R.id.secondaryText);
		Long time = mTask.getLong(TaskLong.SingleNotificationTime);
		
		if (time != null){
			mDate.setTimeInMillis(time);
			mSecondaryText.setText(getSingleText());
		}
		
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.headerToggleButton);
		toggleButton.setVisibility(VISIBLE);
		toggleButton.setChecked(mTask.getBool(TaskBool.HasSingleNotification));
		toggleButton.setOnClickListener(this);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d(TAG, "onCheckedChanged");
				
				if (!(mTask.getBool(TaskBool.HasSingleNotification) == isChecked)){
					mTask.put(TaskBool.HasSingleNotification, isChecked);
					if (isChecked){
						TaskAlarm.setSingleNotification(mTask);
					}else {
						
					}
					
					
				}
			}
		});
	}
	
	@Override
	public void onClick(View view) {
		Log.d(TAG, "onClick");
		
		if (view.equals(mDateButton)){
			new DatePickerDialog(mActivity, 
					mDateSetListener, 
					mDate.get(Calendar.YEAR), 
					mDate.get(Calendar.MONTH), 
					mDate.get(Calendar.DAY_OF_MONTH)
			).show();
		}
		
	}
	
	private void setupHiddenView() {
		Log.d(TAG, "setupHiddenView");
		
		mActivity.getLayoutInflater().inflate(R.layout.hidden_view, this);
		mHiddenView = (LinearLayout) findViewById(R.id.hiddenLinearLayout);
	}
	
	private void addDateChooser() {
		Log.d(TAG, "addDateChooser");
		
		mDateButton = new Button(mActivity);
		mDateButton.setOnClickListener(this);
		mHiddenView.addView(mDateButton);
		
		Long time = mTask.getLong(TaskLong.SingleNotificationTime);
		
		if (time != null){
			mDate.setTimeInMillis(time);
			mSecondaryText.setText(getSingleText());
			mDateButton.setText(getSingleText());
		} else {
			mDateButton.setText("Set The Notification Date");
		}
	}
	
	// Single Alert Time Picker
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
    	new TimePickerDialog.OnTimeSetListener() {	
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Log.d(TAG, "onTimeSet");
				
				mDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mDate.set(Calendar.MINUTE, minute);
				
				if (mDate.getTimeInMillis() > System.currentTimeMillis()){
					mTask.put(TaskLong.SingleNotificationTime, mDate.getTimeInMillis());
					TaskAlarm.setSingleNotification(mTask);
					Log.d(TAG, "alarmTime set to "+getSingleText());
				} else {
					Log.d(TAG, "set alarm time denied because it was before the current time");
				}
				
			}
		};

	// Single Alert Date Picker
	private DatePickerDialog.OnDateSetListener mDateSetListener = 
		new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            	Log.d(TAG, "onDateSet");
            	
            	mDate.set(year, monthOfYear, dayOfMonth);
            	
            	new TimePickerDialog(mActivity, 
            			mTimeSetListener, 
            			mDate.get(Calendar.HOUR_OF_DAY), 
            			mDate.get(Calendar.MINUTE), false).show();
            }
        };
       
    private String getSingleText(){
		long millis = mDate.getTimeInMillis();
		String singleText = DateUtils.formatDateTime(mActivity, millis,
				DateUtils.FORMAT_SHOW_TIME
                | DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_SHOW_YEAR);
		return singleText;
	}
	
//	private void addDayButtons() {
//		// TODO Auto-generated method stub
//		
//	}
	

// What was I thinking; I don't this for single notifications
//	private class DayToggleButton extends ToggleButton implements OnClickListener {
//		
//		Task_Bool mKey;
//
//		public DayToggleButton(Context context, Task_Bool key) {
//			super(context);
//			
//			setOnClickListener(this);
//		}
//
//		@Override
//		public void onClick(View v) {
//			if (this.equals(v)){
//				mTask.put(mKey, this.isChecked());
//			}
//		}
//	}

	





	



}














