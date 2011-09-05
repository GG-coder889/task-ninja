package taskninja.android;

import taskninja.android.ormlite.DatabaseHelper;
import android.app.Application;

public class App extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	private void configureORMLite(){
		DatabaseHelper.setContext(this);
	}

}
