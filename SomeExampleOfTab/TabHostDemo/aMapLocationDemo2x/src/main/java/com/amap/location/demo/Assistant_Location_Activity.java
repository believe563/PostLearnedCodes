package com.amap.location.demo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.amap.api.location.AMapLocationClient;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 低功耗定位模式功能演示
 *
 * @创建时间： 2015年11月24日 下午4:24:07 @项目名称： AMapLocationDemo2.x
 * 
 * @author hongming.wang
 * @文件名称: Battery_Saving_Activity.java
 * @类型名称: Battery_Saving_Activity
 */
public class Assistant_Location_Activity extends Activity implements OnClickListener {
	private Button btAssistant;
	private Button btLocation;

	private TextView tvInfo;
	private TextView tvReult;

	private AMapLocationClient locationClient = null;

	LocationThread mThread = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assistant_location);
		setTitle(R.string.title_assistantLocation);

		btAssistant = (Button) findViewById(R.id.bt_assistant);
		btLocation = (Button) findViewById(R.id.bt_location);

		tvInfo = (TextView) findViewById(R.id.tv_info);
		tvReult = (TextView) findViewById(R.id.tv_result);

		locationClient = new AMapLocationClient(getApplicationContext());
		btAssistant.setOnClickListener(this);
		btLocation.setOnClickListener(this);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopThread();
		if (null != locationClient) {
			locationClient.stopAssistantLocation();
			locationClient.onDestroy();
		}
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bt_assistant) {
			if (btAssistant.getText().equals(getResources().getString(R.string.startAssistantLocation))) {
				btAssistant.setText(getResources().getString(R.string.stopAssistantLocation));
				locationClient.startAssistantLocation();
				tvInfo.setVisibility(View.VISIBLE);
				btLocation.setEnabled(true);
			} else {
				btAssistant.setText(getResources().getString(R.string.startAssistantLocation));
				locationClient.stopAssistantLocation();
				tvInfo.setVisibility(View.GONE);
				tvReult.setText("");
				btLocation.setEnabled(false);
			}
		} else if (v.getId() == R.id.bt_location) {
			// 模拟一次浏览器请求
			mHandler.sendEmptyMessage(Utils.MSG_LOCATION_START);
		}
	}

	Handler mHandler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case Utils.MSG_LOCATION_START:
				tvReult.setText("正在定位...");
				startLocation();
				break;
			// 定位完成
			case Utils.MSG_LOCATION_FINISH:
				String result = (String) msg.obj;
				tvReult.setText(result);
				break;
			default:
				break;
			}
		};
	};
	
	private void startLocation(){
		if(null == mThread){
			mThread = new LocationThread("mockUrl");
			mThread.start();
		}
		mThread.isRun = true;
	}
	
	private void stopThread(){
		if(null != mThread){
			mThread.isRun = false;
			mThread.interrupt();
			mThread = null;
		}
	}
	
	class LocationThread extends Thread{
		private boolean isRun = false;
		public LocationThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				if (isRun) {
					HttpURLConnection urlConnection = null;
					String result = "";
					try {
						URL url = new URL("http://127.0.0.1:43689/");
						urlConnection = (HttpURLConnection) url.openConnection();
						if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
							urlConnection.setRequestProperty("Connection", "close");
						}
						urlConnection.connect();
						InputStream inputStream = urlConnection.getInputStream();
						byte[] bytes = new byte[1024];
						inputStream.read(bytes);
						result = new String(bytes, "utf-8");
						urlConnection.disconnect();
						urlConnection = null;
					} catch (Throwable e) {
						e.printStackTrace();
					}
					Message msg = mHandler.obtainMessage();
					msg.obj = result;
					msg.what = Utils.MSG_LOCATION_FINISH;
					mHandler.sendMessage(msg);
					isRun = false;
				}
			}
		}
	}
}
