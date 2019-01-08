package com.fengjianghui.personal.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by fengjianghui on 2016-01-14.
 */
public class MyserviceActivity1 extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myservice2);
        Button bt_appeal = (Button) findViewById(R.id.bt_myservice2_appeal);
        bt_appeal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_myservice2_appeal:
                Intent intent1 = new Intent(MyserviceActivity1.this, AddAppealReasonActivity.class);
                startActivity(intent1);
        }
    }
}
