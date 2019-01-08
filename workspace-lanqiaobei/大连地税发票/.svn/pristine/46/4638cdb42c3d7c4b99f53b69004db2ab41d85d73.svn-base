package com.innova.reward.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innova.reward.R;
import com.innova.reward.aty.AtyMain;
import com.innova.reward.data.Constant;
import com.innova.reward.util.CommonUtil;
import com.innova.reward.util.DBUtil;
import com.innova.reward.view.MyToast;

public class DataManagerFrag extends BaseFrag {
	/*
	 * 创建该Fragment的视图
	 */
	@Override
	public View onCreateView(LayoutInflater infl, ViewGroup vp, Bundle bundle) {
		View v = infl.inflate(R.layout.lyt_data_manager, null);

		initViews(v);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_clear_no_reward_data:
			DBUtil.deleteData(getActivity(), "谢谢参与");
			refreshTable();
			break;
		case R.id.rl_clear_reward_data:
			DBUtil.deleteData(getActivity(), "%奖");
			refreshTable();
		}
		CommonUtil.sp.edit().putBoolean(Constant.IS_TO_REWARD, false).commit();
		MyToast.toast("已清除", 0);
	}

	@Override
	protected void initViews(View v) {
		v.findViewById(R.id.rl_clear_no_reward_data).setOnClickListener(this);
		v.findViewById(R.id.rl_clear_reward_data).setOnClickListener(this);
	}

	/**
	 * 刷新发票表格
	 */
	private void refreshTable() {
		getActivity().finish();
		myStartActivity(AtyMain.class);
	}

	@Override
	protected void initEvents() {
	}
}
