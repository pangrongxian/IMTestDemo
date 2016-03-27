package com.zyys.zyysdemo.adb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * MySQLiteManager：数据库管理者  继承于SQLiteOpenHelper抽象类
 */
public class MySQLiteManager extends SQLiteOpenHelper {//用来创建数据库和数据表的类

    private static final String NAME = "data.db";
    private static final int VERSION = 1;

    public MySQLiteManager(Context context) {
        super(context, NAME, null, VERSION);
    }


    //四个参数的构造函数
    public MySQLiteManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        /*context:环境,上下文
         * name:数据库名称
		 * factory:游标工厂
		 * version:数据库版本号
		 */
        super(context, name, factory, version);
    }

    /**
     *
     * @param db
     */
    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String form = "CREATE TABLE account(_id integer primary key autoincrement,voipaccount text)";

        String history = "CREATE TABLE history(_id integer primary key autoincrement,voipaccount text,historyList text,type integer)";

        //执行语句
        db.execSQL(form);
        db.execSQL(history);
    }

    //更新表,版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //当版本号改变的时候，先删除原来的数据库，在重新创建。
        db.execSQL("drop table if exists account");
        db.execSQL("drop table if exists history");
        Log.d("MySQLiteManager", "升级数据库成功");
        onCreate(db);
    }

    //需要还原版本时调用
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

}
