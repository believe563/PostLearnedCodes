package com.innova.reward.frag;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.innova.reward.util.CommonUtil;

/**
 * fragment的基本，封装一些最基础的共同方法
 * 
 * @author guo
 * 
 */
public abstract class BaseFrag extends Fragment implements OnClickListener {

	@Override
	public void onClick(View v) {
	}

	protected void myStartActivity(Class<?> c) {
		startActivity(new Intent(getActivity(), c));
	}
	
	/**
	 * SharedPreferences 成批产出变量
	 * 
	 * @param vars
	 *            v1,v2,v3...
	 */
	protected void removeVars(String... vars) {
		Editor e = CommonUtil.sp.edit();
		for (String var : vars) {
			e.remove(var);
		}
		e.commit();
	}
	
	protected abstract void initViews(View v);
	protected abstract void initEvents();
}
