package com.innova.reward.util;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.innova.reward.data.Constant;

public class DBUtil extends SQLiteOpenHelper {
	public DBUtil(Context c, String name, CursorFactory factory, int version) {
		super(c, name, factory, version);
	}

	public DBUtil(Context c, String name) {
		super(c, name, null, 1);
	}

	// 首次创建数据库的时候调用，一般可以把建库、建表的操作放在这里面
	public void onCreate(SQLiteDatabase db) {
		/**
		 * 发票代码，发票号码,中奖类型，备注
		 */
		db.execSQL("create table if not exists " + Constant.TABLE_NAME_FP
				+ " (fpdm text,fphm text,zjlx text,bz text default '请等待',primary key(fpdm,fphm))");
	}

	// 当数据库的版本发生变化的时候，会自动执行
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * @param c
	 *            上下文
	 * @param isRead
	 *            写/读
	 * @return 只读的teaCourse数据库指针
	 */
	public static SQLiteDatabase getDb(Context c, boolean isRead) {
		DBUtil helper = new DBUtil(c, Constant.DB_NAME);
		return isRead ? helper.getReadableDatabase() : helper.getWritableDatabase();
	}

	/**
	 * 删除teaCourse中的所有记录
	 * 
	 * @param c
	 */
	public static void deleteData(Context c, String bz) {
		SQLiteDatabase db = DBUtil.getDb(c, false);
		db.delete(Constant.TABLE_NAME_FP, "bz like ?", new String[] { bz });
		db.close();
	}

	/**
	 * 向数据库中添加数据
	 * 
	 * @param c
	 *            上下文对象
	 * @param tableName
	 *            表名
	 * @param list
	 *            ContentValues的集合
	 */
	public static void addData(Context c, List<ContentValues> list) {
		SQLiteDatabase db = DBUtil.getDb(c, false);

		for (ContentValues v : list) {
			db.insert(Constant.TABLE_NAME_FP, null, v);
		}
		db.close();
	}

	public static void updateData(ContentValues values, String... args) {
		SQLiteDatabase db = DBUtil.getDb(MyAppli.getContext(), false);
		db.update(Constant.TABLE_NAME_FP, values, "fpdm=? and fphm=?", args);
		db.close();
	}
}
