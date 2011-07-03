package com.taskninjapro.android.MainMenu;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.taskninjapro.android.R;
import com.taskninjapro.android.AppSettings.AppSettings;
import com.taskninjapro.android.MasterList.MasterList;
import com.taskninjapro.android.Queue.Queue;
import com.taskninjapro.android.QueueSelector.QueueSelector;
import com.taskninjapro.android.app.App;
import com.taskninjapro.android.app.BaseActivity;
import com.taskninjapro.android.app.Constants;

public class MainMenu extends BaseActivity implements Constants, OnClickListener {

	private static final String TAG = "Menu";
	private Button mQueueButton;
	private Button mMasterButton;
	private Button mSettingsButton;

	// ----------------------------------------------------------------------------------------------------
	// Life Cycle
	// ----------------------------------------------------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_menu);

		mQueueButton = (Button) findViewById(R.id.queueButton);
		mQueueButton.setSelected(true);
		mQueueButton.setOnClickListener(this);
		
		mMasterButton = (Button) findViewById(R.id.masterButton);
		mMasterButton.setSelected(true);
		mMasterButton.setOnClickListener(this);
		
		mSettingsButton = (Button) findViewById(R.id.settingsButton);
		mSettingsButton.setSelected(true);
		mSettingsButton.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();

	}

	// ----------------------------------------------------------------------------------------------------

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.queueButton:
			startActivity(new Intent(App.getContext(), Queue.class));
			break;
		case R.id.masterButton:
			startActivity(new Intent(App.getContext(), MasterList.class));
			break;
		case R.id.settingsButton:
			startActivity(new Intent(App.getContext(), AppSettings.class));
			break;
		}

	}
	
//	// ----------------------------------------------------------------------------------------------------
//	// Options Menu
//	// ----------------------------------------------------------------------------------------------------
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.options_menu, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.settings:
//			startActivity(new Intent(this, AppSettings.class));
//			return true;
//		case R.id.selector:
//			startActivity(new Intent(this, QueueSelector.class));
//			return true;
//		case R.id.master:
//			startActivity(new Intent(this, MasterList.class));
//			return true;
//		case R.id.queue:
//			startActivity(new Intent(this, Queue.class));
//			return true;
//		default:
//			return true;
//		}
//	}
	// ----------------------------------------------------------------------------------------------------


}
