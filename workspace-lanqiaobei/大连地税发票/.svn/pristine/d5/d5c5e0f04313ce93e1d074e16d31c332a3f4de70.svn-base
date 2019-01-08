package com.innova.reward.aty;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.innova.reward.R;
import com.innova.reward.data.Constant;
import com.innova.reward.frag.InFrag;
import com.innova.reward.frag.RewardQueryFrag;
import com.innova.reward.frag.PersonalSetFrag;
import com.innova.reward.util.CommonUtil;
import com.innova.reward.util.DBUtil;
import com.innova.reward.util.NetUtil;
import com.innova.reward.view.MyToast;
import com.innova.reward.view.WaitDialog;

/**
 * 采用Activity+Fragment的开发模式，此时Activity相当于一个调度装配器，实际的逻辑处理都在Fragment里
 * 
 */
public class AtyMain extends FragmentActivity implements OnClickListener {

	private final static int INDEX_IN = 0, INDEX_MANAGER = 1, INDEX_SET = 2, LOAD_LAYOUT = 3, WAIT_DIALOG_CLOSE = 4,
			TOAST = 5;
	private LinearLayout mLlIn, mLlManager, mLlSet;
	private ImageView mIvIn, mIvManager, mIvSet;

	private InFrag mFgtIn;
	private RewardQueryFrag mFgtManager;
	private PersonalSetFrag mFgtSet;

	private Dialog mQueryDialog;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_LAYOUT:
				afterQueryReward();
				break;
			case WAIT_DIALOG_CLOSE:
				mQueryDialog.dismiss();
				break;
			case TOAST:
				MyToast.toast("网络未开启或被禁用", 0);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_main);
		init();
	}

	private void init() {
		// 处理没有新发票时，不查询
		if (CommonUtil.sp.getBoolean(Constant.IS_HAVE_NEW_FP_RECORD, false)) {
			mQueryDialog = WaitDialog.createLoadingDialog(this, "兑奖...");
			mQueryDialog.show();
			new Thread() {
				public void run() {
					if (NetUtil.IsHaveInternet() && CommonUtil.sp.getBoolean(Constant.IS_TO_REWARD, true)) {
						updateFpInfo();
					} else {
						mHandler.sendEmptyMessage(TOAST);
					}
					mHandler.sendEmptyMessage(LOAD_LAYOUT);
					mHandler.sendEmptyMessage(WAIT_DIALOG_CLOSE);
				}
			}.start();
		} else {
			mHandler.sendEmptyMessage(LOAD_LAYOUT);
		}
		overridePendingTransition(R.anim.anim_slide, R.anim.anim_slide);
	}

	/**
	 * 从数据库中查出所有可以兑奖的发票，上网兑奖
	 */
	private void updateFpInfo() {
		SQLiteDatabase db = DBUtil.getDb(this, true);
		Cursor cursor = db.rawQuery("select fpdm,fphm from fp_info where bz in('请等待')", null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				updateReward(cursor.getString(cursor.getColumnIndex("fpdm")),
						cursor.getString(cursor.getColumnIndex("fphm")));
			}
			cursor.close();
		}
		db.close();
		CommonUtil.sp.edit().putBoolean(Constant.IS_HAVE_NEW_FP_RECORD, false).commit();
	}

	/**
	 * 上网查询中奖情况
	 * 
	 * @param fpdm
	 * @param fphm
	 */
	private void updateReward(String fpdm, String fphm) {
		ContentValues values = new ContentValues();
		for (;;) {
			String queryResult = NetUtil.getQueryResult(fpdm, fphm);
			if (queryResult.contains(Constant.RES_MONEY)) {
				String[] split = queryResult.split("\\]|\\[|\\{|\\}|'|,|:");
				CommonUtil.log(split[25]);
				values.put("zjlx", split[25].replace("\"", ""));
				values.put("bz", split[42].replace("\"", ""));
				CommonUtil.log("中奖");
				break;
			} else if (queryResult.contains(Constant.RES_FP_IN_NO_MONEY)) {
				values.put("bz", "谢谢参与");
				break;
			} else if (queryResult.contains(Constant.RES_YZM_WRONG)) {
				CommonUtil.log("验证码错误");
				continue;
			} else if (queryResult.contains(Constant.RES_FP_NOT_IN_POOL)) {
				values.put("bz", "请等待");
				break;
			}
		}
		DBUtil.updateData(values, fpdm, fphm);
	}

	private void afterQueryReward() {
		initViews();
		initEvents();
		setSelect(INDEX_IN);
	}

	protected void initEvents() {
		mLlManager.setOnClickListener(this);
		mLlSet.setOnClickListener(this);
		mLlIn.setOnClickListener(this);
	}

	protected void initViews() {
		mLlIn = (LinearLayout) findViewById(R.id.ll_in);
		mLlManager = (LinearLayout) findViewById(R.id.ll_mnager);
		mLlSet = (LinearLayout) findViewById(R.id.ll_settings);

		mIvManager = (ImageView) findViewById(R.id.iv_manager);
		mIvIn = (ImageView) findViewById(R.id.iv_in);
		mIvSet = (ImageView) findViewById(R.id.iv_settings);

		mFgtSet = new PersonalSetFrag();
		mFgtIn = new InFrag();
		mFgtManager = new RewardQueryFrag();
		// 加到布局里
		getSupportFragmentManager().beginTransaction().add(R.id.fl_content, mFgtIn).add(R.id.fl_content, mFgtManager)
				.add(R.id.fl_content, mFgtSet).commit();
	}

	/**
	 * 设置选哪个tab的操作
	 * 
	 * @param i
	 *            第几个tab
	 */
	private void setSelect(int i) {
		FragmentManager sfm = getSupportFragmentManager();
		FragmentTransaction bt = sfm.beginTransaction();
		hideFragment(bt);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i) {
		case INDEX_IN:
			InFrag.mIsOpenDistinguish = true;
			mIvIn.setImageResource(R.drawable.ic_in_pressed);
			bt.show(mFgtIn);
			break;
		case INDEX_MANAGER:
			InFrag.mIsOpenDistinguish = false;
			mIvManager.setImageResource(R.drawable.ic_reward_query_pressed);
			bt.show(mFgtManager);
			break;
		case INDEX_SET:
			InFrag.mIsOpenDistinguish = false;
			mIvSet.setImageResource(R.drawable.ic_set_pressed);
			bt.show(mFgtSet);
		}
		bt.commit();
	}

	/**
	 * 隐藏所有Fragment
	 * 
	 * @param bt
	 */
	private void hideFragment(FragmentTransaction bt) {
		bt.hide(mFgtSet).hide(mFgtManager).hide(mFgtIn);
	}

	@Override
	public void onClick(View v) {
		resetImg();
		switch (v.getId()) {
		case R.id.ll_in:
			setSelect(INDEX_IN);
			break;
		case R.id.ll_settings:
			setSelect(INDEX_SET);
			break;
		case R.id.ll_mnager:
			setSelect(INDEX_MANAGER);
		}
	}

	/**
	 * 切换图片至暗色
	 */
	private void resetImg() {
		mIvManager.setImageResource(R.drawable.ic_manager_normal);
		mIvIn.setImageResource(R.drawable.ic_in_normal);
		mIvSet.setImageResource(R.drawable.ic_set_normal);
	}
}
