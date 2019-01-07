package com.four.voicerecord.httpclientdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText et_name;
    private EditText et_age;
    private Button bt_commit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        bt_commit = (Button) findViewById(R.id.bt_commit);
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //远程服务器
//                String url="http://115.159.113.225:8080/GetAndPostDemo/servlet/MyServlet";
                //本地
                String url="http://192.168.3.103:8080/GetAndPostDemo/servlet/MyServlet";
//                new HttpClientThread(url+"?name="+et_name.getText().toString()+"&age="+et_age.getText().toString()).start();
                new HttpClientThread(url, et_name.getText().toString(), et_age.getText().toString()).start();
            }
        });
    }

}
