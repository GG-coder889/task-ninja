package android.taskninja.taskgroup.views;

import android.app.Fragment;
import android.os.Bundle;
import android.taskninja.task.Task;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.tools.OnActionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TaskGroupFragment extends Fragment implements OnActionListener<Task> {
	
	private TaskGroup mGroup;
	OnActionListener<Task> mTaskListener;
	private TaskGroupHeaderView mHeaderView;
	private TaskGroupListView mListView;
	private TaskGroupFooterView mFooterView;
	
	public static TaskGroupFragment getInstance(TaskGroup group, OnActionListener<Task> taskListener ){
		return new TaskGroupFragment(group, taskListener);
	}
	
	private TaskGroupFragment(TaskGroup group, OnActionListener<Task> taskListener ){
		super();
		mGroup = group;
		mTaskListener = taskListener;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mHeaderView = TaskGroupHeaderView.getInstance(getActivity(), mGroup);
		mListView = TaskGroupListView.getInstance(getActivity(), mGroup, this);
		mFooterView = TaskGroupFooterView.getInstance(getActivity(), mGroup);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		inflater.inflate(android.taskninja.R.layout.taskgroup, container);
		LinearLayout root = (LinearLayout) container.findViewById(android.taskninja.R.id.root);
		root.addView(mHeaderView);
		root.addView(mListView);
		root.addView(mFooterView);
		
		return getView();
	}

	@Override
	public void onAction(Task action) {
		mTaskListener.onAction(action);
		
	}
}
