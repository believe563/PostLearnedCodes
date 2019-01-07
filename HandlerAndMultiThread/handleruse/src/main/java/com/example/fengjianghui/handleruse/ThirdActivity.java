package com.example.fengjianghui.handleruse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;


/**
 * Created by fengjianghui on 2015/11/4.
 * -->handleMessage（子线程的handlemessage）
 */
public class ThirdActivity extends Activity {
    private  HandlerThread handlerThread;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handlerThread = new HandlerThread("handler thread");
        handlerThread.start();
        handler=new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                System.out.println("currentThread:"+Thread.currentThread());
                super.handleMessage(msg);
            }
        };
        handler.sendEmptyMessage(1);
    }
}
