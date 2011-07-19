package android.taskninja.taskgroup.views;

import java.util.LinkedHashSet;

import android.app.Fragment;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup;
import android.taskninja.tools.IterTool;
import android.taskninja.tools.OnActionListener;
import android.taskninja.views.TitleText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TaskGroupListFragment extends Fragment implements OnItemClickListener {
	
	private LinkedHashSet<OnActionListener<Db_TaskGroup>> mActionListeners = new LinkedHashSet<OnActionListener<Db_TaskGroup>>();
	private LinkedHashSet<Db_TaskGroup> mGroups = Db_TaskGroup.getAll();
	private ListView mListView;
	
	
	public static TaskGroupListFragment getInstance(){
		return new TaskGroupListFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mListView = new ListView(getActivity());
		
		mListView.addHeaderView(TitleText.getInstance(getActivity(), "Task Lists"));
		
//		loadGroups();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//		getListView().addHeaderView(TitleText.getInstance(getActivity(), "Task Lists"));
//		AddButton addButton = AddButton.getInstance(getActivity());
//		addButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//				
//			}
//		});
//		getListView().addFooterView(addButton);
		
		return mListView;
	}
	private void loadGroups() {
		String[] hello = {"Hello1","hello2"};
//		setListAdapter(new ArrayAdapter(getActivity(), R.layout.text_list_item, hello));
//		getListView().setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (position < mGroups.size()){
			
			Db_TaskGroup group = new IterTool<Db_TaskGroup>().get(mGroups.iterator(), position);
			
			if (group != null){
				for (OnActionListener<Db_TaskGroup> listener: mActionListeners){
					listener.onAction(group);
				}
			}
			
		}
	}
	
	public void addOnActionListener(OnActionListener<Db_TaskGroup> listener){
		mActionListeners.add(listener);
	}
	
	public void removeOnActionListener(OnActionListener<Db_TaskGroup> listener){
		mActionListeners.remove(listener);
	}
	
	
	
}
