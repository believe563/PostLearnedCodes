package com.example.fengjianghui.handleruse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;

/**
 * Created by fengjianghui on 2015/11/4.
 * 主线程与子线程之间相互传递信息
 */
public class FourthActivity extends Activity implements View.OnClickListener{
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            System.out.println("主线程里的handle");
            Message message=new Message();
            threadHandler.sendMessageDelayed(message, 1000);
            super.handleMessage(msg);
        }
    };
    private Button send;
    private Button stop;
    private HandlerThread handlerthread;
    private Handler threadHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_layout);
        send = (Button) findViewById(R.id.send);
        stop = (Button) findViewById(R.id.stop);
        send.setOnClickListener(this);
        stop.setOnClickListener(this);
        handlerthread=new HandlerThread("handlerthread");
        handlerthread.start();
        threadHandler=new Handler(handlerthread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                System.out.println("子线程里的handler");
                Message message=new Message();
                handler.sendMessageDelayed(message,1000);
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
                handler.sendEmptyMessage(1);
                break;
            case R.id.stop:
                handler.removeMessages(1);
                break;
        }
    }
}
