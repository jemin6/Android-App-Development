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
        String query = "SELECT * FROM data WHERE Zip = ? AND Date = ? ORDER BY date ASC";
        String[] variables = new String[]{location, date};
        return db.rawQuery(query, variables);
    }

    protected TideItems parseXmlFile(InputStream in){
        TideItems items = null;
        if(in != null) {
            try{
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
            } catch( Exception e) {
                Log.e("tide", "parseXMLStream error: " + e.toString());
            }
        }
        return items;
    } // End parseXmlFile

    protected void putForecastIntoDb(TideItems items) {

        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Put weather forecast in the database
        ContentValues cv = new ContentValues();

        for (TideItem item : items) {
            cv.put(STATE, items.getZip() );
            cv.put(CITY, items.getCity() );
            cv.put(TIME, item.getTime());
            cv.put(PRED, item.getPred());
            cv.put(TYPE, item.getType());
            db.insert(DATA, null, cv);
        }
        db.close();
    }

}
