package com.fengjianghui.personal.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by fengjianghui on 2016-01-14.
 */
public class MyServiceActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myservice);
        LinearLayout ll1_myservice = (LinearLayout) findViewById(R.id.ll1_myservice);
        ll1_myservice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll1_myservice:
                Intent intent1 = new Intent(MyServiceActivity.this, MyserviceActivity1.class);
                startActivity(intent1);
        }
    }
}
