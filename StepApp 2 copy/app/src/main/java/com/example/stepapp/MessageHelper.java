package com.example.stepapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.Toast;

import com.example.stepapp.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MessageHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "Messages";

    // Table columns
    public static final String _ID = "_id";
    public static final String SUBJECT = "Sender";
    public static final String DESC = "Message";
    public static final String DATA = "Data";


    // Database Information
    static final String DB_NAME = "BluetoothMessages.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + DESC + "TEXT" + DATA + " TEXT);";

    public MessageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

//class BluetoothMessageListener<> implements SensorEventListener {
//    // Constant for the notification ID:
//    public static int NOTIFICATION_ID =0 ;
//
//    //Get the number of stored steps for the current day
////    public int mACCStepCounter = null;
//
//    ArrayList<Integer> mACCSeries = new ArrayList<Integer>();
//    ArrayList<String> mTimeSeries = new ArrayList<String>();
//
//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent) {
//
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//
//    }
//
//}