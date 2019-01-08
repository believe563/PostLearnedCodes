package com.feng.demo.tabfromviewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    private LinearLayout mTabSearch;
    private LinearLayout mTabAdd;
    private LinearLayout mTabSet;
    private ImageButton mIbSearch;
    private ImageButton mIbAdd;
    private ImageButton mIbSet;

    private PagerAdapter mPagerAdapter;

    private List<View> tabsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        addEvent();
    }

    private void addEvent() {
        /*给每个tab设置监听*/
        mTabSearch.setOnClickListener(this);
        mTabAdd.setOnClickListener(this);
        mTabSet.setOnClickListener(this);
        /*给viewPager设置滑动监听*/
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //将图标先重置
                resetImage();
                int currentItem=mViewPager.getCurrentItem();
                switch (currentItem) {
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

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_menu);
        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        View view1=li.inflate(R.layout.tab01_viewpager, null);
        View view2=li.inflate(R.layout.tab02_viewpager, null);
        View view3=li.inflate(R.layout.tab03_viewpager, null);
        tabsList.add(view1);
        tabsList.add(view2);
        tabsList.add(view3);

        mTabSearch = (LinearLayout) findViewById(R.id.lp_tab_search);
        mTabAdd = (LinearLayout) findViewById(R.id.lp_tab_add);
        mTabSet = (LinearLayout) findViewById(R.id.lp_tab_set);

        mIbSearch = (ImageButton) findViewById(R.id.ib_search);
        mIbAdd = (ImageButton) findViewById(R.id.ib_add);
        mIbSet = (ImageButton) findViewById(R.id.ib_set);

        mPagerAdapter=new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = tabsList.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(tabsList.get(position));
            }

            @Override
            public int getCount() {
                return tabsList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        };
        mViewPager.setAdapter(mPagerAdapter);

    }

    @Override
    public void onClick(View v) {
        resetImage();
        switch (v.getId()) {
            case R.id.lp_tab_search:
                mViewPager.setCurrentItem(0);
                mIbSearch.setImageResource(R.mipmap.search2);
                break;
            case R.id.lp_tab_add:
                mViewPager.setCurrentItem(1);
                mIbAdd.setImageResource(R.mipmap.add2);
                break;
            case R.id.lp_tab_set:
                mViewPager.setCurrentItem(2);
                mIbSet.setImageResource(R.mipmap.set2);
                break;
        }
    }

    /*将所有图标切换为暗色*/
    private void resetImage() {
        mIbSearch.setImageResource(R.mipmap.search1);
        mIbAdd.setImageResource(R.mipmap.add1);
        mIbSet.setImageResource(R.mipmap.set1);
    }
}
