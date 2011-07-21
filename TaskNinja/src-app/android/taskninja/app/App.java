package android.taskninja.app;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup;
import android.taskninja.tools.Background;
import android.taskninja.tools.BackgroundManager;
import android.taskninja.tools.Color;
import android.taskninja.tools.ColorManager;

public class App extends Application {
	
	private boolean DEVELOPER_MODE = true;

	private static App mApp;

	@Override
	public void onCreate() {
		super.onCreate();
		Db_Model.setContext(this);
		
		mApp = this;
		
		if (DEVELOPER_MODE) {
			this.getSharedPreferences("Db_TaskGroup", 4).edit().clear().commit();
			this.getSharedPreferences("Db_TaskCollection", 4).edit().clear().commit();
			this.getSharedPreferences("Db_Task", 4).edit().clear().commit();
		}
	}

	public static App getApp() {
		return mApp;
	}

	public static Context getContext() {
		return mApp.getBaseContext();
	}
	
	public static Drawable get(Background background, Context context){
		return BackgroundManager.get(background, context);
	}
	
	public static int getResource(Color color){
		return ColorManager.getResource(color);
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
