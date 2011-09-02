package android.taskninja.task.views;

import java.util.HashSet;
import java.util.Set;

import android.app.Fragment;
import android.os.Bundle;
import com.rocksolidmobility.taskninja.android.R;
import android.taskninja.task.Task;
import android.taskninja.tools.MSG;
import android.taskninja.tools.OnActionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskFragment extends Fragment implements OnActionListener<MSG> {
	
	private Task mTask;
	private LinearLayout mLinearLayout;
	
	private final Set<OnActionListener<MSG>> mMSGListeners = new HashSet<OnActionListener<MSG>>();
	
	private TaskSettingsTitleView mTaskSettingsTitleView;
	private TaskSingleNotifiactionView mTaskSingleNotifiactionView;
	private TaskRecurringNotifiactionView mTaskRecurringNotifiactionView;
	private TaskButtonBarView mTaskButtonBarView;
	
	public static TaskFragment getInstance(Task task){
		return new TaskFragment(task);
	}
	
	public TaskFragment(Task task){
		super();
		mTask = task;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mTaskSettingsTitleView = TaskSettingsTitleView.getInstance(getActivity(), mTask);
		mMSGListeners.add(mTaskSettingsTitleView);
		
		mTaskSingleNotifiactionView = TaskSingleNotifiactionView.getInstance(getActivity(), mTask);
		mMSGListeners.add(mTaskSingleNotifiactionView);
		
		mTaskRecurringNotifiactionView = TaskRecurringNotifiactionView.getInstance(getActivity(), mTask);
		mMSGListeners.add(mTaskRecurringNotifiactionView);
		
		mTaskButtonBarView = TaskButtonBarView.getInstance(getActivity(), this);

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.task_view, container);
		
		mLinearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
		
		loadSettingsViews();
		
		return getView();
	}

	private void loadSettingsViews() {
		
		mLinearLayout.removeAllViews();
		
		mLinearLayout.addView(mTaskSettingsTitleView);
		mLinearLayout.addView(mTaskSingleNotifiactionView);
		mLinearLayout.addView(mTaskRecurringNotifiactionView);
		mLinearLayout.addView(mTaskButtonBarView);
		
	}
	

	@Override
	public void onAction(MSG action) {
		switch (action) {
		case SAVE:
			break;
		case CANCEL:
			break;
		case DELETE:
			break;
		default:
			break;
		}
		
		for (OnActionListener<MSG> listener: mMSGListeners){
			listener.onAction(action);
		}
		
	}
	
	
	


}
