package android.taskninja.task.views;

import android.app.Activity;
import android.taskninja.R;
import android.taskninja.tools.MSG;
import android.taskninja.tools.OnActionListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class TaskButtonBarView extends LinearLayout implements OnClickListener {

	private Activity mActivity;
	private OnActionListener<MSG> mListener;
	
	public static TaskButtonBarView getInstance(Activity activity, OnActionListener<MSG> listener){
		return new TaskButtonBarView(activity, listener);
	}
	
	private TaskButtonBarView(Activity activity, OnActionListener<MSG> listener) {
		super(activity);
		mActivity = activity;
		mListener = listener;
		
		mActivity.getLayoutInflater().inflate(android.taskninja.R.layout.task_button_bar, this);
		
		findViewById(R.id.delete).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.save).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delete:
			mListener.onAction(MSG.DELETE);
			break;
		case R.id.cancel:
			mListener.onAction(MSG.CANCEL);
			break;
		case R.id.save:
			mListener.onAction(MSG.SAVE);
			break;
		default:
			break;
		}
		
	}

}
