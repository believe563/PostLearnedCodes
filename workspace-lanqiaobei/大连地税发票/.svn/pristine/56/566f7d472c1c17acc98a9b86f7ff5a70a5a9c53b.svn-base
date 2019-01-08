package com.innova.reward.util;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * 公用的一个工具方法,log......
 * 
 * @author guo
 * 
 */
public class CommonUtil {
	public static void log(String msg) {
		Log.i("msg", msg);
	}

	public static SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyAppli.getContext());

	public static boolean getExternalStorageStatus() {
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	public static String TESSBASE_PATH;
	static {
		if (getExternalStorageStatus()) {
			TESSBASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tessdata";
		} else {
			TESSBASE_PATH = null;
		}
	}
}
