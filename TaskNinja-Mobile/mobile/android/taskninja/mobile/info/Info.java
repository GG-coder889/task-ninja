package android.taskninja.mobile.info;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.taskninja.mobile.app.BaseActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.taskninjapro.android.R;

public class Info extends BaseActivity implements OnClickListener {
	ScrollView mScrollView;
	LinearLayout mLinearLayout;
	Stack<PanelInfo> mInfoStack = new Stack<PanelInfo>();
	Set<OnClickListener> mListeners = new HashSet<OnClickListener>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.app_settings);
		
		mScrollView = (ScrollView) findViewById(R.id.scrollView);
		mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
				
		new PanelInfo(R.string.info_queue_title, R.string.info_queue);
		new PanelInfo(R.string.info_queue_selector_title, R.string.info_queue_selector);
		new PanelInfo(R.string.info_queue_widget_title, R.string.info_queue_widget);
		new PanelInfo(R.string.info_queuing_title, R.string.info_queuing);
		

		
		for (PanelInfo p: mInfoStack){
			new AsyncInfoPanelView().execute(this);
		}
	}
	
	private class PanelInfo {
		String mTitle;
		String mInfo;
		
		public PanelInfo(int title, int info){
			Resources r = getResources();
			mTitle = r.getString(title);
			mInfo = r.getString(info);
			if (!mInfoStack.contains(this)){
				mInfoStack.add(this);
			}
		}
		
		public PanelInfo(String title, String info){
			mTitle = title;
			mInfo = info;
			if (!mInfoStack.contains(this)){
				mInfoStack.add(this);
			}
		}
		
		public String getTitle(){
			return mTitle;
		}
		
		public String getInfo(){
			return mInfo;
		}
	}
	
	private class AsyncInfoPanelView extends AsyncTask<Info, Void, InfoPanelView> {
		@Override
		protected InfoPanelView doInBackground(Info... Info) {
			return new InfoPanelView(Info[0], Info[0]);
		}

		@Override
		protected void onPostExecute(InfoPanelView view) {
			if (!mInfoStack.empty()){
				PanelInfo panelInfo = mInfoStack.pop();
				view.setInfo(panelInfo.getTitle(), panelInfo.getInfo());
				view.setSelected(true);
				mListeners.add(view);
				mLinearLayout.addView(view);
			}
			
		}
	}

	public void onClick(View v) {
		for (OnClickListener listener: mListeners){
			if (!listener.equals(v)){
				listener.onClick(v);
			}
		}

		
	}

}
