package com.innova.reward.aty;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.innova.reward.R;
import com.innova.reward.data.Constant;
import com.innova.reward.util.CommonUtil;
import com.innova.reward.util.NetUtil;
import com.innova.reward.view.MyToast;
import com.innova.reward.view.WaitDialog;

public class AtyLogin extends FinalActivity {
	@ViewInject(id = R.id.btn_login, click = "onClick")
	private Button mBtnLogin;
	@ViewInject(id = R.id.tv_register_user, click = "onClick")
	private TextView mTvRegisterUser;
	@ViewInject(id = R.id.et_id_login)
	private EditText mEtId;
	@ViewInject(id = R.id.et_pwd_login)
	private EditText mEtPwd;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		// 已经登录过了
		if (CommonUtil.sp.getBoolean(Constant.IS_LOGINED, false)) {
			finish();
			startActivity(new Intent(this, AtyMain.class));
		} else {
			setContentView(R.layout.aty_login);
			// 介绍界面已经看过了
			CommonUtil.sp.edit().putBoolean(Constant.FIRST_USE, false).commit();
		}
	}

	/*
	 * 所有的点击事件都放在这里
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			validateLogin();
			break;
		case R.id.tv_register_user:
			startActivity(new Intent(this, AtyRegister.class));
			// TODO把后台放到服务器上
			break;
		case R.id.tv_can_not_login:
			// TODO 无法登陆的逻辑
		}
	}

	/**
	 * 验证登录
	 * 
	 * @return
	 */
	private void validateLogin() {
		mBtnLogin.setEnabled(false);
		String username = mEtId.getText().toString();
		String password = mEtPwd.getText().toString();
		if (!username.equals("") && !password.equals("")) {
			if (NetUtil.IsHaveInternet()) {
				FinalHttp fh = new FinalHttp();
				fh.configTimeout(10000);
				final Dialog dialog = WaitDialog.createLoadingDialog(this, R.string.submiting);
				fh.get(Constant.BASE_URL + "CoreServlet?action=login&username=" + username + "&password=" + password,
						new AjaxCallBack<Object>() {
							@Override
							public void onSuccess(Object t) {
								String msg = (String) t;
								if (msg.equals(Constant.SUCCESS)) {
									MyToast.toast(R.string.success, 0);
									finish();
									CommonUtil.sp.edit().putBoolean(Constant.IS_LOGINED, true).commit();
									startActivity(new Intent(AtyLogin.this, AtyMain.class));
								} else {
									MyToast.toast("数据有误", 0);
								}
								dialog.dismiss();
								mBtnLogin.setEnabled(true);
							}

							@Override
							public void onStart() {
								dialog.show();
							}

							@Override
							public void onFailure(Throwable t, int errorNo, String strMsg) {
								MyToast.toast(R.string.fail, 0);
								dialog.dismiss();
								mBtnLogin.setEnabled(true);
							}
						});
			} else {
				MyToast.toast(R.string.net_is_bad, 0);
				mBtnLogin.setEnabled(true);
			}
		} else {
			MyToast.toast("数据不能为空", 0);
			mBtnLogin.setEnabled(true);
		}
	}
}
