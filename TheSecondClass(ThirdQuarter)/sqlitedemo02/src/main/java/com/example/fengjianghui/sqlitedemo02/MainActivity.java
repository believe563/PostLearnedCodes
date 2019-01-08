package com.example.fengjianghui.sqlitedemo02;

import android.app.Activity;
import android.content.ContentValues;
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

    public static final String TABLENAME="stutb";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建数据库
        SQLiteDatabase db=openOrCreateDatabase("stu.db",MODE_PRIVATE,null);
        //创建一张表,拼表名的时候要注意不要忘记exists后面加空格
        db.execSQL("create table if not exists "+TABLENAME+"(_id integer primary key autoincrement,name text not null,sex text not null,age integer not null)");
        //insert方法中第一个参数最好是写一个public static final String将它存起来再写到参数里

        ContentValues values = new ContentValues();//非常类似于键值对
        values.put("name","张三");
        values.put("sex","男");
        values.put("age", "19");
        //insert返回值long类型的值是该行的id
        /**
         * insert的第三个参数：
         * nullColumnHack：当values参数为空或者里面没有内容的时候，
         * 我们insert是会失败的（底层数据库不允许插入一个空行），为了防止这种情况，
         * 我们要在这里指定一个列名，到时候如果发现将要插入的行为空行时，
         * 就会将你指定的这个列名的值设为null，然后再向数据库中插入。
         *
         * 如果我们不添加nullColumnHack的话，
         * 那么我们的sql语句最终的结果将会类似insert into tableName()values();
         * 这显然是不允许的。
         * 而如果我们添加上nullColumnHack呢，sql将会变成这样，
         * insert into tableName (nullColumnHack)values(null);
         * 这样很显然就是可以的。
         */
        long rowId=db.insert(TABLENAME,null,values);
        //接着可以再次创建一个values，然后再次执行存值操作，或者可以把它原先的值清掉，再次存值
        values.clear();
        values.put("name","张三丰");
        values.put("sex", "男");
        values.put("age", "99");
        db.insert(TABLENAME, null, values);
        values.clear();
        values.put("name","张三疯");
        values.put("sex", "男");
        values.put("age", "59");
        db.insert(TABLENAME, null, values);
        values.clear();
        values.put("name","张三峰");
        values.put("sex", "男");
        values.put("age", "39");
        db.insert(TABLENAME, null, values);
        values.clear();
        values.put("name","张三封");
        values.put("sex", "男");
        values.put("age", "29");
        db.insert(TABLENAME, null, values);//5条数据
        //把id为3以上的全部改成女
        values.clear();
        //更新时直接将values键值对传到update方法里
        values.put("sex", "女");
        db.update(TABLENAME, values, "_id>?", new String[]{"3"});
        db.delete(TABLENAME, "name like ?", new String[]{"%丰"});//删除名字中所有带有丰字的人
        Cursor c=db.query(TABLENAME, null, "_id>?", new String[]{"0"}, null, null, "age");
        if (c!=null){
            //获得所有的列名
            String[] columns=c.getColumnNames();
            while (c.moveToNext()){
                for (String columnName:columns) {
                    Log.i("info", c.getString(c.getColumnIndex(columnName)));
                }
            }
            c.close();
        }
        db.close();
    }
}
