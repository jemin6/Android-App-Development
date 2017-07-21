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

    public static final String ITEM_TABLE = "item";



    private Context context = null;

    public TideSQLiteHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ITEM_TABLE
                    );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
