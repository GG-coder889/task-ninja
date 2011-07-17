package taskninja.core.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {

	private static App mApp;

	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
	}

	public static App getApp() {
		return mApp;
	}

	public static Context getContext() {
		return mApp.getBaseContext();
	}
	
	// Settings
	static final String PREFS = "settings";
	static final int PREFS_MODE = 0;
	
	static final String PRIORITY = "PRIORITY";
	static final boolean PRIORITY_DEFAULT = true;
	
	static final String DUE_DATE = "DUE_DATE";
	static final boolean DUE_DATE_DEFAULT = true;
	
	static final String ALERTS = "ALERTS";
	static final boolean ALERTS_DEFAULT = true;
	
	static final String QUEUING = "QUEUING";
	static final boolean QUEUING_DEFAULT = true;
	
	static final String SUBTASKING = "SUBTASKING";
	static final boolean SUBTASKING_DEFAULT = false;
	
	static final String QUEUE = "QUEUE";
	static final String QUEUE_ALERT = "queue_alert";
	static final String QUEUE_ALERT_HOUR = "queue_alert_hour";
	static final String QUEUE_ALERT_MINUTE = "queue_alert_minute";
	
	static final int MAX_TITLE_LENGTH = 70;
}
