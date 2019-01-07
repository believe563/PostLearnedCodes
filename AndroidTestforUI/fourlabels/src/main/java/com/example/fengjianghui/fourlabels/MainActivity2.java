package com.example.fengjianghui.fourlabels;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity2 extends Activity {
;
    private TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        textView1= (TextView) findViewById(R.id.textView1);
    }
}
