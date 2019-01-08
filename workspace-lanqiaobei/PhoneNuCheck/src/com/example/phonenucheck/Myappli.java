package com.example.phonenucheck;

import android.app.Application;
import android.content.Context;

public class Myappli extends Application {
	private static Context context;

	public static Context getContext() {
		return context;
	}
	@Override
	public void onCreate() {
		context=getApplicationContext();
	}
}
