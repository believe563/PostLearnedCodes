package com.feng.demo.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feng.demo.aidldemo.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mEtNum1;
    private EditText mEtNum2;
    private EditText mEtRes;

    private Button mBtnAdd;

    IMyAidlInterface iMyAidlInterface;
    private ServiceConnection conn=new ServiceConnection() {

        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("MainActivity","connect service start");
            //拿到了远程的服务
            iMyAidlInterface=IMyAidlInterface.Stub.asInterface(service);//返回的是一个IMyAidlInterface
        }

        //服务断开的时候
        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface=null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //软件一启动就绑定服务
        bangdingService();
    }

    private void init() {
        mEtNum1 = (EditText) findViewById(R.id.et_num1);
        mEtNum2 = (EditText) findViewById(R.id.et_num2);
        mEtRes = (EditText) findViewById(R.id.et_res);
        mBtnAdd = (Button) findViewById(R.id.bt_add);
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                int num1=Integer.parseInt(mEtNum1.getText().toString());
                int num2=Integer.parseInt(mEtNum2.getText().toString());
                try {
                    //调用远程的服务
                    int res=iMyAidlInterface.add(num1, num2);
                    mEtRes.setText(res+"");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void bangdingService() {
        //获取到服务端
        Intent intent = new Intent();//android5.0之后已经不允许通过隐释放式来获取服务
        //所以这里必须采用明文标识指定到哪个服务
        //而是要必须 显式Intent启动 绑定服务
        //第一个参数是包名，要写死，不能采用隐式的com.feng.demo.aidldemo
        //包名要与服务器端一致，就是aidl接口的那个包名
        //第二个参数是类名，包名+类名
        intent.setComponent(new ComponentName("com.feng.demo.aidldemo", "com.feng.demo.aidldemo.IRemoteService"));
        //绑定service
        //第三个参数是一个flag，参数的意思是只要客户端启动服务，服务器端就自动绑定

        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑定服务
        unbindService(conn);
    }
}
