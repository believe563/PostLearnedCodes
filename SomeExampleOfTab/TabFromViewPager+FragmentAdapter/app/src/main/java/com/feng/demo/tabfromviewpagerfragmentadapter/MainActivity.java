package com.feng.demo.tabfromviewpagerfragmentadapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    //三个tab内容布局
    private LinearLayout mTabSearch;
    private LinearLayout mTabAdd;
    private LinearLayout mTabSet;

    //三个imagebutton
    private ImageButton mIbSearch;
    private ImageButton mIbAdd;
    private ImageButton mIbSet;

    private Fragment mSearchFragment;
    private Fragment mAddFragment;
    private Fragment mSetFragment;

    private ViewPager mViewPager;

    private List<Fragment> fragments;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        viewEvent();
        //初始化时默认显示第一个fragment
        setSelect(0);
    }

    private void viewEvent() {
        mTabSearch.setOnClickListener(this);
        mTabAdd.setOnClickListener(this);
        mTabSet.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                向左或向右滑动时设置tab上的图片的选中和未选中的效果
                resetImages();
                setSelectedTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mTabSearch = (LinearLayout) findViewById(R.id.ll_tab_search);
        mTabAdd = (LinearLayout) findViewById(R.id.ll_tab_add);
        mTabSet = (LinearLayout) findViewById(R.id.ll_tab_set);

        mIbSearch = (ImageButton) findViewById(R.id.ib_search);
        mIbAdd = (ImageButton) findViewById(R.id.ib_add);
        mIbSet = (ImageButton) findViewById(R.id.ib_set);

        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mSearchFragment = new SearchFragment();
        mAddFragment = new AddFragment();
        mSetFragment = new SetFragment();
        fragments = new ArrayList<>();
        fragments.add(mSearchFragment);
        fragments.add(mAddFragment);
        fragments.add(mSetFragment);
        //new FragmentPagerAdapter的时候要传一个参数 fragment管理器
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);
    }

    @Override
    public void onClick(View v) {

        //先重置图标成未选中状态
        resetImages();

        switch (v.getId()) {
            case R.id.ll_tab_search:
                setSelect(0);
                break;
            case R.id.ll_tab_add:
                setSelect(1);
                break;
            case R.id.ll_tab_set:
                setSelect(2);
                break;
        }
    }

    //设置选中的tab对应的fragment的状态
    private void setSelect(int i) {
        //把图片设置为亮的

        setSelectedTabs(i);
        //设置内容区域
        mViewPager.setCurrentItem(i);
    }

    private void setSelectedTabs(int i) {
        switch (i) {
            case 0:
                mIbSearch.setImageResource(R.mipmap.search2);
                break;
            case 1:
                mIbAdd.setImageResource(R.mipmap.add2);
                break;
            case 2:
                mIbSet.setImageResource(R.mipmap.set2);
                break;
        }
    }


    private void resetImages() {
        mIbSearch.setImageResource(R.mipmap.search1);
        mIbAdd.setImageResource(R.mipmap.add1);
        mIbSet.setImageResource(R.mipmap.set1);
    }
}
