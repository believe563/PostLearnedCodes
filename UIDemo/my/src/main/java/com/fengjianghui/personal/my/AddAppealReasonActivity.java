package com.fengjianghui.personal.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by fengjianghui on 2016-01-14.
 */
public class AddAppealReasonActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addappealreason);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_submit);
        ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_submit:
                Intent intent1 = new Intent(AddAppealReasonActivity.this, MyAppealActivity.class);
                startActivity(intent1);
        }
    }
}
