package android.taskninja.task.views;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import com.rocksolidmobility.taskninja.android.R;
import android.taskninja.alarm.TaskAlarm;
import android.taskninja.dbmodel.Db_Listener;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class TaskSingleNotifiactionView extends LinearLayout implements OnClickListener, OnActionListener<MSG> {
	
	private static final String TAG = "TaskSingleNotifiactionView";
	
	private Task mTask;
	private Activity mActivity;
	private TextView mSecondaryText;
	private Calendar mDate = Calendar.getInstance();
	private LinearLayout mHiddenView;
	private Button mDateButton;
	private ToggleButton mNotificationButton;
	
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
		updateCal();
	}
	
	private void setDbListener() {
		Log.d(TAG, "setDbListener");
		
		mTask.addListener(new Db_Listener() {
			@Override
			public void onChange(Enum key) {
				if (key.equals(TaskLong.SingleNotificationTime)){
					updateCal();
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
		
		mNotificationButton = (ToggleButton) findViewById(R.id.headerToggleButton);
		mNotificationButton.setVisibility(VISIBLE);
		mNotificationButton.setChecked(mTask.getBool(TaskBool.SingleNotification));
		mNotificationButton.setOnClickListener(this);
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
	}
	
	// Single Alert Time Picker
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
    	new TimePickerDialog.OnTimeSetListener() {	
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Log.d(TAG, "onTimeSet");
				
				mDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mDate.set(Calendar.MINUTE, minute);
				mDateButton.setText(getSingleText());
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
    
    private void updateCal(){
		Long time = mTask.getLong(TaskLong.SingleNotificationTime);
		
		if (time != null){
			mDate.setTimeInMillis(time);
			mSecondaryText.setText(getSingleText());
			mSecondaryText.setVisibility(VISIBLE);
			mDateButton.setText(getSingleText());
		} else {
			mDateButton.setText("Set The Notification Date");
			mSecondaryText.setText(null);
			mSecondaryText.setVisibility(GONE);
		}
    }

	@Override
	public void onAction(MSG action) {
		switch (action) {
		case SAVE:
			
			if (mDate.getTimeInMillis() > System.currentTimeMillis()){
				mTask.put(TaskLong.SingleNotificationTime, mDate.getTimeInMillis());
			}
			
			mTask.put(TaskBool.SingleNotification, mNotificationButton.isChecked());
			
			Long time = mTask.getLong(TaskLong.SingleNotificationTime);
			
			if ( mTask.getBool(TaskBool.SingleNotification)  
					&& time != null
					&& time > System.currentTimeMillis()){
				TaskAlarm.setSingleNotification(mTask);
			}
			
			updateCal();
			break;
			
		case CANCEL:
			updateCal();
			break;

		default:
			break;
		}
		
	}
	

	





	



}














