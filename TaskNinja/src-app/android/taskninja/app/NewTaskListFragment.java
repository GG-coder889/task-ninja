package android.taskninja.app;

import android.app.Fragment;
import android.os.Bundle;
import android.taskninja.taskgroup.views.NewTaskListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewTaskListFragment extends Fragment {
	
	private NewTaskListView mNewTaskListView;
	
	public static NewTaskListFragment getInstance(){
		return new NewTaskListFragment();
	}
	
//	protected NewTaskListView(){
//		super();
//		mSetting = setting;
//	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mNewTaskListView = NewTaskListView.getInstance(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return mNewTaskListView;
	}

}
