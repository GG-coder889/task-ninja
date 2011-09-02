package android.taskninja.app;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import com.rocksolidmobility.taskninja.android.R;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.taskgroup.views.TaskGroupNewDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public abstract class AbsractBaseActivity extends Activity {
	
	private void onNewGroup(){
	    FragmentTransaction ft = getFragmentManager().beginTransaction();
	    DialogFragment newFragment = TaskGroupNewDialog.getInstance();
	    newFragment.show(ft, "dialog");
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			getActionBar().setBackgroundDrawable(getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));
			
		for (TaskGroup group :TaskGroup.getAll()) {
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
		case R.id.newGroup:
			onNewGroup();
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
