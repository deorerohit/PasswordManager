package com.example.passwordmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelperClass extends SQLiteOpenHelper {

    private static final String databaseName = "PasswordManager";
    private static final int version = 1;

    private static final String TABLE_NAME = "credentials";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";


    public SQLiteHelperClass(@Nullable Context context) {
        super(context, databaseName, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
     //   sqLiteDatabase.execSQL("drop table credentials");
        String createTable = "create table credentials(_id integer primary key autoincrement, title text, username text, password text)";
        sqLiteDatabase.execSQL(createTable);
    }


    public long insert(String title, String username, String password, SQLiteDatabase sqLiteDatabase ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);

        long id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return id;
    }

    public void update(long id, String title, String username, String password, SQLiteDatabase sqLiteDatabase) {
        String toUpdate = id+"";
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        sqLiteDatabase.update(TABLE_NAME,contentValues, "_id=?", new String[]{ toUpdate });
    }





    public void delete(long id, SQLiteDatabase sqLiteDatabase) {
        String toDelete = id+"";
        sqLiteDatabase.delete(TABLE_NAME,"_id=?", new String[]{ toDelete });
    }



    public void deleteAll(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("delete from "+TABLE_NAME);
        sqLiteDatabase.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME= 'credentials'");
       // sqLiteDatabase.execSQL("ALTER TABLE mytable AUTOINCREMENT = 1");
    }



    public Cursor getAllData(SQLiteDatabase sqLiteDatabase) {
        String getAll = "select * from "+TABLE_NAME;
        Cursor res = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }

    public Cursor latestInsertedData(SQLiteDatabase sqLiteDatabase) {
        String latestData = "SELECT * FROM credentials WHERE _id = (SELECT MAX(_id)  FROM credentials)";
        Cursor cursor = sqLiteDatabase.rawQuery(latestData, null);
        return cursor;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
