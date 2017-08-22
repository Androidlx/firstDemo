package com.example.lixin.lixin1506a20170821.database;
/**
 * 创建数据库
 * 姓名 李鑫
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hua on 2017/8/21.
 */

public class Sqlite extends SQLiteOpenHelper {
    public Sqlite(Context context) {
        super(context, "zhoukao3.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table zhoukao(name varchar(20),url varchar (20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
