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

public class SubtaskingView extends LinearLayout implements LifeCycleListener, Constants, OnCheckedChangeListener {

	Activity mActivity;
	SharedPreferences mSettings;
	
	TextView mTextView;
	ToggleButton mToggleButton;

	public SubtaskingView(Activity activity) {
		super(activity);
		mActivity = activity;
		mSettings = mActivity.getSharedPreferences(PREFS, PREFS_MODE);
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.toggle_view, this);
		
		mTextView = (TextView) findViewById(R.id.textView);
		mTextView.setText("Subtasking");
		
		mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		mToggleButton.setOnCheckedChangeListener(this);
		mToggleButton.setTextOff("Subtasking Off");
		mToggleButton.setTextOn("Subtasking On");
		mToggleButton.setChecked(mSettings.getBoolean(SUBTASKING, SUBTASKING_DEFAULT));
		
	}

	public void onPause() {
		mSettings.edit().putBoolean(SUBTASKING, mToggleButton.isChecked()).commit();
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		this.setSelected(isChecked);
	}
	
}
