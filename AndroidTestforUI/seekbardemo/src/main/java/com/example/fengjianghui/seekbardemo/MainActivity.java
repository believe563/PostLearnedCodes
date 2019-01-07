package com.example.fengjianghui.seekbardemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener{

    private SeekBar seekBar;
    //显示当前的状态，开始拖动还是停止拖动还是正在拖动
    private TextView tv1;
    //显示当前数值
    private TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar= (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        tv1= (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
    }

    //数值改变，正在拖动
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tv1.setText("正在拖动");
        tv2.setText("当前数值："+progress);

    }

    //开始拖动
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        tv1.setText("开始拖动");
    }

    //停止拖动
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        tv1.setText("停止拖动");
    }
}
