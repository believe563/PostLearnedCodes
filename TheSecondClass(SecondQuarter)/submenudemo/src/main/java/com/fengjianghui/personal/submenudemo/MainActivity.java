package com.fengjianghui.personal.submenudemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

/**
 * 在有标题栏的情况下显示在标题栏的设置按钮中，
 * 在没有标题栏的情况下点击menu菜单可以显示
 * 可以在manifest文件中设置theme样式显示或隐藏标题栏
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //通过xml静态添加子菜单
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

//        //动态添加子菜单
//        SubMenu file = menu.addSubMenu("文件");
//        SubMenu edit = menu.addSubMenu("编辑");
//        //给子菜单添加菜单项
//        file.add(1, 1, 1, "新建");
//        file.add(1, 2, 1, "打开");
//        file.add(1, 3, 1, "保存");
//        file.setHeaderTitle("文件操作");
//        file.setHeaderIcon(R.mipmap.ic_launcher);
//        edit.add(2, 1, 1, "复制");
//        edit.add(2, 2, 1, "粘贴");
//        edit.add(2, 3, 1, "剪切");
//        edit.setHeaderTitle("编辑操作");
//        edit.setHeaderIcon(R.mipmap.ic_launcher);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        //判断当前是哪个groupId
//        if (item.getGroupId() == 1) {
//            switch (item.getItemId()) {
//                case 1:
//                    Toast.makeText(MainActivity.this, "点击了新建", Toast.LENGTH_SHORT).show();
//                    break;
//                case 2:
//                    Toast.makeText(MainActivity.this, "点击了打开", Toast.LENGTH_SHORT).show();
//                    break;
//                case 3:
//                    Toast.makeText(MainActivity.this, "点击了保存", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        } else if (item.getGroupId() == 2) {
//            switch (item.getItemId()) {
//                case 1:
//                    Toast.makeText(MainActivity.this, "点击了复制", Toast.LENGTH_SHORT).show();
//                    break;
//                case 2:
//                    Toast.makeText(MainActivity.this, "点击了粘贴", Toast.LENGTH_SHORT).show();
//                    break;
//                case 3:
//                    Toast.makeText(MainActivity.this, "点击了剪切", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }

        switch (item.getItemId()) {
                case R.id.new_file:
                    Toast.makeText(MainActivity.this, "点击了新建", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.new_open:
                    Toast.makeText(MainActivity.this, "点击了打开", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.new_save:
                    Toast.makeText(MainActivity.this, "点击了保存", Toast.LENGTH_SHORT).show();
                    break;
            case R.id.new_copy:
                    Toast.makeText(MainActivity.this, "点击了复制", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.new_paste:
                    Toast.makeText(MainActivity.this, "点击了粘贴", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.new_cut:
                    Toast.makeText(MainActivity.this, "点击了剪切", Toast.LENGTH_SHORT).show();
                    break;
            }
        return super.onOptionsItemSelected(item);
    }
}
