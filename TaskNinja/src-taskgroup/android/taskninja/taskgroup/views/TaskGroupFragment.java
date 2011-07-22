package android.taskninja.taskgroup.views;

import java.util.LinkedHashSet;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.app.App;
import android.taskninja.task.Task;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.tools.Background;
import android.taskninja.tools.OnActionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TaskGroupFragment extends Fragment implements OnItemClickListener {
	
	private LinkedHashSet<OnActionListener<Task>> mActionListeners = new LinkedHashSet<OnActionListener<Task>>();
	
	private TaskGroup mGroup;
	private ListView mListView;
	
//	private FragmentManager mFragMan;
	
	private EditGroupDialog mEditDialog;
	
	private ArrayAdapter<Task> mAdapter;
	private View mTitleView;
	
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
		
//		mEditDialog = EditGroupDialog.getInstance(mGroup);
		
		mListView = new ListView(getActivity());
		mListView.setOnItemClickListener(this);
		
		mTitleView = HeaderView.getInstance(getActivity(), mGroup.toString());
		mListView.addHeaderView(mTitleView);
		
		mAdapter = new ArrayAdapter<Task>(getActivity(), R.layout.text_list_item, mGroup);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		mListView.setBackgroundResource(android.R.drawable.screen_background_dark_transparent);
		return mListView;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		switch(position){
		case 0:
			onEditGroup();
			break;
		}
		if (mTitleView.equals(arg1)){
//			Toast.makeText(getActivity(), "Hello edit", Toast.LENGTH_SHORT).show();
			onEditGroup();
		} else {
			if (position -1 < mGroup.size()){
				Task task = mGroup.get(position-1);
				
				if (task != null){
					for (OnActionListener<Task> listener: mActionListeners){
						listener.onAction(task);
					}
				}
			}
			
		}
		
	}
	
	private void onEditGroup() {
//		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
//		EditGroupDialog newFragment = EditGroupDialog.getInstance(mGroup);
//	    newFragment.show(ft, "dialog");
//	    newFragment.show(manager, tag)
//		mEditDialog.dismiss();
//		mEditDialog.show(getFragmentManager(), "EditGroup");
	}

	public void addOnActionListener(OnActionListener<Task> listener){
		mActionListeners.add(listener);
	}
	
	public void removeOnActionListener(OnActionListener<Task> listener){
		mActionListeners.remove(listener);
	}
}
