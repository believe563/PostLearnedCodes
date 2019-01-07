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
public class RoomTypeHeightPicker extends LinearLayout {

    private int mAge ;
    private ArrayList<String> mList_Height;
    private WheelView mWheelView;
    private OnChangeListener onChangeListener; //onChangeListener
    public RoomTypeHeightPicker(Context context) {
        super(context);
        init(context);
    }

    public RoomTypeHeightPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        mList_Height = new ArrayList<String>();
        String[] strRoomType = new String[]{"公寓","别墅","民宿","四合院","旅馆","农家院","客栈"};
        for (int i = 0; i < 7; i++) {
            mList_Height.add(strRoomType[i]);
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
