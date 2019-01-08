package com.example.fengjianghui.sqliteopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fengjianghui on 2015/10/14.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context,String name){
        super(context,name,null,1);
    }
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//后两个直接写死，不再赋值
    }

    @Override//首次创建数据库时调用，一般可以把建库、建表的操作在这里执行
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists stutb(_id integer primary key increment,name text not null,sex text not null,age integer not null)");
        db.execSQL("insert into stutb(name,sex,age) values('张三','女',18)");
    }

    @Override//数据库的版本发生变化的时候自动执行
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
