package com.karl.c9_weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(Context context, String name,
                      SQLiteDatabase.CursorFactory factory, int version){
        super(context, "my.db", factory, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists " + "user" + " (Id integer primary key AUTOINCREMENT,cityName text,cityCode text)";
        sqLiteDatabase.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS user";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

}