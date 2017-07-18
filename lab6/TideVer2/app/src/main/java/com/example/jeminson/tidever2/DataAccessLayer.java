package com.example.jeminson.tidever2;

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

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class DataAccessLayer {
    private Context context = null;

    public DataAccessLayer(Context context) { this.context = context; }

    // Parse the XML files and put the data in the db
    public void loadDbFromXML(String stationId) {

        // Get the data from the XML file
        String fileName = "station" + stationId + ".xml";
        TideItems items = parseXmlFile(fileName);
        items.setStationName(stationId);

        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Put tide table in the database
        ContentValues cv = new ContentValues();

        for(TideItem item : items) {

            cv.put(TideSQLiteHelper.DATE, item.getTideDate());
            cv.put(TideSQLiteHelper.STATION_ID, items.getStationId());
            cv.put(TideSQLiteHelper.STATION_NAME, items.getStationName());
            cv.put(TideSQLiteHelper.TIME, item.getTime());
            cv.put(TideSQLiteHelper.DAY, item.getDay());
            cv.put(TideSQLiteHelper.PRED_IN_FT, item.getPredInFt());
            cv.put(TideSQLiteHelper.HIGH_LOW, item.getHighLow());
            db.insert(TideSQLiteHelper.ITEM, null, cv);
        }  // End for loop
        db.close();

    } // End loadDbFromXML

    public Cursor getTideByLocation(String location) {

        // Initialize the database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Get tide table for one location
        String query = "SELECT * FROM item WHERE stationid = ? ORDER BY date ASC";
        String[] variables = new String[]{location};
        return db.rawQuery(query, variables);
    }

    public TideItems parseXmlFile(String fileName){
        try{
            // get the XML reader
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlreader = parser.getXMLReader();

            // set content handler
            ParseHandler handler = new ParseHandler();
            xmlreader.setContentHandler(handler);

            // read the file from internal storage
            InputStream in = context.getAssets().open(fileName);

            // parse the data
            InputSource is = new InputSource(in);
            xmlreader.parse(is);

            // set the feed in the activity
            TideItems items = handler.getItems();
            return items;

        } // End try
        catch( Exception e) {
            Log.e("News reader", e.toString());
            return null;
        }
    } // End parseXmlFile

}
