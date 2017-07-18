package com.example.jeminson.tidever2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeminson on 2017. 7. 17..
 */

public class TideSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATEBASE_NAME = "";
    private static final int DATEBASE_VERSION = 1;

    public static final String ITEM = "item";
    public static final String DATE = "date";
    public static final String STATION_NAME = "stationname";
    public static final String STATION_ID = "stationid";
    public static final String DAY = "day";
    public static final String PRED_IN_FT = "pred_in_ft";
    public static final String HIGH_LOW = "highlow";


    private Context context = null;

    public TideSQLiteHelper(Context c) {
        super(c, DATEBASE_NAME, null, DATEBASE_VERSION);
        this.context = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ITEM + "( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DATE + " TEXT,"
                + STATION_ID + " INTEGER,"
                + STATION_NAME + " TEXT,"
                + DAY + " TEXT,"
                + PRED_IN_FT + " INTEGER,"
                + HIGH_LOW + " TEXT,"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

} // End tideSQLiteHelper
