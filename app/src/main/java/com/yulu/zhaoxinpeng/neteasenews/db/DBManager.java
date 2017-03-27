package com.yulu.zhaoxinpeng.neteasenews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yulu.zhaoxinpeng.neteasenews.bean.NewsInfor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.R.attr.name;
import static android.R.id.list;

/**
 * Created by Administrator on 2017/3/17.
 */

public class DBManager {
    private static DBManager dbManager;
    private static DBHelper dbHelper;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public static DBManager getInstance(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }
        return dbManager;
    }
    //把网络数据写入数据库中
    public static void insert(NewsInfor newsInfor){
        //获取SQLiteDataBase对象
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("summary",newsInfor.getSummary());
        contentValues.put("icon",newsInfor.getIcon());
        contentValues.put("stamp",newsInfor.getStamp());
        contentValues.put("title",newsInfor.getTitle());
        contentValues.put("nid",newsInfor.getNid());
        contentValues.put("link",newsInfor.getLink());
        contentValues.put("type",newsInfor.getType());


        writableDatabase.insert("news",null,contentValues);
    }
    //获取数据库表中的信息 ，最后返回list集合
    public LinkedList<NewsInfor> query(){
        LinkedList<NewsInfor> list = new LinkedList<NewsInfor>();
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        String s="select * from news";
        Cursor cursor = readableDatabase.rawQuery(s, null);
        if (cursor.moveToFirst()) {
            do {
                String summary = cursor.getString(cursor.getColumnIndex("summary"));
                String icon = cursor.getString(cursor.getColumnIndex("icon"));
                String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int nid = cursor.getInt(cursor.getColumnIndex("nid"));
                String link = cursor.getString(cursor.getColumnIndex("link"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));

                NewsInfor newsInfor = new NewsInfor(summary, icon, stamp, title, nid, link, type);
                list.addFirst(newsInfor);
            }while (cursor.moveToNext());
        }
        return list;
    }

    //获取数据库表中的信息 ，最后返回list集合
    public LinkedList<NewsInfor> queryCollection(){
        LinkedList<NewsInfor> list = new LinkedList<NewsInfor>();
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        String s="select * from collections";
        Cursor cursor = readableDatabase.rawQuery(s, null);
        if (cursor.moveToFirst()) {
            do {
                String summary = cursor.getString(cursor.getColumnIndex("summary"));
                String icon = cursor.getString(cursor.getColumnIndex("icon"));
                String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int nid = cursor.getInt(cursor.getColumnIndex("nid"));
                String link = cursor.getString(cursor.getColumnIndex("link"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));

                NewsInfor newsInfor = new NewsInfor(summary, icon, stamp, title, nid, link, type);
                list.add(newsInfor);
            }while (cursor.moveToNext());
        }
        return list;
    }

    //获取数据库文件中是否有值，据此判断是否需要加载网络
    public long getCount(){
        SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
        String s="select count(*)from news";
        Cursor cursor = readableDatabase.rawQuery(s, null);
        if (cursor.moveToFirst()) {
            long aLong = cursor.getLong(0);
            return aLong;
        }
        return 0;
    }

    //插入一条数据
    public long insertone(NewsInfor newsInfor){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("summary",newsInfor.getSummary());
        contentValues.put("icon",newsInfor.getIcon());
        contentValues.put("stamp",newsInfor.getStamp());
        contentValues.put("title",newsInfor.getTitle());
        contentValues.put("nid",newsInfor.getNid());
        contentValues.put("link",newsInfor.getLink());
        contentValues.put("type",newsInfor.getType());

        return writableDatabase.insert("collections",null,contentValues);
    }

    //删除一条数据
    public void delete(NewsInfor newsInfor){
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        String[] strings={String.valueOf(newsInfor.getNid())};
        writableDatabase.delete("collections","nid=?",strings);
    }

    //判断 收藏 表中是否已经存在此数据
    public boolean find(String title) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from collections where title=?",
                new String[] { title });
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }

    //判断 收藏 表中是否已经存在此数据
    public static boolean findIn(String title) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from news where title=?",
                new String[] { title });
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }
}
