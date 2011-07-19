package android.taskninja.app;

import android.app.ActionBar;
import android.app.Activity;
import android.taskninja.R;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public abstract class AbsractBaseActivity extends Activity {
	
//	protected LinearLayout mRoot;
//    
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.main);
////
////        mRoot = (LinearLayout) findViewById(R.id.root);
//        
//    }
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			
		for (Db_TaskGroup group :Db_TaskGroup.getAll()){
			MenuItem item = menu.add(group.toString());
		}
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
//			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.queue:
//			startActivity(new Intent(this, QueueActivity.class));
			return true;
		case R.id.tasks:
//			startActivity(new Intent(this, Info.class));
			return true;
		default:
//			item.get
//			startActivity(new Intent(this, Info.class));
			return true;
		}
	}

}
