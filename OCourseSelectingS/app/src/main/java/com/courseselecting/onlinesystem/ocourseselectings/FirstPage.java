package com.courseselecting.onlinesystem.ocourseselectings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * 开始时的欢迎界面
 * 
 */
public class FirstPage extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 去掉标题 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.firstpage);
		/* 全屏设置，隐藏窗口所有装饰 */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FirstPage.this, MainActivity.class);
				startActivity(intent);
				FirstPage.this.finish();
			}
		}, 3000);

	}
}
