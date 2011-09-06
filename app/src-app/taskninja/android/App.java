package taskninja.android;

import com.j256.ormlite.table.TableUtils;

import taskninja.android.activities.NinjaTasksActivity;
import taskninja.android.models.NinjaList;
import taskninja.android.ormlite.DatabaseHelper;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class App extends Application {
	
	private boolean DEVELOPMENT = true;
	
	private static App mApp;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
		
		configureORMLite();
	}
	

	public static Context getContext(){
		return mApp.getBaseContext();
	}
	
	private void configureORMLite(){
		DatabaseHelper.setContext(getContext());
	}

	public static void launch(NinjaList item) {
		Intent intent = new Intent(getContext(), NinjaTasksActivity.class);
		intent.putExtra(NinjaList.class.getSimpleName(), item.getId());
		mApp.startActivity(intent);
	}
	
	public static void fail(Activity activity){
		
	}

}
