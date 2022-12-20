package com.example.stepapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private MessageHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new MessageHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(MessageHelper.SUBJECT, name);
        contentValue.put(MessageHelper.DESC, desc);
        database.insert(MessageHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { MessageHelper._ID, MessageHelper.SUBJECT, MessageHelper.DESC };
        Cursor cursor = database.query(MessageHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MessageHelper.SUBJECT, name);
        contentValues.put(MessageHelper.DESC, desc);
        int i = database.update(MessageHelper.TABLE_NAME, contentValues, MessageHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(MessageHelper.TABLE_NAME, MessageHelper._ID + "=" + _id, null);
    }

}

