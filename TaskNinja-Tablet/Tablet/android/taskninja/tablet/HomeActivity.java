package android.taskninja.tablet;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.core.views.tasklists.TaskListsView;
import android.widget.LinearLayout;




public class HomeActivity extends BaseActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRoot.addView(TaskListsView.getInstance(this));
        
        
    }
}