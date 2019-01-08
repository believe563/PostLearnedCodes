package com.tuisongfeng.push.pushdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
/*
*用激光推送网站完成的推送demo
* */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);//初始化之后就可以接收推送了
        //为手机上的当前运行软件设置别名
        JPushInterface.setAlias(this, "fengjianghui", new TagAliasCallback() {//第三个参数是回调
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i("Tag", "log result tag is" + i);//打印返回值，如果是0则设置成功
            }
        });


        //按标签推送消息
        Set<String> sets = new HashSet<>();
        sets.add("swim");
        sets.add("jogging");
        JPushInterface.setTags(this, sets, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i("Tag", "the tag result is " + i);
            }
        });
    }
}
