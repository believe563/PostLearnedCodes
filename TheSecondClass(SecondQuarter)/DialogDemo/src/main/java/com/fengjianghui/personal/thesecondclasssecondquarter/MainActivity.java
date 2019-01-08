package com.fengjianghui.personal.thesecondclasssecondquarter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {


    private String[] single_str = {"男", "女", "程序员", "女博士"};
    private String[] multi_str = {"跑步", "乒乓球", "网球", "游泳", "瑜伽"};
    private String[] item_list = {"项目经理", "策划", "测试", "美工", "程序员"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initEvent();
    }

    //初始化点击事件
    public void initEvent() {

        //确认对话框
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });
        //单选对话框
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog2();
            }
        });
        //多选对话框
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog3();
            }
        });
        //列表对话框
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog4();
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog5();
            }
        });

    }

    /**
     * 新技能get
     */
//    public void func(Object... objects)
//    {
//
//    }

    /**
     * 显示确认对话框
     */
    public void showDialog1() {
//        List list=null;
//        func();
//        func(1,3,"dfadf",7.9,0f,list,null,3);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("部门列表");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("确认对话框的提示内容");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "确认对话框显示成功", Toast.LENGTH_SHORT).show();
            }
        });
        //获取dialog
        AlertDialog dialog = builder.create();
        //显示dialog
        dialog.show();

    }

    /**
     * 单选对话框
     */
    public void showDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("性别分类");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setSingleChoiceItems(single_str, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "性别为：" + single_str[which], Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 多选对话框
     */
    public void showDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("兴趣爱好");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMultiChoiceItems(multi_str, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "我的兴趣包括：" + multi_str[which], Toast.LENGTH_SHORT).show();
                }
            }
        });
        //设置取消按钮，点击的时候多选对话框隐藏
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 列表对话框
     */
    private void showDialog4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("列表对话框");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(item_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "我的工作是：" + item_list[which], Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 自定义对话框
     */
    private void showdialog5() {
        //取到自定义的view
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("自定义对话框");
        builder.setIcon(R.mipmap.ic_launcher);
        //设置自定义的view
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
