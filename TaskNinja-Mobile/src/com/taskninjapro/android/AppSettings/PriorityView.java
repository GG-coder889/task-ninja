package com.taskninjapro.android.AppSettings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.taskninjapro.android.R;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.app.LifeCycleListener;

public class PriorityView extends LinearLayout implements LifeCycleListener, Constants, OnCheckedChangeListener {
	
	Activity mActivity;
	SharedPreferences mSettings;
	
	TextView mTextView;
	ToggleButton mToggleButton;

	public PriorityView(Activity activity) {
		super(activity);
		mActivity = activity;
		mSettings = mActivity.getSharedPreferences(PREFS, PREFS_MODE);
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.toggle_view, this);
		
		mTextView = (TextView) findViewById(R.id.textView);
		mTextView.setText("Priority");
		
		mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		mToggleButton.setOnCheckedChangeListener(this);
		mToggleButton.setTextOff("Priority Off");
		mToggleButton.setTextOn("Priority On");
		mToggleButton.setChecked(mSettings.getBoolean(PRIORITY, PRIORITY_DEFAULT));

		
	}

	public void onPause() {
		mSettings.edit().putBoolean(PRIORITY, mToggleButton.isChecked()).commit();
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		this.setSelected(isChecked);
	}
	
}
