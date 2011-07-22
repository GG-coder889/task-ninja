package android.taskninja.app;

import java.util.LinkedHashSet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.task.Task;
import android.taskninja.task.views.TaskFragment;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.taskgroup.views.TaskGroupFragment;
import android.taskninja.tools.IterTool;
import android.taskninja.tools.OnActionListener;
import android.widget.Toast;




public class TaskGroupActivity extends AbsractBaseActivity implements OnActionListener<Task> {
	
	private TaskGroupFragment mGroupFrag;
	private TaskFragment mTaskFrag;
	
	private FragmentManager mFragMan;;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskgroup);
        
        String id = getIntent().getStringExtra(Db_Model.BuiltIn.ID.name());
        TaskGroup group = TaskGroup.get(id);
        
        if (group == null) {
        	LinkedHashSet<TaskGroup> groups = TaskGroup.getAll();
        	if (groups.size() == 0){
        		group = TaskGroup.getInstance("Default");
        	} else {
        		for (TaskGroup g: groups ){
        			group = g;
        			break;
        		}
        	}
        }
        	
        mFragMan = getFragmentManager();
        
        mGroupFrag = new TaskGroupFragment(group);
        mGroupFrag.addOnActionListener(this);
        
        mFragMan.beginTransaction().add(R.id.leftRoot, mGroupFrag).commit();
    }


	@Override
	public void onAction(Task action) {
		mTaskFrag = TaskFragment.getInstance(action);
		mFragMan.beginTransaction().replace(R.id.rightRoot, mTaskFrag).commit();
	}
    

}