package com.example.fengjianghui.fragmentdemo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by fengjianghui on 2015/9/23.
 * activity与fragment之间的通信
 */

public class MainActivity4 extends Activity implements MyFragment5.MyListener{
    //上面实现myFragment5中定义的接口是为了接收从fragment传来的数据
    private EditText editText;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main4);
        editText= (EditText) findViewById(R.id.editText);
        send= (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editText.getText().toString();
                MyFragment5 myFragment5=new MyFragment5();
                //把数据打包到fragment中
                Bundle bundle=new Bundle();
                bundle.putString("name",text);
                myFragment5.setArguments(bundle);
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
                beginTransaction.add(R.id.layout,myFragment5,"fragment5");
                beginTransaction.commit();
                Toast.makeText(MainActivity4.this,"向Fragment发送数据"+text,Toast.LENGTH_SHORT).show();
            }
        });
        FragmentManager fragmentManager=getFragmentManager();
        Fragment findFregmentById= fragmentManager.findFragmentById(R.id.fragment);//静态加载过来的myfragment对象
        //需要获得fragment中的方法  所以需要做类型转换
        MyFragment frag= (MyFragment) findFregmentById;
        frag.setAaa("fragment静态传值");
    }

    @Override
    public void thank(String code) {
        Toast.makeText(this,"已成功接收到"+code,Toast.LENGTH_SHORT).show();
    }
}
