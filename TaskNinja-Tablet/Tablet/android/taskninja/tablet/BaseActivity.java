package android.taskninja.tablet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.core.views.buttons.AddButton;
import android.taskninja.tablet.settings.SettingsActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;


public abstract class BaseActivity extends Activity {
	
	protected LinearLayout mRoot;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mRoot = (LinearLayout) findViewById(R.id.root);
        
    }
	
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.options_menu, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.settings:
//			startActivity(new Intent(this, SettingsActivity.class));
//			return true;
//		case R.id.lists:
//			startActivity(new Intent(this, HomeActivity.class));
//			return true;
//		case R.id.queue:
////			startActivity(new Intent(this, QueueActivity.class));
//			return true;
//		case R.id.tasks:
////			startActivity(new Intent(this, Info.class));
//			return true;
//		default:
//			return true;
//		}
//	}

}
