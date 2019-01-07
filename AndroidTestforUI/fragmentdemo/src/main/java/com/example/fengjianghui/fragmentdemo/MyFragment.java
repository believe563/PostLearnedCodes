package com.example.fengjianghui.fragmentdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by fengjianghui on 2015/9/20.
 */
public class MyFragment extends Fragment{
    private String aaa;


    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }
    private TextView text;
    private Button button;
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
        button= (Button) view.findViewById(R.id.button);
        text.setText("获取内容");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=getAaa();
                Toast.makeText(getActivity(),"value="+value,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

