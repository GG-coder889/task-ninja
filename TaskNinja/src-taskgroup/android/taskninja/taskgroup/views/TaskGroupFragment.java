package android.taskninja.taskgroup.views;

import java.util.LinkedHashSet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.task.Task;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.tools.OnActionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TaskGroupFragment extends Fragment {
	
	private LinkedHashSet<OnActionListener<Task>> mActionListeners = new LinkedHashSet<OnActionListener<Task>>();
	
	private TaskGroup mGroup;
	
	private TaskGroupHeaderView mHeaderView;
	private TaskGroupListView mListView;
	private TaskGroupFooterView mFooterView;
	
	public static TaskGroupFragment getInstance(TaskGroup group){
		return new TaskGroupFragment(group);
	}
	
	public TaskGroupFragment(TaskGroup group){
		super();
		mGroup = group;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		mHeaderView = TaskGroupHeaderView.getInstance(getActivity(), mGroup);
		mListView = TaskGroupListView.getInstance(getActivity(), mGroup);
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

	public void addOnActionListener(OnActionListener<Task> listener){
		mActionListeners.add(listener);
	}
	
	public void removeOnActionListener(OnActionListener<Task> listener){
		mActionListeners.remove(listener);
	}
}
