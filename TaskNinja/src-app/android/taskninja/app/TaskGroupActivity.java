package android.taskninja.app;

import java.util.LinkedHashSet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.taskgroup.views.TaskGroupFragment;
import android.taskninja.tools.IterTool;
import android.widget.Toast;




public class TaskGroupActivity extends AbsractBaseActivity {
	
	
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
        
        Toast.makeText(this, "Hello from "+group.toString()+" group.", Toast.LENGTH_SHORT).show();
        	
    	FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        
        fragmentTransaction.add(R.id.root, new TaskGroupFragment(group));
        fragmentTransaction.commit();

        
    }
    

}