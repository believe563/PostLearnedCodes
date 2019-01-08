package com.innova.reward.util;

import android.app.Application;
import android.content.Context;

/**
 * 编写自己的Application，管理全局状态信息，比如Context
 */
public class MyAppli extends Application {
	private static Context c;

	@Override
	public void onCreate() {
		// 获取Context
		c = getApplicationContext();
	}

	// 返回
	public static Context getContext() {
		return c;
	}
}
