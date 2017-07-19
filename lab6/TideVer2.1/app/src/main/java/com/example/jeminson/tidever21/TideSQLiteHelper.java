package com.example.jeminson.tidever21;

/**
 * Created by jeminson on 2017. 7. 18..
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TideSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tide.sqlite";
    private static final int DATABASE_VERSION = 1;

    public static final String ITEM = "item";
    public static final String DATE = "date";
    public static final String ZIP = "Zip";
    public static final String CITY = "City";
    public static final String DAY = "day";
    public static final String TIME = "time";
    public static final String PRED_IN_FT = "pred_in_ft";
    public static final String PRED_IN_CM = "pred_in_cm";
    public static final String HIGH_LOW = "highlow";

    private Context context = null;

    public TideSQLiteHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ITEM
                + "( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DATE + " TEXT,"
                + ZIP + " INTEGER,"
                + CITY + " TEXT,"
                + DAY + " TEXT,"
                + TIME + " TEXT,"
                + PRED_IN_FT + " INTEGER,"
                + PRED_IN_CM + " INTEGER,"
                + HIGH_LOW + " TEXT"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
