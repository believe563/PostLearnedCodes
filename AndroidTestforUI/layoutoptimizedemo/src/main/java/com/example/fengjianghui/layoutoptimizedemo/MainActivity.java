package com.example.fengjianghui.layoutoptimizedemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button button;
    private ViewStub viewStub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewStub= (ViewStub) findViewById(R.id.viewStub);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //惰性加载去加载布局
                viewStub.inflate();
            }
        });
    }
}
