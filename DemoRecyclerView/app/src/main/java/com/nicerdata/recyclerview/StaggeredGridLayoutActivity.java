package com.nicerdata.recyclerview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class StaggeredGridLayoutActivity extends ActionBarActivity {

    //声明
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private StaggeredAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        initDatas();
        initViews();

        mAdapter = new StaggeredAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);

        //设置RecyclerView的布局管理
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                mAdapter.deleteData(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                mAdapter.deleteData(position);
            }
        });

    }

    private void initViews() {

        mRecyclerView = (RecyclerView) this.findViewById(R.id.id_recyclerView);
    }

    private void initDatas() {

        //赋值
        mDatas = new ArrayList<String>();

        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }
}
