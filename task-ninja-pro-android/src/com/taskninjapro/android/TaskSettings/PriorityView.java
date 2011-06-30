package com.taskninjapro.android.TaskSettings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.taskninjapro.android.R;
import com.taskninjapro.android.Task.Task;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.app.LifeCycleListener;

public class PriorityView extends LinearLayout implements OnClickListener, Constants, LifeCycleListener, OnCheckedChangeListener  {
	
	Task mTask;
	Activity mActivity;
	
	ToggleButton mP1ToggleButton;
	ToggleButton mP2ToggleButton;
	ToggleButton mP3ToggleButton;

	public PriorityView(Activity activity, Task task) {
		super(activity);
		
		mTask = task;
		mActivity = activity;
		
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.task_settings_priority, this);
		
		// Priority
		mP1ToggleButton = (ToggleButton) findViewById(R.id.p1ToggleButton);
		mP1ToggleButton.setOnCheckedChangeListener(this);
		mP1ToggleButton.setOnClickListener(this);
		
		mP2ToggleButton = (ToggleButton) findViewById(R.id.p2ToggleButton);
		mP2ToggleButton.setOnCheckedChangeListener(this);
		mP2ToggleButton.setOnClickListener(this);
		
		mP3ToggleButton = (ToggleButton) findViewById(R.id.p3ToggleButton);
		mP3ToggleButton.setOnCheckedChangeListener(this);
		mP3ToggleButton.setOnClickListener(this);
		
		switch (mTask.getAsInteger(KEY_PRIORITY)){
		case 1:
			mP1ToggleButton.setChecked(true);
			break;
		case 2:
			mP2ToggleButton.setChecked(true);
			break;
		case 3:
			mP3ToggleButton.setChecked(true);
			break;
		}
		
	}

	public void onClick(View v) {
		
		switch (v.getId()){
		
		case R.id.p1ToggleButton:
			mP2ToggleButton.setChecked(false);
			mP3ToggleButton.setChecked(false);
			break;
		case R.id.p2ToggleButton:
			mP1ToggleButton.setChecked(false);
			mP3ToggleButton.setChecked(false);
			break;
		case R.id.p3ToggleButton:
			mP1ToggleButton.setChecked(false);
			mP2ToggleButton.setChecked(false);
			break;
	
		}
		
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		buttonView.setSelected(isChecked);
	}

	public void onPause() {
		if (mP1ToggleButton.isChecked())
			mTask.put(KEY_PRIORITY, 1);
		else if (mP2ToggleButton.isChecked())
			mTask.put(KEY_PRIORITY, 2);
		else if (mP3ToggleButton.isChecked())
			mTask.put(KEY_PRIORITY, 3);
		
	}

}
