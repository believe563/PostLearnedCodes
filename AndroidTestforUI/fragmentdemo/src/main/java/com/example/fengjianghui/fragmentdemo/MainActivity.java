package com.example.fengjianghui.fragmentdemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.first:
                Intent intent=new Intent(this,MainActivity2.class);
                startActivity(intent);
                break;
            case R.id.second:
                Fragment myFragment2=new MyFragment2();
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
                beginTransaction.add(R.id.frame, myFragment2);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.third:
                Intent intent1=new Intent(MainActivity.this,MainActivity3.class);
                startActivity(intent1);
                break;
            case R.id.fourth:
                Intent intent2=new Intent(MainActivity.this,MainActivity4.class);
                startActivity(intent2);
                break;
        }
    }
}
