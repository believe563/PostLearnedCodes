package com.feng.demo.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by Administrator on 2016/4/20.
 * 这样就实现了服务器端的共享
 */
public class IRemoteService extends Service {

    /*
    * 客户端绑定到该服务时会执行
    * */
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;//客户端绑定到该服务时，就会得到自定义的这个服务
        //这样就完成了服务器端信息的共享
    }

    //写一个这个，直接new一个接口类，调用静态方法stub，实现自己定义的方法
    //这个相当于是在服务端返回service的实现
    private IBinder iBinder = new IMyAidlInterface.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.d("TAG", "收到了远程的请求,输入的参数是" + num1 + "和" + num2);
            return num1 + num2;
        }
    };

}
