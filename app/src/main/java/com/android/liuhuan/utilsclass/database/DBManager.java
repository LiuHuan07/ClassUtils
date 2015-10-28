package com.android.liuhuan.utilsclass.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by LiuHuan on 2015/10/12.
 */
public class DBManager<T> {
    private static DBManager ourInstance ;

    public static DBManager newInstance(Context context){
        if (ourInstance == null) {
            ourInstance = new DBManager(context);
        }else {
            throw new IllegalArgumentException("Context must be set");
        }
        return ourInstance;
    }
    public static DBManager getInstance() {
        if (ourInstance==null){
            throw new IllegalStateException("Please invoke newInstance");
        }
        return ourInstance;
    }
    private  Context context;

    private  DBHelper helper;
    private DBManager(Context context) {
        this.context=context;
        helper=new DBHelper(context);
    }
    public long insert(T book){

        long ret=0;
        if (book != null) {
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
//            values.put("title", book.getTitle());
//            values.put("price", book.getPrice());
//            values.put("author", book.getAuthor());

            ret = db.insert("books", null, values);

            db.close();
        }
        return ret;
    }

    public T findById(long id){
        T ret=null;
        SQLiteDatabase db=helper.getReadableDatabase();
       Cursor cursor = db.query("books",//table
                null,//列
                "_id=?",//where条件
                new String[]{Long.toString(id)},//条件参数
                null,
                null,
                null
        );
        if (cursor!=null){
            cursor.close();
            if (cursor.moveToNext()){
//                ret=new T();
                int index=cursor.getColumnIndex("_id");
                if(index!=-1){
//                    ret.setId(cursor.getLong(index));
                }
                index=cursor.getColumnIndex("title");
                if (index!=-1){
//                    ret.setTitle(cursor.getString(index));
                }
            }
        }
        db.close();
        return ret;
    }
}
