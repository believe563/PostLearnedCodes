package com.example.fengjianghui.filedemo1;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity{

    private EditText editText;
    private Button btWrite;
    private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        btWrite = (Button) findViewById(R.id.write);
        tvContent = (TextView) findViewById(R.id.contentvalue);
    }
    //保存文件内容
    public void WriteFiles(String content) {
        //开启流来往文件中写
        FileOutputStream fos=null;
        try {
            //默认的把文件写到data/data/<包名>下的file文件夹中
            //如果写到内存卡中则要加权限
            //如果文件不存在，可以帮默认地创建
            fos=openFileOutput("a.txt", MODE_PRIVATE);//权限可以是一种，也可以是多种，用+号连接
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //读取文件内容
    public String readFiles(){
        //定义一个字符串，作为要写出去的数据
        String content=null;
        try {
            //指定读的路径
            FileInputStream fis=openFileInput("a.txt");
            //每次读取一个字节，同时将它转成byteArray
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //每次去读1024个字节
            byte[] buffer = new byte[1024];
            //定义一个长度
            int len=0;
            while((len=fis.read(buffer))!=-1) {
                //每次都写一个buffer，从0读到len，把整个数据写出去
                baos.write(buffer,0,len);
            }
            content=baos.toString();
            fis.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public void onBtClick(View v){
        String content=editText.getText().toString();
        WriteFiles(content);
        String content1=readFiles();
        tvContent.setText(content1);
    }
}
