package com.yulu.zhaoxinpeng.neteasenews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/3/17.
 */

/**
 * 创建数据库、表
 */
public class DBHelper extends SQLiteOpenHelper {


    //创建数据库文件
    public DBHelper(Context context) {
        super(context, "neteasenews.db", null, 1);
    }

    //创建数据库表
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表，放入从网络获取的数据
        String s_news = "CREATE TABLE news(_id INTEGER PRIMARY KEY AUTOINCREMENT,summary Text , icon Text, stamp Text, title Text, nid INTEGER, link Text, type INTEGER)";
        db.execSQL(s_news);
        //创建表，放入要收藏的新闻
        String s_collections = "CREATE TABLE collections(_id INTEGER PRIMARY KEY AUTOINCREMENT,summary Text , icon Text, stamp Text, title Text, nid INTEGER, link Text, type INTEGER)";
        db.execSQL(s_collections);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
