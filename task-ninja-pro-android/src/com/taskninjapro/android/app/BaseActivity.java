package com.taskninjapro.android.app;

import com.taskninjapro.android.R;
import com.taskninjapro.android.AppSettings.AppSettings;
import com.taskninjapro.android.MasterList.MasterList;
import com.taskninjapro.android.Queue.Queue;
import com.taskninjapro.android.QueueSelector.QueueSelector;
import com.taskninjapro.android.info.Info;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public abstract class BaseActivity extends Activity {
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			startActivity(new Intent(this, AppSettings.class));
			return true;
		case R.id.selector:
			startActivity(new Intent(this, QueueSelector.class));
			return true;
		case R.id.master:
			startActivity(new Intent(this, MasterList.class));
			return true;
		case R.id.queue:
			startActivity(new Intent(this, Queue.class));
			return true;
		case R.id.info:
			startActivity(new Intent(this, Info.class));
			return true;
		default:
			return true;
		}
	}

}
