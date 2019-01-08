package com.imooc.tab04;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter
{

	public static String[] TITLES = new String[]
	{ "课程", "问答", "求课", "学习", "计划" };

	public TabAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0)
	{
		TabFragment fragment = new TabFragment(arg0);
		return fragment;
	}

	@Override
	public int getCount()
	{
		return TITLES.length;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return TITLES[position];
	}

}
