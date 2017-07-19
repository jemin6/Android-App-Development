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

    // This is a temporary method for loading fixed data into the db
    public void loadTestData(String zipCode)
    {
        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        // load the database with test data if it isn't already loaded
        if (db.rawQuery("SELECT * FROM item WHERE " + TideSQLiteHelper.ZIP
                + " = " + zipCode, null).getCount() == 0)
        {
            loadDbFromXML("97420"); // Coos Bay
            loadDbFromXML("97439"); // Florence
            loadDbFromXML("97365");	// Newport
        }
        db.close();
    }


    // Parse the XML files and put the data in the db
    public void loadDbFromXML(String zipCode) {

        // Get the data from the XML file
        String fileName = "tide" + zipCode + ".xml";
        TideItems items = parseXmlFile(fileName);
        items.setZip(zipCode);

        // Initialize database
        TideSQLiteHelper helper = new TideSQLiteHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Put tide table in the database
        ContentValues cv = new ContentValues();

        for(TideItem item : items) {
            cv.put(TideSQLiteHelper.DATE, item.getTideDateFormatted());
            cv.put(TideSQLiteHelper.ZIP, items.getZip());
            cv.put(TideSQLiteHelper.CITY, items.getCity());
            cv.put(TideSQLiteHelper.DAY, item.getTideDay());
            cv.put(TideSQLiteHelper.TIME, item.getTideTime());
            cv.put(TideSQLiteHelper.PRED_IN_FT, item.getPredInFt());
            cv.put(TideSQLiteHelper.PRED_IN_CM, item.getPredInCm());
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
        String query = "SELECT * FROM item WHERE Zip = ? ORDER BY date ASC";
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
