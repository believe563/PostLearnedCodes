package com.example.demo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.net.Net;

public class MainActivity extends Activity {
	private Button mBtnQuery;
	private EditText mEtFpdm, mEtFphm, mEtYzm;
	private ImageView mIvYzm;
	public static SharedPreferences sp;
	private static String resYzmWrong = "验证码输入错误";
	private static String resFpNotInPool = "未曾进入奖池";
	private static String resFpInButNoMoney = "谢谢您的参与";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		registeListeners();
		init();
	}

	private void init() {
		freshYzm();
		sp = PreferenceManager.getDefaultSharedPreferences(this);
	}

	private void freshYzm() {
		new myAsyncTask().execute(0);
	}

	private void registeListeners() {
		mIvYzm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				freshYzm();
			}
		});
		mBtnQuery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread() {
					@Override
					public void run() {
						String fpdm = mEtFpdm.getText().toString();
						String fphm = mEtFphm.getText().toString();
						String yzm = mEtYzm.getText().toString();
						res = Net.getResult(fpdm, fphm, yzm);
						mHandler.sendEmptyMessage(0);
						freshYzm();
					}
				}.start();
			}
		});
	}

	private String res;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(MainActivity.this, res, 0).show();
		}
	};

	private void initViews() {
		mEtFpdm = (EditText) findViewById(R.id.et_fpdm);
		mEtFphm = (EditText) findViewById(R.id.et_fphm);
		mEtYzm = (EditText) findViewById(R.id.et_yzm);
		mIvYzm = (ImageView) findViewById(R.id.iv_yzm);
		mBtnQuery = (Button) findViewById(R.id.btn_query);
	}

	private Bitmap mBitmap;
	private String code;

	private class myAsyncTask extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... params) {
			byte[] bs = Net.getCodeByte();
			//TODO
			//bs--->String
			code= bs2Str(bs);
			mBitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
			System.out.println(mBitmap);
			return null;
		}

		private String bs2Str(byte[] bs) {
			
			return null;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onPostExecute(Object result) {
			mIvYzm.setImageBitmap(mBitmap);
			mEtYzm.setText(code);
		}
	}
}
