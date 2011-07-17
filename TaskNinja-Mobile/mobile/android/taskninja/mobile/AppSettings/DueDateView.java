package android.taskninja.mobile.AppSettings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.taskninja.mobile.app.Constants;
import android.taskninja.mobile.app.LifeCycleListener;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.taskninjapro.android.R;

public class DueDateView extends LinearLayout implements LifeCycleListener, Constants, OnCheckedChangeListener {

	Activity mActivity;
	SharedPreferences mSettings;
	
	TextView mTextView;
	ToggleButton mToggleButton;

	public DueDateView(Activity activity) {
		super(activity);
		mActivity = activity;
		mSettings = mActivity.getSharedPreferences(PREFS, PREFS_MODE);
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.toggle_view, this);
		
		mTextView = (TextView) findViewById(R.id.textView);
		mTextView.setText("Due Date");
		
		mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		mToggleButton.setOnCheckedChangeListener(this);
		mToggleButton.setTextOff("Due Date Off");
		mToggleButton.setTextOn("Due Date On");
		mToggleButton.setChecked(mSettings.getBoolean(DUE_DATE, DUE_DATE_DEFAULT));

		
	}

	public void onPause() {
		mSettings.edit().putBoolean(DUE_DATE, mToggleButton.isChecked()).commit();
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		this.setSelected(isChecked);
	}
	
}
