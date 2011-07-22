package android.taskninja.task.views;

import java.util.HashSet;

import android.app.Fragment;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.app.App;
import android.taskninja.task.Task;
import android.taskninja.tools.Background;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskFragment extends Fragment {
	
	private Task mTask;
	private LinearLayout mLinearLayout;
	private TextView mTitleText;
	
	private TaskSettingsTitleView mTaskSettingsTitleView;
	
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
		
	}


}
