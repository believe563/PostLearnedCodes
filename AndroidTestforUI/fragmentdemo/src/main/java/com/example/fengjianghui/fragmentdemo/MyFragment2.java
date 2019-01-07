package com.example.fengjianghui.fragmentdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fengjianghui on 2015/9/20.
 */
public class MyFragment2 extends Fragment{
    private TextView text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * resource:Fragment需要加载的布局文件
         * root:加载layout的父ViewGroup
         * attactToRoot:false，不反回父viewGroup，如果是true的话就返回，但是上个参数已经返回了，就写false
         */
        View view=inflater.inflate(R.layout.fragment, container,false);
        text= (TextView) view.findViewById(R.id.text);
        text.setText("动态加载Fragment");
        return view;
    }
}

