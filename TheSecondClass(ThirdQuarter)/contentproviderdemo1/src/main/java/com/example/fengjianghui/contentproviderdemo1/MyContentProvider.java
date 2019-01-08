package com.example.fengjianghui.contentproviderdemo1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by fengjianghui on 2015/10/15.
 * 这里面的方法可以实现增删改查等
 * 比如实现了增和删，别人要是访问数据库的话，将只能去实现增和删的功能
 * 第三方的能力全部取决于contentProvider的功能
 */
public class MyContentProvider extends ContentProvider {
    @Override//contentProvider创建之后调用
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override//根据uri查询出selection指定的条件所匹配的全部记录，并且可以指定查询哪些列，以什么方式(order)排序
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override//返回当前uri的MIME类型，如果该uri对应的数据可能包括多条记录，
    //那么MIME类型字符串 就是以vnd.android.dir/开头
    //如果该uri对应的数据只有一条记录，该MIME类型字符串就是以vnd.android.cursor.item/开头
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override//根据uri插入values所对应的数据
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override//根据Uri(统一资源标识符)删除selection指定的条件所匹配的全部记录
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override//根据uri修改selection指定的条件所匹配的全部记录
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
