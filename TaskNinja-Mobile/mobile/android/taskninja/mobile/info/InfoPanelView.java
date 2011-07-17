package android.taskninja.mobile.info;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.taskninjapro.android.R;

public class InfoPanelView extends LinearLayout implements OnCheckedChangeListener, OnClickListener {
	
	Activity mActivity;
	OnClickListener mListener;
	ToggleButton mToggleButton;
	TextView mTextView;

	public InfoPanelView(Activity activity, OnClickListener listener) {
		super(activity);
		mActivity = activity;
		mListener = listener;
		
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.info_panel, this);
		
		
		
		mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		mToggleButton.setOnCheckedChangeListener(this);
		mToggleButton.setChecked(false);
		
		mTextView = (TextView) findViewById(R.id.textView);
	}
	
	public void setInfo(String title, String info){
		mToggleButton.setTextOff(title);
		mToggleButton.setTextOn(title);
		mToggleButton.setText(title);
		
		mTextView.setText(info);
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked){
			mTextView.setVisibility(VISIBLE);
		} else {
			mTextView.setVisibility(GONE);
		}
//		mListener.onClick(this);
	}

	public void onClick(View v) {
		if (!v.equals(this)){
			mToggleButton.setChecked(false);
		} 
		
	}
	
	

}
