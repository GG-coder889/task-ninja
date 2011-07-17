package android.taskninja.mobile.AppSettings;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.taskninja.mobile.app.BaseActivity;
import android.taskninja.mobile.app.Constants;
import android.taskninja.mobile.app.LifeCycleListener;
import android.widget.LinearLayout;

import com.taskninjapro.android.R;

public class AppSettings extends BaseActivity implements Constants {
	
	List<LifeCycleListener> mOnPauseListeners = new LinkedList<LifeCycleListener>();
	
	LinearLayout mLinearLayout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.app_settings);
		
		mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		
		new AsyncPriorityView().execute(this);
		new AsyncDueDateView().execute(this);
		new AsyncAlertsView().execute(this);
		new AsyncQueuingView().execute(this);
		new AsyncSubtaskingView().execute(this);
	}
	
	private class AsyncPriorityView extends AsyncTask<Activity, Void, PriorityView> {
		@Override
		protected PriorityView doInBackground(Activity... activity) {
			return new PriorityView(activity[0]);
		}

		@Override
		protected void onPostExecute(PriorityView view) {
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}
	
	private class AsyncDueDateView extends AsyncTask<Activity, Void, DueDateView> {
		@Override
		protected DueDateView doInBackground(Activity... activity) {
			return new DueDateView(activity[0]);
		}

		@Override
		protected void onPostExecute(DueDateView view) {
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}
	
	private class AsyncAlertsView extends AsyncTask<Activity, Void, AlertsView> {
		@Override
		protected AlertsView doInBackground(Activity... activity) {
			return new AlertsView(activity[0]);
		}

		@Override
		protected void onPostExecute(AlertsView view) {
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}
	
	private class AsyncQueuingView extends AsyncTask<Activity, Void, QueuingView> {
		@Override
		protected QueuingView doInBackground(Activity... activity) {
			return new QueuingView(activity[0]);
		}

		@Override
		protected void onPostExecute(QueuingView view) {
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}
	
	private class AsyncSubtaskingView extends AsyncTask<Activity, Void, SubtaskingView> {
		@Override
		protected SubtaskingView doInBackground(Activity... activity) {
			return new SubtaskingView(activity[0]);
		}

		@Override
		protected void onPostExecute(SubtaskingView view) {
			mOnPauseListeners.add(view);
			mLinearLayout.addView(view);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		for (LifeCycleListener opl : mOnPauseListeners){
			opl.onPause();
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.options_menu, menu);
//		menu.removeItem(R.id.settings);
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

}