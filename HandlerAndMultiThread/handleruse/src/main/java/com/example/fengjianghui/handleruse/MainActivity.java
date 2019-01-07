package com.example.fengjianghui.handleruse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView textView;
    private Button button;
    private Button button2;
//    //    private Handler handler=new Handler();
//    //发送消息时，handler应写为：
//    private Handler handler = new Handler() {
//        @Override//接收到发送过来的消息,参数msg就是发过来的消息
//        public void handleMessage(Message msg) {
//            //在子线程中给文本页面更新一下UI信息
//            textView.setText(""+msg.arg1+","+msg.arg2+msg.obj);//arg1是int型
//            super.handleMessage(msg);
//        }
//    };

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "" + 1, Toast.LENGTH_SHORT).show();
            return false;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "" + 2, Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };
    private ImageView imageView;

//    class Person{
//        public int age;
//        public String name;
//        public String toString(){
//            return "name="+name+",age="+age;
//        }
//    }


    //        //三张图片的id
//    private int images[] = {R.mipmap.cute, R.mipmap.g, R.mipmap.jason};
//    //建立一个索引,指定图片的当前位置
//    private int index;
//    //创建一个Runnable对象
//    MyRunnable myRunnable=new MyRunnable();
//
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
//            handler.removeCallbacks(myRunnable);
                break;
            case R.id.button2:
                handler.sendEmptyMessage(1);
        }
    }

//    //创建一个Runnable
//    class MyRunnable implements Runnable{
//        @Override
//        public void run() {
//            index++;
//            index=index%3;
//            imageView.setImageResource(images[index]);
//            //将runnable对象加到messagequeue中，在规定的1秒后这个runnable就会在线程中执行
//            handler.postDelayed(myRunnable, 1000);
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在非Ui线程更新textview的文本信息
        textView = (TextView) findViewById(R.id.textview);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(this);


//        //发送一个消息
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                    //创建一个message,自定义message
////                    Message message=new Message();
//                    Message message=handler.obtainMessage();
//                    //给message指定一个arg1参数
//                    message.arg1=88;
//                    message.arg2=100;
//                    Person person=new Person();
//                    person.age=23;
//                    person.name="fengjianghui";
//                    message.obj=person;
////                    handler.sendMessage(message);
//                    message.sendToTarget();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                super.run();
//            }
//        }.start();//一定要记得调用start方法

//        //要先加一个runnable进来让它执行
//        handler.postDelayed(myRunnable, 1000);

//        //创建一个线程
//        new Thread(){
//            @Override
//            public void run() {
//                //让当前线程sleep1秒钟，（它会抛出异常）
//                try {
//                    Thread.sleep(1000);
//                    //用handler更新UI
//                    handler.post(new Runnable() {//在runnable中就可以执行UI操作了
//                        @Override
//                        public void run() {//这个方法是在UI线程中执行的
//                            textView.setText("update thread");
//                        }
//                    });
//                    //textView.setText("update thread");//在非Ui线程中更新UI，会报错
//                    // error:android.view.ViewRootImpl$CalledFromWrongThreadException:
//                    // Only the original thread that created a view hierarchy can touch its views.
//                    //所以要在上面用handler更新UI
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                super.run();
//            }
//        }.start();//不调用start方法是没法调用run方法的
    }
}
