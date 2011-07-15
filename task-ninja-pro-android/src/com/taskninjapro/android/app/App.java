package com.taskninjapro.android.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {

	public static final String PREFS = "TaskNinjaPrefs";
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

}
