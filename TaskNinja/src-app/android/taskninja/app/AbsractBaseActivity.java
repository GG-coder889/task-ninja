package android.taskninja.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.taskninja.R;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.taskgroup.TaskGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public abstract class AbsractBaseActivity extends Activity {
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			
		for (TaskGroup group :TaskGroup.getAll()){
			MenuItem item = menu.add(group.toString());
			Intent i = new Intent(this, TaskGroupActivity.class);
			i.putExtra(Db_Model.BuiltIn.ID.name(), group.getId());
			item.setIntent(i);
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
			Intent i = item.getIntent();
			if (i != null){
				startActivity(i);
				return true;
			}
			return false;
		}
	}

}
