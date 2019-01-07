package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


import com.example.myapplication.wheel_widget.OnWheelChangedListener;
import com.example.myapplication.wheel_widget.StringWheelAdapter;
import com.example.myapplication.wheel_widget.WheelView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/28.
 * 滑动的选择器--当前界面底部弹出
 */
public class HeightPicker extends LinearLayout {

    private int mAge ;
    private ArrayList<String> mList_Height;
    private WheelView mWheelView;
    private OnChangeListener onChangeListener; //onChangeListener
    public HeightPicker(Context context) {
        super(context);
        init(context);
    }

    public HeightPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        mList_Height = new ArrayList<String>();
        for (int i = 160; i < 250; i++) {
            mList_Height.add(i + " cm");
        }
        for (int i = 1; i < 160; i++) {
            mList_Height.add(i + " cm");
        }
        mWheelView = new WheelView(context);
        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mWheelView.setLayoutParams(param);
        mWheelView.setAdapter(new StringWheelAdapter(mList_Height, mList_Height.size()));
        mWheelView.setVisibleItems(3);
        mWheelView.setCyclic(true);
        mWheelView.addChangingListener(onHeightChangedListener);
        addView(mWheelView);
    }
    /**
     * 滑动改变监听器
     */
    private OnWheelChangedListener onHeightChangedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            change(mList_Height.get(newValue));
        }
    };
        /**
     * 滑动改变监听器回调的接口
     */
    public interface OnChangeListener {
        void onChange(String height);
    }

    /**
     * 设置滑动改变监听器
     * @param onChangeListener
     */
    public void setOnChangeListener(OnChangeListener onChangeListener){
        this.onChangeListener = onChangeListener;
    }

    /**
     * 滑动最终调用的方法
     */
    private void change(String str){
        if(onChangeListener!=null){
            onChangeListener.onChange(str);
        }
    }
}
