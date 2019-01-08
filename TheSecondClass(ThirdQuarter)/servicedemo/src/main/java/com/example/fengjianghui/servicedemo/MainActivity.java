package com.example.fengjianghui.servicedemo;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    Intent intent;
    Intent intent1;
    MyBindService service;//接收serviceConnection回传来的数据
    //ServiceConnection用来实现客户端到service的绑定
    ServiceConnection conn=new ServiceConnection() {
        @Override//当启动源跟service成功连接之后
        public void onServiceConnected(ComponentName name, IBinder binder) {
            service=((MyBindService.MyBinder)binder).getService();
        }

        @Override//当启动源和service连接以外丢失，例如当service崩溃了或者被强行杀死了，如果是解除绑定就不调用
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void doClick(View view){
        switch (view.getId()){
            case R.id.startservice:
                intent=new Intent(MainActivity.this,MyStartService.class);
                startService(intent);
                break;
            case R.id.stop:
                stopService(intent);
                break;
            case R.id.bind:
                intent1 = new Intent(MainActivity.this,MyBindService.class);
//                //第二个参数是connection
//                //第三个参数的作用是为了成功创建bindService
//                bindService(intent1,conn, Service.BIND_AUTO_CREATE);
                //如果把startService和bindService一起写。。：要重写activity的onDestroy方法
                intent=new Intent(MainActivity.this,MyStartService.class);
                startService(intent);
                bindService(intent1,conn, Service.BIND_AUTO_CREATE);
                break;
            case R.id.play:
                service.Play();
                break;
            case R.id.pause:
                service.Pause();
                break;
            case R.id.next:
                service.Next();
                break;
            case R.id.previous:
                service.Previous();
                break;
            case R.id.unbinds:
                unbindService(conn);
                break;
        }
    }

    //为保险起见每次退出程序就将service停掉
    @Override
    protected void onDestroy() {
        stopService(intent);
        unbindService(conn);
        super.onDestroy();
    }
}

