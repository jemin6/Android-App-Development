package com.example.jeminson.tidever21;

/**
 * Created by jeminson on 2017. 7. 17..
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

import static com.example.jeminson.tidever21.TideSQLiteHelper.*;
import static com.example.jeminson.tidever21.TideSQLiteHelper.ITEM;


public class DataAccessLayer {

    // Instance variables
    private Context context = null;

    public DataAccessLayer(Context context) { this.context = context; }


    public Cursor getTideFromDb(String state, String city, Date date)
    {
        // Initialize the database for reading
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Set up a query for a weather forecast for one location
        String query = "SELECT * FROM " + ITEM + " WHERE " + CITY + " = ? ORDER BY Date ASC";
        String[] variables = new String[]{state, city};    // rawQuery must not include a trailing ';'

        // Do the query
        Cursor cursor = db.rawQuery(query, variables);

        return cursor;
    }

    protected TideItems parseXmlStream(InputStream in) {
        TideItems items = null;
        if (in != null) {
            try {
                // get the XML reader
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XMLReader xmlreader = parser.getXMLReader();

                // set content handler
                ParseHandler handler = new ParseHandler();
                xmlreader.setContentHandler(handler);

                // parse the data
                InputSource is = new InputSource(in);
                xmlreader.parse(is);
                items = handler.getItems();
            } catch (Exception e) {
                Log.e("Tide", "parseXMLStream error: " + e.toString());
            }
        }
        return items;
    }

    protected void putTideIntoDb(TideItems items) {

        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Put weather forecast in the database
        ContentValues cv = new ContentValues();

        for (TideItem item : items) {
            cv.put(STATE, items.getState() );
            cv.put(CITY, items.getCity() );

            db.insert(ITEM, null, cv);
        }
        db.close();
    }

}
