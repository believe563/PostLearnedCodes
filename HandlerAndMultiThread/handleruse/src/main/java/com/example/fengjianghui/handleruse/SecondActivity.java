package com.example.fengjianghui.handleruse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by fengjianghui on 2015/11/3.
 * 自定义子线程
 */
public class SecondActivity extends Activity {

    private MyThread thread;

    private Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            System.out.println("UI线程中handler的执行:"+Thread.currentThread());
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("hello thread");
        setContentView(textView);

        //在子线程中处理消息
        //实例化子线程
        thread=new MyThread();
        //线程启动
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //子线程中的发送消息
        thread.handler.sendEmptyMessage(1);

        //主线程中的发送消息
        handler1.sendEmptyMessage(1);
    }
    private class MyThread extends Thread{
        public Handler handler;
        @Override
         public void run() {
            //创建handler对象时，要在当前线程中创建一个Looper
            Looper.prepare();
            //创建handler，拿到looper
            handler=new Handler(){
              //handleMessage方法
                @Override
                public void handleMessage(Message msg) {
                    //打印当前线程
                    System.out.println("子线程中的handler的执行:"+Thread.currentThread());
                    super.handleMessage(msg);
                }
            };
            //循环处理消息
            Looper.loop();
            //这样handler就与子线程进行关联了
            super.run();
        }
    }
}
