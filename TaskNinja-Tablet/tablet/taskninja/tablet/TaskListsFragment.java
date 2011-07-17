package taskninja.tablet;

import taskninja.core.views.tasklists.TaskListsView;
import android.app.Fragment;
import android.os.Bundle;
import android.taskninja.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TaskListsFragment extends Fragment {
	
	private TaskListsView mTasksListsView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mTasksListsView = TaskListsView.getInstance(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return mTasksListsView;
	}
}
