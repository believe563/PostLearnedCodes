package com.example.fengjianghui.viewpagerdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * fragmentPagetAdapter会把所有页面全部加载进来
 * viewPager里fragment比较少的时候可以用它
 *
 * FragmentPagerAdapter没有创建和销毁页卡的过程
 * Created by fengjianghui on 2015/9/25.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    //数据源集合
    private List<Fragment> fragmentList;
    //标题
    private List<String> titleList;
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> titleList) {
        super(fm);
        this.fragmentList=fragmentList;
        this.titleList=titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    /**
     * 后来加入的方法
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
