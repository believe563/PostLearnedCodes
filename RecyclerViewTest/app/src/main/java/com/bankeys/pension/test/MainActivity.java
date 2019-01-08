package com.bankeys.pension.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMainClicListener(View v) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        switch (v.getId()) {
            case R.id.tv1:
                intent.putExtra("activity_type", 1);
                break;
            case R.id.tv2:
                intent.putExtra("activity_type", 2);
                break;
            case R.id.tv3:
                intent.putExtra("activity_type", 3);
                break;
        }
        startActivity(intent);
    }
}
