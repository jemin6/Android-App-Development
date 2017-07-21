package com.example.jeminson.tidever3;


/**
 * Created by jeminson on 2017. 7. 20..
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static com.example.jeminson.tidever3.TideSQLiteHelper.*;

public class DataAccessLayer {

    // Instance variables
    private Context context = null;

    public DataAccessLayer(Context context) { this.context = context; }

    // Works for location and date
    public Cursor getTideByFromDb(String location, String date) {

        // Initialize the database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Get tide table for one location
        String query = "SELECT * FROM item WHERE Zip = ? AND Date = ? ORDER BY date ASC";
        String[] variables = new String[]{location, date};
        return db.rawQuery(query, variables);
    }



}
