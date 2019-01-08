package com.innova.reward.frag;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innova.reward.R;
import com.innova.reward.data.Constant;
import com.viewpagerindicator.TabPageIndicator;

public class PersonalSetFrag extends BaseFrag {
	private ViewPager mVpContent;
	private TabPageIndicator mTpIndicator;

	private FragmentPagerAdapter mFpaAdapter;
	private List<Fragment> mFList;

	public View onCreateView(LayoutInflater infl, ViewGroup vg, Bundle bundle) {
		View v = infl.inflate(R.layout.lyt_set, null);

		initViews(v);

		return v;
	}

	@Override
	protected void initViews(View v) {
		mVpContent = (ViewPager) v.findViewById(R.id.vp_content);

		mFList = new ArrayList<Fragment>();

		mFList.add(new MsgFrag());
		mFList.add(new DataManagerFrag());
		mFList.add(new PersonalDataFrag());

		mFpaAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

			@Override
			public Fragment getItem(int pos) {
				// 应该是一个list，每次返回list.get(pos);
				return mFList.get(pos);
			}

			@Override
			public int getCount() {
				return Constant.TITLES.length;
			}

			@Override
			public CharSequence getPageTitle(int pos) {
				return Constant.TITLES[pos];
			}
		};
		mTpIndicator = (TabPageIndicator) v.findViewById(R.id.tpi_indicator);

		mVpContent.setAdapter(mFpaAdapter);
		mTpIndicator.setViewPager(mVpContent, 1);
	}

	@Override
	protected void initEvents() {
	}
}
