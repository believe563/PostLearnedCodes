package com.feng.demo.viewpagerindicatorfortab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class TabAdapter extends FragmentPagerAdapter {
    public static String[] TITLES = {"课程", "问答", "求课", "学习", "计划"};
//    private Fragment searchFrag;

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        SearchFragment searchFrag=new SearchFragment(position);
        return searchFrag;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];//设置indicator显示的文字的，很重要！！！
    }
}
