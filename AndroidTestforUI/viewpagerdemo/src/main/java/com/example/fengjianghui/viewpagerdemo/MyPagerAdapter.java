package com.example.fengjianghui.viewpagerdemo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 有自己动态的构建选项卡，销毁选项卡
 * 实例化和销毁view对象是想让适配器管理好自己的view对象，默认可以同时加载3个
 * Created by fengjianghui on 2015/9/25.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<View> viewList;
    private List<String> titleList;
    public MyPagerAdapter(List<View> viewList,List<String> titleList){
         this.viewList=viewList;
        this.titleList=titleList;
    }

    /**
     *
     * @return 页卡的数量
     */
    @Override
    public int getCount() {
        return viewList.size();
    }

    /**
     *
     * @param view
     * @param object
     * @return
     * view是否来自于对象
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 实例化一个页卡,手动加入的
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    /**
     * 销毁一个页卡，手动加入的
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    /**
     * 设置viewPager页卡的标题，必须在布局的viewPager中加入pagerTabStrip标签这个方法才管用
     * @param position
     * @return
     * 手动加入的
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
