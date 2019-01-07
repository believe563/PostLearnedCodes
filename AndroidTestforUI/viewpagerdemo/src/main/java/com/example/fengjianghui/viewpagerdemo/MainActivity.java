package com.example.fengjianghui.viewpagerdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener{

    private ViewPager pager;
    //首先创建了四个线性布局
    //PagerAdapter的
    private List<View> viewList;
    private List<String> titleList;
    private PagerTabStrip pagerTabStrip;
    private PagerTitleStrip pagerTitleStrip;
    //FragmentPagerAdapter的
    private List<android.support.v4.app.Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pagerTabStrip= (PagerTabStrip) findViewById(R.id.tab);
        pagerTitleStrip= (PagerTitleStrip) findViewById(R.id.title);
        //然后将四个布局转换成view对象存储在list中
        viewList=new ArrayList<View>();
        //通过view对象作为viewpager的数据源，要用PagerAdapter适配器
        View view1=View.inflate(this,R.layout.view1,null);
        View view2=View.inflate(this,R.layout.view2,null);
        View view3=View.inflate(this,R.layout.view3,null);
        View view4=View.inflate(this,R.layout.view4,null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        /**
         * 通过fragment作为viewPager的数据源
         * 用fragmentAdapter
         */
        fragmentList=new ArrayList<android.support.v4.app.Fragment>();
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());
        fragmentList.add(new Fragment4());

        //为viewPager页卡设置标题
        titleList=new ArrayList<String>();
        titleList.add("第一页");
        titleList.add("第二页");
        titleList.add("第三页");
        titleList.add("第四页");

        //为pagerTabStrip设置一些属性
        pagerTabStrip.setBackgroundColor(Color.YELLOW);
        pagerTabStrip.setTextColor(Color.RED);
        pagerTabStrip.setDrawFullUnderline(false);//如果为true，就回有一条黑线在tabStrip的下面
        pagerTabStrip.setTabIndicatorColor(Color.GREEN);
        //创建PagerAdapter的适配器
        MyPagerAdapter myPagerAdapter=new MyPagerAdapter(viewList,titleList);

        //初始化viewPager
        pager= (ViewPager) findViewById(R.id.pager);

//        //viewPager加载适配器，用其它适配器之前先注释掉它
//        pager.setAdapter(myPagerAdapter);

//        //第一个参数fragmentManager要用新方法写，用getsupportFragmentManager
//        //这是为了配合supportV4包
//        //上面应该换成继承FragmentActivity
//        MyFragmentPagerAdapter myFragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
//        pager.setAdapter(myFragmentPagerAdapter);

        MyFragmentStatePagerAdapter myFragmentStatePagerAdapter=new MyFragmentStatePagerAdapter(getSupportFragmentManager(),fragmentList,titleList);
        pager.setAdapter(myFragmentStatePagerAdapter);

        pager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Toast.makeText(MainActivity.this,"当前是第"+(position+1)+"个页面",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
