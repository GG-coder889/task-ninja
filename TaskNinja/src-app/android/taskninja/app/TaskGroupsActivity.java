package android.taskninja.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.taskgroup.views.TaskGroupListFragment;




public class TaskGroupsActivity extends AbsractBaseActivity {
	
//	private ListFrame mListFrame;
//	private TaskListsView mTaskListsView;
//	private FragmentFrame mFragmentFrame;
//	private Fragment mFragment;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskgroups);

        
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        
        fragmentTransaction.add(R.id.root, TaskGroupListFragment.getInstance());
        fragmentTransaction.commit();
        
//        mListFrame = ListFrame.getInstance(this);
//        mRoot.addView(mListFrame);
//        mTaskListsView = TaskListsView.getInstance(this);
//        mListFrame.addView(mTaskListsView);
        
//        mFragmentFrame = FragmentFrame.getInstance(this);
//        mRoot.addView(mFragmentFrame);
//        
//        mFragment = NewTaskListFragment.getInstance();
//        
//        showFragment();
    }
    
//    private void showFragment(){
//    	FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        
//        fragmentTransaction.add(mFragmentFrame.getId(), mFragment);
//        fragmentTransaction.commit();
//    }
//    
//    private static class FragmentFrame extends FrameLayout {
//
//		private FragmentFrame(Context context) {
//			super(context);
//			setId(R.id.settings);
//		}
//
//		public static FragmentFrame getInstance(Context context) {
//			return new FragmentFrame(context);
//		}
//    	
//    }
//    
//    private static class ListFrame extends FrameLayout {
//
//		private ListFrame(Context context) {
//			super(context);
//			setId(R.id.lists);
//		}
//
//		public static ListFrame getInstance(Context context) {
//			return new ListFrame(context);
//		}
//    	
//    }
//    
//    private void deleteLists(){
//    	for (Db_TaskGroup list : Db_TaskGroup.getAll()){
//    		list.delete();
//    	}
//    }
}