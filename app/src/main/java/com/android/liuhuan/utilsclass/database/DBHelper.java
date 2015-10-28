package com.android.liuhuan.utilsclass.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LiuHuan on 2015/10/12.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="app.db";
    public static final int DB_VER=1;

    private static final String CREATE_TABLE_BOOK =
            "create table books() {"
                    +" _id integer primary key autoincrement,"
                    +"title text not null,"
                    +"price real default,"
                    +"author text default 'None'"
                    +"}";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOK);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
