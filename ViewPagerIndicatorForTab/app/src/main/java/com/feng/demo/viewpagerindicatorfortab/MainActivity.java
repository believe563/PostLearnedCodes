package com.feng.demo.viewpagerindicatorfortab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.viewpagerindicator.TabPageIndicator;

import java.util.List;

public class MainActivity extends FragmentActivity{

    private ViewPager mViewPager;
    private TabPageIndicator mTabPageIndicator;
    private TabAdapter mTabAdapter;
    private List<Fragment> mFragments;//一定要记得继承的是fragmentactivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mTabPageIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
        mTabPageIndicator.setViewPager(mViewPager,0);//可以设置下面那条线的颜色、样式等属性
    }


}
