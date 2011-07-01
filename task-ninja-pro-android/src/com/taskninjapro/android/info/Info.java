package com.taskninjapro.android.info;

import java.util.Stack;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.taskninjapro.android.R;
import com.taskninjapro.android.app.BaseActivity;

public class Info extends BaseActivity {
	
	LinearLayout mLinearLayout;
	Stack<PanelInfo> mInfoStack = new Stack<PanelInfo>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.app_settings);
		
		mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		
		mInfoStack.push(new PanelInfo("hello panel","some text"));
		
		for (PanelInfo p: mInfoStack){
			new AsyncInfoPanelView().execute(this);
		}
	}
	
	private class PanelInfo {
		String mTitle;
		String mInfo;
		
		public PanelInfo(String title, String info){
			mTitle = title;
			mInfo = info;
		}
		
		public String getTitle(){
			return mTitle;
		}
		
		public String getInfo(){
			return mInfo;
		}
	}
	
	private class AsyncInfoPanelView extends AsyncTask<Activity, Void, InfoPanelView> {
		@Override
		protected InfoPanelView doInBackground(Activity... activity) {
			return new InfoPanelView(activity[0]);
		}

		@Override
		protected void onPostExecute(InfoPanelView view) {
			if (!mInfoStack.empty()){
				PanelInfo panelInfo = mInfoStack.pop();
				view.setInfo(panelInfo.getTitle(), panelInfo.getInfo());
				view.setSelected(true);
				mLinearLayout.addView(view);
			}
			
		}
	}

}
