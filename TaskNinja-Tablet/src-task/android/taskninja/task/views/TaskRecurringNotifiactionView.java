package android.taskninja.task.views;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.TimePickerDialog;
import com.rocksolidmobility.taskninja.android.R;
import android.taskninja.alarm.TaskAlarm;
import android.taskninja.task.Task;
import android.taskninja.task.TaskBool;
import android.taskninja.task.TaskLong;
import android.taskninja.tools.MSG;
import android.taskninja.tools.OnActionListener;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class TaskRecurringNotifiactionView extends LinearLayout implements OnClickListener, OnActionListener<MSG> {
	
	private static final String TAG = "TaskRecurringNotifiactionView";
	
	private Task mTask;
	private Activity mActivity;
	private TextView mSecondaryText;
	private Calendar mCal = Calendar.getInstance();
	private Long mTime = null;
	private LinearLayout mHiddenView;
	private Button mTimeButton;
	private ToggleButton mNotificationToggleButton;
	
	// Recurring Alert Day Toggle Buttons
	ToggleButton mMondayToggleButton;
	ToggleButton mTuesdayToggleButton;
	ToggleButton mWednesdayToggleButton;
	ToggleButton mThursdayToggleButton;
	ToggleButton mFridayToggleButton;
	ToggleButton mSaturdayToggleButton;
	ToggleButton mSundayToggleButton;
	
	public static TaskRecurringNotifiactionView getInstance(Activity activity, Task task) {
		Log.d(TAG, "getInstance");
		return new TaskRecurringNotifiactionView(activity, task);
	}
	
	public TaskRecurringNotifiactionView(Activity activity, Task task) {
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
		addTimeChooser();
		addDayButtons();
		updateCal();
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
		primaryText.setText("Recurring Notification");
		
		mSecondaryText = (TextView) findViewById(R.id.secondaryText);
		
		mNotificationToggleButton = (ToggleButton) findViewById(R.id.headerToggleButton);
		mNotificationToggleButton.setVisibility(VISIBLE);
		mNotificationToggleButton.setChecked(mTask.getBool(TaskBool.RecurringNotification));
		mNotificationToggleButton.setOnClickListener(this);
	}
	
	private void setupHiddenView() {
		Log.d(TAG, "setupHiddenView");
		
		mActivity.getLayoutInflater().inflate(R.layout.hidden_view, this);
		mHiddenView = (LinearLayout) findViewById(R.id.hiddenLinearLayout);
	}
	
	private void addTimeChooser() {
		Log.d(TAG, "addTimeChooser");
		
		mTimeButton = new Button(mActivity);
		mTimeButton.setOnClickListener(this);
		mHiddenView.addView(mTimeButton);
	}
	
	@Override
	public void onClick(View view) {
		Log.d(TAG, "onClick");
		
		if (view.equals(mTimeButton)){
			new TimePickerDialog(mActivity, 
        			mTimeSetListener, 
        			mCal.get(Calendar.HOUR_OF_DAY), 
        			mCal.get(Calendar.MINUTE), false).show();
		}
		
	}
	
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
    	new TimePickerDialog.OnTimeSetListener() {	
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Log.d(TAG, "onTimeSet");
				
				mTime = TimeUnit.HOURS.toMillis(hourOfDay) + TimeUnit.MINUTES.toMillis(minute);
				mCal.setTimeInMillis(TaskAlarm.getZeroCal().getTimeInMillis() + mTime);
				mTimeButton.setText(getTimeText());
				
			}

		};
       
    private String getTimeText(){
		long millis = mCal.getTimeInMillis();
		String time = DateUtils.formatDateTime(mActivity, millis,
				DateUtils.FORMAT_SHOW_TIME);
		return time;
	}
	
	private void addDayButtons() {
		mActivity.getLayoutInflater().inflate(R.layout.day_toggle_buttons, mHiddenView);
		
		mMondayToggleButton = (ToggleButton) findViewById(R.id.mondayToggleButton);
		mMondayToggleButton.setChecked(mTask.getBool(TaskBool.MondayNotification));
		
		mTuesdayToggleButton = (ToggleButton) findViewById(R.id.tuesdayToggleButton);
		mTuesdayToggleButton.setChecked(mTask.getBool(TaskBool.TuesdayNotification));
		
		mWednesdayToggleButton = (ToggleButton) findViewById(R.id.wednesdayToggleButton);
		mWednesdayToggleButton.setChecked(mTask.getBool(TaskBool.WednesdayNotification));
		
		mThursdayToggleButton = (ToggleButton) findViewById(R.id.thursdayToggleButton);
		mThursdayToggleButton.setChecked(mTask.getBool(TaskBool.ThursdayNotification));
		
		mFridayToggleButton = (ToggleButton) findViewById(R.id.fridayToggleButton);
		mFridayToggleButton.setChecked(mTask.getBool(TaskBool.FridayNotification));
		
		mSaturdayToggleButton = (ToggleButton) findViewById(R.id.saturdayToggleButton);
		mSaturdayToggleButton.setChecked(mTask.getBool(TaskBool.SaturdayNotification));
		
		mSundayToggleButton = (ToggleButton) findViewById(R.id.sundayToggleButton);
		mSundayToggleButton.setChecked(mTask.getBool(TaskBool.SundayNotification));
		
	}
	
	private void updateCal() {
		Long time = mTask.getLong(TaskLong.RecurringNotificationTime);
		
		if (time != null){
			mCal.setTimeInMillis(TaskAlarm.getZeroCal().getTimeInMillis() + time);
			String text = getTimeText();
			mTimeButton.setText(text);
			
			if (mTask.getBool(TaskBool.MondayNotification)){
				text = text +"  M";
			}
			
			if (mTask.getBool(TaskBool.TuesdayNotification)){
				text = text +"  Tu";
			}
			
			if (mTask.getBool(TaskBool.WednesdayNotification)){
				text = text +"  W";
			}
			
			if (mTask.getBool(TaskBool.ThursdayNotification)){
				text = text +"  Th";
			}
			
			if (mTask.getBool(TaskBool.FridayNotification)){
				text = text +"  F";
			}
			
			if (mTask.getBool(TaskBool.SaturdayNotification)){
				text = text +"  Sa";
			}
			
			if (mTask.getBool(TaskBool.SundayNotification)){
				text = text +"  Su";
			}
			
			mSecondaryText.setText(text);
		} else {
			mTimeButton.setText("Set The Notification Time");
		}
	}


	@Override
	public void onAction(MSG action) {
		switch (action){
		case SAVE:
			mTask.put(TaskLong.RecurringNotificationTime, mTime);
			mTask.put(TaskBool.RecurringNotification, mNotificationToggleButton.isChecked());
			mTask.put(TaskBool.MondayNotification, mMondayToggleButton.isChecked());
			mTask.put(TaskBool.TuesdayNotification, mTuesdayToggleButton.isChecked());
			mTask.put(TaskBool.WednesdayNotification, mWednesdayToggleButton.isChecked());
			mTask.put(TaskBool.ThursdayNotification, mThursdayToggleButton.isChecked());
			mTask.put(TaskBool.FridayNotification, mFridayToggleButton.isChecked());
			mTask.put(TaskBool.SaturdayNotification, mSaturdayToggleButton.isChecked());
			mTask.put(TaskBool.SundayNotification, mSundayToggleButton.isChecked());
			
			if (mTask.getBool(TaskBool.RecurringNotification) && mTask.getLong(TaskLong.RecurringNotificationTime) != null){
				TaskAlarm.setRecurringNotification(mTask);
			}
			
			updateCal();
			
		case CANCEL:
			updateCal();
			break;
		}
		
	}



	





	



}














