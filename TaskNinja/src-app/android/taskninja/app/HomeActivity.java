package android.taskninja.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup;
import android.taskninja.taskgroup.views.TaskListsView;
import android.widget.FrameLayout;




public class HomeActivity extends AbsractBaseActivity {
	
	private ListFrame mListFrame;
	private TaskListsView mTaskListsView;
	private FragmentFrame mFragmentFrame;
	private Fragment mFragment;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListFrame = ListFrame.getInstance(this);
        mRoot.addView(mListFrame);
        mTaskListsView = TaskListsView.getInstance(this);
        mListFrame.addView(mTaskListsView);
        
        mFragmentFrame = FragmentFrame.getInstance(this);
        mRoot.addView(mFragmentFrame);
        
        mFragment = NewTaskListFragment.getInstance();
        
        showFragment();
    }
    
    private void showFragment(){
    	FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        
        fragmentTransaction.add(mFragmentFrame.getId(), mFragment);
        fragmentTransaction.commit();
    }
    
    private static class FragmentFrame extends FrameLayout {

		private FragmentFrame(Context context) {
			super(context);
			setId(R.id.settings);
		}

		public static FragmentFrame getInstance(Context context) {
			return new FragmentFrame(context);
		}
    	
    }
    
    private static class ListFrame extends FrameLayout {

		private ListFrame(Context context) {
			super(context);
			setId(R.id.lists);
		}

		public static ListFrame getInstance(Context context) {
			return new ListFrame(context);
		}
    	
    }
    
    private void deleteLists(){
    	for (Db_TaskGroup list : Db_TaskGroup.getAll()){
    		list.delete();
    	}
    }
}