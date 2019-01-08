package com.example.fengjianghui.sqliteopenhelper;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new一个DBOpenHelper对象
        DBOpenHelper helper=new DBOpenHelper(MainActivity.this,"stu.db");
        //通过DBOpenHelper获取一个数据库的对象
        helper.getReadableDatabase();//获取一个只能查询不能写入 不能更新的数据库
        SQLiteDatabase db=helper.getWritableDatabase();//可读可写的数据库
        //接下来就是跟数据库的访问一样的操作

        //举例：
        Cursor c=db.rawQuery("select * from stutb", null);
        if (c!=null){
            String[] columnNames=c.getColumnNames();
            while(c.moveToNext()){
                for (String columnName:columnNames) {
                    Log.i("info",c.getString(c.getColumnIndex(columnName)));
                }
            }
        }
    }
}
