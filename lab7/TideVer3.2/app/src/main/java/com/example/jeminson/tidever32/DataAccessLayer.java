package com.example.jeminson.tidever32;

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

import static com.example.jeminson.tidever32.TideSQLiteHelper.*;

public class DataAccessLayer {

    // Instance variables
    private Context context = null;

    public DataAccessLayer(Context context) { this.context = context; }

    // Works for location and date
    public Cursor getTideFromDb(String state, String city, Date date) {

        // Initialize the database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Get tide table for one location
        String query = "SELECT * FROM" + DATA + " WHERE " + CITY + " = ? ORDER BY Date ASC";
        String[] variables = new String[]{city};

        // Do the query
        Cursor cursor = db.rawQuery(query, variables);

        return cursor;
    }

    protected TideItems parseXmlStream(InputStream in){
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

    protected void putTideIntoDb(TideItems items) {

        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Put in the database
        ContentValues cv = new ContentValues();

        for (TideItem item : items) {

            cv.put(TIME, item.getTime());
            db.insert(DATA, null, cv);
        }
        db.close();
    }

}
