package com.innova.reward.aty;

import java.util.HashMap;
import java.util.Random;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.innova.reward.R;
import com.innova.reward.data.Constant;
import com.innova.reward.util.NetUtil;
import com.innova.reward.view.MyToast;
import com.innova.reward.view.WaitDialog;

public class AtyRegister extends FinalActivity implements Callback {
	private void init() {
		((TextView) findViewById(R.id.tv_title)).setText(R.string.register);
		initSDK();
		openRegistePage();
	}

	private boolean ready;
	@ViewInject(id = R.id.et_id_register)
	private EditText mEtId;
	@ViewInject(id = R.id.et_pwd_register)
	private EditText mEtPwd;
	@ViewInject(id = R.id.et_pwd_again_register)
	private EditText mEtPwdAgain;
	@ViewInject(id = R.id.btn_register, click = "onClick")
	private Button mBtnRegister;
	private final static int FLAG_ENABLE_REGISTERBUTTON = 0;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FLAG_ENABLE_REGISTERBUTTON:
				mBtnRegister.setEnabled(true);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.aty_register);

		init();
	}

	private void initSDK() {
		// 初始化短信SDK
		SMSSDK.initSDK(this, Constant.APPKEY, Constant.APPSECRET);
		final Handler handler = new Handler(this);
		EventHandler eventHandler = new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
		ready = true;
	}

	protected void onDestroy() {
		if (ready) {
			// 销毁回调监听接口
			SMSSDK.unregisterAllEventHandler();
		}
		super.onDestroy();
	}

	private void openRegistePage() {
		RegisterPage rp = new RegisterPage();
		rp.setRegisterCallback(new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				// 解析注册结果
				if (result == SMSSDK.RESULT_COMPLETE) {
					@SuppressWarnings("unchecked")
					HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
					String country = (String) phoneMap.get("country");
					String phone = (String) phoneMap.get("phone");
					// 提交用户信息
					registerUser(country, phone);
				}
			}
		});
		rp.show(this);
	}

	public boolean handleMessage(Message msg) {

		int event = msg.arg1;
		int result = msg.arg2;
		Object data = msg.obj;
		if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
			// 短信注册成功后，返回MainActivity,然后提示新好友
			if (result == SMSSDK.RESULT_COMPLETE) {
				Toast.makeText(this, R.string.smssdk_user_info_submited, Toast.LENGTH_SHORT).show();
			} else {
				((Throwable) data).printStackTrace();
			}
		}
		return false;
	}

	// 提交用户信息
	private void registerUser(String country, String phone) {
		Random r = new Random();
		String uid = String.valueOf(Math.abs(r.nextInt()));
		String nickName = "SmsSDK_User_" + uid;
		String avatar = Constant.AVATARS;
		SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
		mEtId.setText(phone);
		// TODO 去除SMSDK中残留的Toast
		// INotificationManager service =
		// INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			if (validateRegisterUserInfo()) {
				submitUserInfo();
			}
		}
	}

	/**
	 * 提交用户信息
	 */
	private void submitUserInfo() {
		mBtnRegister.setEnabled(false);
		if (NetUtil.IsHaveInternet()) {
			FinalHttp fh = new FinalHttp();
			fh.configTimeout(10000);
			final Dialog dialog = WaitDialog.createLoadingDialog(this, R.string.submiting);
			fh.get(Constant.BASE_URL + "CoreServlet?action=registerUser&username=" + mEtId.getText().toString()
					+ "&password=" + mEtPwd.getText().toString(), new AjaxCallBack<Object>() {
				@Override
				public void onSuccess(Object t) {
					String msg = (String) t;
					if (msg.equals(Constant.SUCCESS)) {
						MyToast.toast(R.string.success, 0);
					} else if (msg.equals(Constant.DUPLICATE)) {
						MyToast.toast(R.string.duplicated_phone_number, 0);
					} else if (msg.equals(Constant.EXCEPTION)) {
						MyToast.toast(R.string.fail, 0);
					}
					dialog.dismiss();
					mBtnRegister.setEnabled(true);
				}

				@Override
				public void onStart() {
					dialog.show();
				}

				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					MyToast.toast(R.string.fail, 0);
					MyToast.toast(strMsg, 0);
					dialog.dismiss();
					mBtnRegister.setEnabled(true);
				}
			});
		} else {
			MyToast.toast(R.string.net_is_bad, 0);
		}
	}

	/**
	 * 处理注册是的数据验证
	 */
	private boolean validateRegisterUserInfo() {
		if (!mEtId.getText().toString().matches("1[3,5,7,8][\\d]{9}")) {
			MyToast.toast(getResources().getString(R.string.phone_format_error), 0);
			return false;
		} else if (TextUtils.isEmpty(mEtPwd.getText().toString())
				|| TextUtils.isEmpty(mEtPwdAgain.getText().toString())) {
			MyToast.toast(getResources().getString(R.string.pwd_no_empty), 0);
			return false;
		} else if (!mEtPwd.getText().toString().equals(mEtPwdAgain.getText().toString())) {
			MyToast.toast(R.string.twice_pwd_not_same, 0);
			return false;
		}
		return true;
	}
}
