package com.four.voicerecord.getandpostdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterActivity extends Activity {

    private EditText name_et;
    private EditText age_et;
    private Button commit_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        name_et = (EditText) findViewById(R.id.name_et);
        age_et = (EditText) findViewById(R.id.age_et);
        commit_bt = (Button) findViewById(R.id.commit_button);
        commit_bt.setOnClickListener(new View.OnClickListener() {
            //远程服务器115.159.113.225
            @Override
            public void onClick(View v) {
//                String url="http://115.159.113.225:8080/GetAndPostDemo/servlet/MyServlet";
                String url="http://172.42.202.131:8080/GetAndPostDemo/servlet/MyServlet";
                //调用start方法！！！
                    new HttpThread(url, name_et.getText().toString(), age_et.getText().toString()).start();
            }
        });
    }
}
