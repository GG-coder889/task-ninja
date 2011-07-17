package taskninja.tablet;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.taskninja.R;
import android.widget.LinearLayout;




public class Main extends Activity {
	
	private LinearLayout mRoot;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        
        TaskListsFragment taskListsFragment = new TaskListsFragment();
        fragmentTransaction.add(R.id.root, taskListsFragment);
        fragmentTransaction.commit();
        
        
    }
}