package android.taskninja.taskgroup.views;

import java.util.LinkedHashSet;

import android.app.Fragment;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.task.dbtask.Task;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.tools.OnActionListener;
import android.taskninja.views.AddButton;
import android.taskninja.views.TitleText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TaskGroupFragment extends Fragment implements OnItemClickListener {
	
	private LinkedHashSet<OnActionListener<Task>> mActionListeners = new LinkedHashSet<OnActionListener<Task>>();
	
	private TaskGroup mGroup;
	private ListView mListView;
	private ArrayAdapter<Task> mAdapter;
	
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
		mListView = new ListView(getActivity());
		mListView.addHeaderView(TitleText.getInstance(getActivity(), mGroup.toString()));
		
		mAdapter = new ArrayAdapter<Task>(getActivity(), R.layout.text_list_item, mGroup);
		mListView.setAdapter(mAdapter);
		
		mListView.addFooterView(AddButton.getInstance(getActivity()));
		mListView.addFooterView(AddButton.getInstance(getActivity()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		return mListView;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (position < mGroup.size()){
			
			Task task = mGroup.get(position);
			
			if (task != null){
				for (OnActionListener<Task> listener: mActionListeners){
					listener.onAction(task);
				}
			}
			
		}
	}
	
	public void addOnActionListener(OnActionListener<Task> listener){
		mActionListeners.add(listener);
	}
	
	public void removeOnActionListener(OnActionListener<Task> listener){
		mActionListeners.remove(listener);
	}
}
