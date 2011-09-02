package com.rocksolidmobility.taskninja.android;

import android.app.Application;
import android.content.Context;

import com.rocksolidmobility.taskninja.android.dbmodel.Db_Model;


public class App extends Application {

	private static App mApp;

	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
		Db_Model.setContext(this);
	}

	public static Context getContext() {
		return mApp.getBaseContext();
	}	
}
