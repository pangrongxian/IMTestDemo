package com.zyys.zyysdemo.adb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zyys.zyysdemo.bean.Account;
import com.zyys.zyysdemo.bean.Msg;

import java.util.ArrayList;

/**
 * url text,name text,voipaccount text
 */
public class MySQLiteHelper {//用来管理数据库的类

    private MySQLiteManager manager;

    public MySQLiteHelper(Context context) {
        //创建管理数据库的类的对象
        manager = new MySQLiteManager(context);
    }


    //插入数据的方法
    public void insert(String table, Account account) {
        //添加数据
        ContentValues values = new ContentValues();
        values.put("voipaccount", account.getVoipaccount());

        //调用自定义的MySQLiteManager（数据库管理者）对象来调用getReadableDatabase（）方法打开数据库
        SQLiteDatabase db = manager.getWritableDatabase();
        //插入数据
        long p = db.insert(table, null, values);
        if (p > 0) {
            Log.i("db", "添加数据库成功！");
        } else {
            Log.i("db", "添加数据库失败！");
        }
        db.close();
    }


    //插入history数据的方法
    public void insertHistoryInfo(String table,String voipAccount, String history,Integer type) {
        //添加数据
        ContentValues values = new ContentValues();
        values.put("voipaccount", voipAccount);//字段1:账户id
        values.put("historyList", history);//字段2收到的消息
        values.put("type", type);//字段3类型

        //调用自定义的MySQLiteManager（数据库管理者）对象来调用getReadableDatabase（）方法打开数据库
        SQLiteDatabase db = manager.getWritableDatabase();
        //插入数据
        long p = db.insert(table, null, values);
        if (p > 0) {
            Log.i("db", "history:添加数据库成功！");
        } else {
            Log.i("db", "history:添加数据库失败！");
        }
        db.close();
    }

    // 查询所有历史消息数据
    public ArrayList<Msg> selectHistory(String voipaccount) {
        String sql = "select * from history where voipaccount="+voipaccount;
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<Msg> msgArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String historyList = cursor.getString(cursor.getColumnIndex("historyList"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            Msg msg = new Msg(historyList,type);
            msgArrayList.add(msg);
        }
        return msgArrayList;
    }

    // 查询所有数据
    public ArrayList<Account> selectAll() {
        String sql = "select * from account";
        SQLiteDatabase db = manager.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<Account> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String voipaccount = cursor.getString(cursor.getColumnIndex("voipaccount"));
            Account account = new Account(voipaccount);
            list.add(account);
        }
        return list;
    }

}
