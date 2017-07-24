package com.example.jeminson.tidever3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeminson on 2017. 7. 20..
 */

public class TideSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tide.sqlite";
    private static final int DATABASE_VERSION = 1;

    public static final String HIGH_LOW_VALUES = "HighLowValues";
    public static final String ITEM = "item";
    public static final String DATA = "data";
    public static final String TIME = "time";
    public static final String PRED = "pred";
    public static final String TYPE = "type";

    public static final String STATE = "state";
    public static final String CITY = "city";

    private Context context = null;

    public TideSQLiteHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATA
                + "( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TIME + " TEXT,"
                + PRED + " INTEGER,"
                + TYPE + " TEXT,"
                + STATE + " TEXT,"
                + CITY + " TEXT,"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
