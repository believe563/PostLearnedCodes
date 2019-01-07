package com.example.bmobpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import cn.bmob.push.PushConstants;

/**
 * Created by Administrator on 2016/4/30 0030.
 * //客户端收到服务器推送的信息的操作
 */
public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "";
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {//获得的action是pushsdk那得到action的话
            //获取intent里的bundle中传递的数据
            String msg = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Toast.makeText(context, "客户端收到的推送内容:" + msg, Toast.LENGTH_SHORT).show();
            JSONTokener jsonTokener = new JSONTokener(msg);
            try {
                //获取jsonobject对象
                JSONObject object = (JSONObject) jsonTokener.nextValue();
                message = object.getString("alert");//通过key找到value
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //把消息推送到notification中
            //拿到notification的系统服务，并把它强转为notificationmanager
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher).setTicker("BmobPushNotification").setContentTitle("title").setContentText(message).build();
            manager.notify(R.mipmap.ic_launcher, notification);
        }
    }
}
