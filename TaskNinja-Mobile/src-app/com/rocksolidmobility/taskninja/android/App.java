package com.rocksolidmobility.taskninja.android;

import android.app.Application;
import android.content.Context;

import com.rocksolidmobility.android.rsmodel.RSModel;


public class App extends Application {

	private static App mApp;

	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
		RSModel.setContext(this);
	}

	public static Context getContext() {
		return mApp.getBaseContext();
	}	
}
