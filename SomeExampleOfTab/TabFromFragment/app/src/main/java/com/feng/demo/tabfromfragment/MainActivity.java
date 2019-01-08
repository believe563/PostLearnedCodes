package com.feng.demo.tabfromfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    //三个tab内容布局
    private LinearLayout mTabSearch;
    private LinearLayout mTabAdd;
    private LinearLayout mTabSet;

    //三个imagebutton
    private ImageButton mIbSearch;
    private ImageButton mIbAdd;
    private ImageButton mIbSet;

    private Fragment searchFragment;
    private Fragment addFragment;
    private Fragment setFragment;


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
    }

    private void initView() {
        mTabSearch = (LinearLayout) findViewById(R.id.ll_tab_search);
        mTabAdd = (LinearLayout) findViewById(R.id.ll_tab_add);
        mTabSet = (LinearLayout) findViewById(R.id.ll_tab_set);

        mIbSearch = (ImageButton) findViewById(R.id.ib_search);
        mIbAdd = (ImageButton) findViewById(R.id.ib_add);
        mIbSet = (ImageButton) findViewById(R.id.ib_set);

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
        //设置内容区域
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //先把所有的fragment隐藏一下
        hideFragment(ft);
        switch (i) {
            case 0:
                mIbSearch.setImageResource(R.mipmap.search2);
                if (searchFragment == null) {
                    searchFragment=new SearchFragment();
                    ft.add(R.id.fl_content, searchFragment);
                }
                ft.show(searchFragment);
                break;
            case 1:
                mIbAdd.setImageResource(R.mipmap.add2);
                if (addFragment == null) {
                    addFragment=new AddFragment();
                    ft.add(R.id.fl_content, addFragment);
                }
                ft.show(addFragment);
                break;
            case 2:
                mIbSet.setImageResource(R.mipmap.set2);
                if (setFragment == null) {
                    setFragment=new SetFragment();
                    ft.add(R.id.fl_content, setFragment);
                }
                ft.show(setFragment);
                break;
        }
        //把事务提交*
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        if (searchFragment != null) {
            ft.hide(searchFragment);
        }
        if (addFragment != null) {
            ft.hide(addFragment);
        }
        if (setFragment != null) {
            ft.hide(setFragment);
        }
    }

    private void resetImages() {
        mIbSearch.setImageResource(R.mipmap.search1);
        mIbAdd.setImageResource(R.mipmap.add1);
        mIbSet.setImageResource(R.mipmap.set1);
    }
}
