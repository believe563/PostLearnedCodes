package com.example.fengjianghui.sharedpreferencedemo01;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText etUserName;
    private EditText etPwd;
    private CheckBox cbSaveName;
    //在onClick语句中调用了两个button的id，不用实例化
//    private Button btLogin;
//    private Button btCancel;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //生成一个默认的preference对象，名字是当前包名中默认的一个xml文件
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

//        //自定义包名及文件的权限,运行完之后就可以生成这样的一个文件
//        SharedPreferences pref1 = getSharedPreferences("myPref",MODE_PRIVATE );
//
//        //得到一个编辑器对象
//        SharedPreferences.Editor editor=pref1.edit();
//        //往里面存值
//        editor.putString("name","张三");
//        editor.putInt("age", 30);
//        editor.putLong("time", System.currentTimeMillis());
//        editor.putBoolean("default", true);
//        editor.commit();
//        editor.remove("default");
//        //每次操作完之后必须commit才生效
//        editor.commit();
//
//        //取值，第一个参数为键，第二个参数为默认值，如果没取到则为这个默认值
//        System.out.println(pref1.getString("name","李四"));
//        System.out.println(pref1.getInt("age",0));
        init();
        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        //启用编辑
        editor = preferences.edit();
        String name=preferences.getString("userName","");
        if (name==null){
            cbSaveName.setChecked(false);
        }else{
            etUserName.setText(name);
            cbSaveName.setChecked(true);
        }
    }

    public void init() {
        etUserName = (EditText) findViewById(R.id.editUserName);
        etPwd = (EditText) findViewById(R.id.editPwd);
        cbSaveName = (CheckBox) findViewById(R.id.cbSaveName);
//        btLogin = (Button) findViewById(R.id.btLogin);
//        btCancel = (Button) findViewById(R.id.btCancel);
    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.btLogin:
                String userName = etUserName.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                if ("admin".equals(userName) && "123456".equals(pwd)) {
                    if (cbSaveName.isChecked()) {
                        editor.putString("userName", userName);
                        editor.commit();
                    }else {
                        editor.remove("userName");
                        editor.commit();
                    }
                    Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"禁止登录",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
