package com.example.lixin.lixin1506a20170821.database;
/**
 * 进行数据库里的一些操作
 * 姓名 李鑫
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lixin.lixin1506a20170821.bean.MenuInfo;

import java.util.ArrayList;

/**
 * Created by hua on 2017/8/21.
 */

public class DbUtils  {

    private final SQLiteDatabase db;

    public DbUtils(Context context){
        Sqlite sqlite = new Sqlite(context);
        db = sqlite.getWritableDatabase();
    }
    public void add(String name,String url){

        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("url",url);
        db.insert("zhoukao",null,values);
    }
    public ArrayList<MenuInfo.ResultBean.DataBean> findAll(){
        ArrayList<MenuInfo.ResultBean.DataBean> list = null;
        MenuInfo.ResultBean.DataBean bean = null;
        Cursor cursor = db.query(false, "zhoukao", null, null, null, null, null, null, null);
        list = new ArrayList<>();
        while (cursor.moveToNext()){
             bean = new MenuInfo.ResultBean.DataBean();
            ArrayList<String> strings = new ArrayList<>();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            strings.add(url);
            bean.setTitle(name);
            bean.setAlbums(strings);
            list.add(bean);
        }
        return list;
    }
}
