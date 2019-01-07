package com.example.fengjianghui.fragmentdemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fengjianghui on 2015/9/21.
 */
public class MyFragment3 extends Fragment {

    /**
     * 测试周期的顺序
     * 启动Fragment-->屏幕锁屏-->屏幕解锁-->切换到其他的Fragment中
     * -->回到桌面-->回到应用-->退出Fragment
     */
    /**
     * 当fragment被添加到Activity的时候会回调这个方法，并且只调用一次
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Main", "MyFragment3-----onAttach()");
    }

    /**
     * 创建Fragment时会回调这个方法
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Main", "MyFragment3-----onCreate()");
    }
    /**
     * 每次创建Fragment都会绘制fragment的View组件，这个时候回调该方法
     * 相当于给Fragment绑定一个布局，并返回一个view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment2,container,false);
        TextView textView2= (TextView) view.findViewById(R.id.textView2);
        textView2.setText("第三个Fragment");
        Log.i("Main", "MyFragment3-----onCreateView()");
        return view;
    }
    /**
     * 当Fragment所在的Activity启动完成后调用
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Main", "MyFragment3-----onActivityCreated()");
    }

    /**
     * 启动Fragment
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.i("Main", "MyFragment3-----onStart()");
    }

    /**
     * 恢复fragment时被回调，调用onStart（）方法后面一定会调用onResume方法
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.i("Main", "MyFragment3-----onResume()");
    }

    /**
     * 暂停Fragment
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.i("Main", "MyFragment3-----onPause()");
    }

    /**
     * 停止fragment
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.i("Main", "MyFragment3-----onStop()");
    }

    /**
     * 销毁Fragment所包含的View组件时调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Main", "MyFragment3-----onDestroyView()");
    }

    /**
     * 销毁Fragment时被回调
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Main", "MyFragment3-----onDestroy()");
    }

    /**
     * Fragment从Activity中删除时会回调，并且只回调一次
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Main", "MyFragment3-----onDetach()");
    }
}
