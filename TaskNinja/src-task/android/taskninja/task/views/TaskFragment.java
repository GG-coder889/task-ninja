package android.taskninja.task.views;

import android.app.Fragment;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.task.Task;
import android.taskninja.views.TitleText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskFragment extends Fragment {
	
	private Task mTask;
	private LinearLayout mLinearLayout;
	private TextView mTitleText;
	
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.task_view, container);
		
		mLinearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
		mTitleText = (TextView) view.findViewById(R.id.titleText);
		
		mTitleText.setText(mTask.toString());
		
		loadSettingsViews();
		
		return getView();
	}

	private void loadSettingsViews() {
		// TODO Auto-generated method stub
		
	}
}
