package com.fengjianghui.personal.optionsmenudemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //在xml静态添加
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        // 用代码动态添加
        MenuItem item1=menu.add(1, 100, 1, "菜单一");
        item1.setTitle("第一个菜单");//修改标题
        item1.setIcon(R.mipmap.ic_launcher);//api>=11时不显示图标
        menu.add(1, 101, 1, "菜单二");
        menu.add(1, 102, 1, "菜单三");
        menu.add(1, 103, 1, "菜单四");
        menu.add(1, 104, 1, "菜单五");
        menu.add(1, 105, 1, "菜单六");
        menu.add(1, 106, 1, "菜单七");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//menuItem是点击了得菜单项
        switch (item.getItemId()) {
//            case R.id.action_menu_item1:
//                Toast.makeText(MainActivity.this, "点击了action_menu_item1", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.action_menu_item2:
//                Toast.makeText(MainActivity.this, "点击了action_menu_item2", Toast.LENGTH_SHORT).show();
//                break;
            case 100:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                item.setIntent(intent);
                Toast.makeText(MainActivity.this, "点击了菜单一", Toast.LENGTH_SHORT).show();
                break;
            case 101:
                Toast.makeText(MainActivity.this, "点击了菜单二", Toast.LENGTH_SHORT).show();
                break;
            case 102:
                Toast.makeText(MainActivity.this, "点击了菜单三", Toast.LENGTH_SHORT).show();
                break;
            case 103:
                Toast.makeText(MainActivity.this, "点击了菜单四", Toast.LENGTH_SHORT).show();
                break;
            case 104:
                Toast.makeText(MainActivity.this, "点击了菜单五", Toast.LENGTH_SHORT).show();
                break;
            case 105:
                Toast.makeText(MainActivity.this, "点击了菜单六", Toast.LENGTH_SHORT).show();
                break;
            case 106:
                Toast.makeText(MainActivity.this, "点击了菜单七", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
