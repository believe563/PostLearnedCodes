package com.nicerdata.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author IsayesHu
 * Date 2015/6/19.
 */
public class StaggeredAdapter extends SimpleAdapter {

    private List<Integer> mHeights;

    //构造方法
    public StaggeredAdapter(Context context, List<String> datas) {
        super(context, datas);
        //成员变量进行赋值

        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 300));
        }
    }

    /*
    绑定ViewHolder的数据
     */
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int pos) {
        ViewGroup.LayoutParams lp = myViewHolder.itemView.getLayoutParams();
        lp.height = mHeights.get(pos);
        myViewHolder.itemView.setLayoutParams(lp);
        myViewHolder.tv.setText(mDatas.get(pos));

        setUpitemEvent(myViewHolder);
    }
}


