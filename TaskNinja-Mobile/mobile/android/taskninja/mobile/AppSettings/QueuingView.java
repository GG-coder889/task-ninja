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

public class QueuingView extends LinearLayout implements LifeCycleListener, Constants, OnCheckedChangeListener {

	Activity mActivity;
	SharedPreferences mSettings;
	
	TextView mTextView;
	ToggleButton mToggleButton;

	public QueuingView(Activity activity) {
		super(activity);
		mActivity = activity;
		mSettings = mActivity.getSharedPreferences(PREFS, PREFS_MODE);
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.toggle_view, this);
		
		mTextView = (TextView) findViewById(R.id.textView);
		mTextView.setText("Queuing");
		
		mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		mToggleButton.setOnCheckedChangeListener(this);
		mToggleButton.setTextOff("Queuing Off");
		mToggleButton.setTextOn("Queuing On");
		mToggleButton.setChecked(mSettings.getBoolean(QUEUING, QUEUING_DEFAULT));
		
	}

	public void onPause() {
		mSettings.edit().putBoolean(QUEUING, mToggleButton.isChecked()).commit();
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		this.setSelected(isChecked);
	}
}
