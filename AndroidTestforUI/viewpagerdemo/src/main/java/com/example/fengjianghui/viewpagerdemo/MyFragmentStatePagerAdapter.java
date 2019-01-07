package com.example.fengjianghui.viewpagerdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * fragmentStatePagerAdapter会把所有页面全部加载进来
 * FragmentStatePagerAdapter有创建和销毁的过程
 * Created by fengjianghui on 2015/9/25.
 */
public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    //数据源集合
    private List<Fragment> fragmentList;
    //标题
    private List<String> titleList;
    public MyFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    /**
     * 手动加入的方法
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    /**
     * 手动加入的方法
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
