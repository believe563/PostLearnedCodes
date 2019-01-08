package com.example.fengjianghui.gestureoverlayviewdemo;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private GestureOverlayView gestureOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //首先将从gesturebuilder生成的手势文件放到res文件夹的raw文件夹中
        //如果放在assets文件夹中的话将不能被R文件索引
        gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestureOverlayView);
//        1、找到刚才预设定的手势文件
//        2、加载那个手势文件中的所有手势
//        3、匹配、识别
        //得到一个手势库对象
        // 从资源中将手势库文件加载进来
        final GestureLibrary library = GestureLibraries.fromRawResource(MainActivity.this, R.raw.gestures);
        library.load();
        //给gestureOverlayView加监听
        gestureOverlayView.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override//两个参数
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                //读出手势库中的内容，识别手势
                //识别手势，并用手势的预测集合将识别出来的手势存放
                ArrayList<Prediction> mygesture = library.recognize(gesture);
                Prediction prediction = mygesture.get(0);
                //prediction有一个相似度的值score可以判断所画的手势与原先定义的手势的相似度
                if (prediction.score >5) {
                    //根据手势的名字name去判断画的是哪个手势
                    if (prediction.name.equals("exit")) {
                        //activity结束
                        finish();
                    } else if (prediction.name.equals("next")) {
                        Toast.makeText(MainActivity.this, "进行下一个动作", Toast.LENGTH_SHORT).show();
                    } else if (prediction.name.equals("previous")) {
                        Toast.makeText(MainActivity.this, "进行上一个动作", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "没有该手势", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
