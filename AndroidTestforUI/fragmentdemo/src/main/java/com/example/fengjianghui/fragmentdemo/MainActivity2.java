package com.example.fengjianghui.fragmentdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by fengjianghui on 2015/9/19.
 * 静态加载所用到的类
 */
public class MainActivity2 extends Activity {
    private Button button;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        button= (Button) findViewById(R.id.button);
        button.setText("改变");
        text= (TextView)findViewById(R.id.text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("改变");
            }
        });
    }
}
