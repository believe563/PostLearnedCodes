package com.fengjianghui.personal.contextmenudemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showListView();


    }

    /**
     * 设置listView显示的内容
     */
    public void showListView() {
        ListView listView = (ListView) findViewById(R.id.listView);
        //给listView设置显示内容
        //第二个参数是子布局，第三个参数是数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData());
        listView.setAdapter(adapter);
        //首先给listView注册上下文菜单
        this.registerForContextMenu(listView);
    }


    //重写onCreateContextMenu方法
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //设置menu显示的内容
        menu.setHeaderTitle("文件操作");
        menu.setHeaderIcon(R.mipmap.ic_launcher);
        //动态添加
//        menu.add(1, 1, 1, "复制");
//        menu.add(1, 2, 1, "粘贴");
//        menu.add(1, 3, 1, "剪切");
//        menu.add(1, 4, 1, "重命名");
        //静态添加
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
    }


    //重写onContextItemSelected()方法
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case 1:
//                Toast.makeText(MainActivity.this, "复制操作", Toast.LENGTH_SHORT).show();
//                break;
//            case 2:
//                Toast.makeText(MainActivity.this, "粘贴操作", Toast.LENGTH_SHORT).show();
//                break;
//            case 3:
//                Toast.makeText(MainActivity.this, "剪切操作", Toast.LENGTH_SHORT).show();
//                break;
//            case 4:
//                Toast.makeText(MainActivity.this, "重命名操作", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.menu_item1:
                Toast.makeText(MainActivity.this, "复制操作", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item2:
                Toast.makeText(MainActivity.this, "粘贴操作", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item3:
                Toast.makeText(MainActivity.this, "剪切操作", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_item4:
                Toast.makeText(MainActivity.this, "重命名操作", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 构造listView显示的数据
     * @return
     */
    private ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i=0;i<5;i++) {
            list.add("文件" + (i + 1));
        }
        return list;
    }

}
