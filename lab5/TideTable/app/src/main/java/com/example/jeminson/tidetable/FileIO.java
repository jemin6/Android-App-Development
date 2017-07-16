package com.example.jeminson.tidetable;

/**
 * Created by jeminson on 2017. 7. 16..
 */

import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.content.Context;
import android.util.Log;

public class FileIO {

    private final String FILENAME = "tide.xml";
    private Context context = null;

    public FileIO (Context context) {
        this.context = context;
    }

    public RSSFeed readFile() {
        try {
            // get the XML reader
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlreader = parser.getXMLReader();

            // set content theRssHandler
            RSSFeedHandler theRssHandler = new RSSFeedHandler();
            xmlreader.setContentHandler(theRssHandler);

            // read the file from internal storage
            InputStream in = context.getAssets().open(FILENAME);

            // parse the data
            InputSource is = new InputSource(in);
            xmlreader.parse(is);

            // set the feed in the activity
            RSSFeed items = theRssHandler.getItems();
            return items;
        }
        catch (Exception e) {
            Log.e("News reader", e.toString());
            return null;
        }
    }
}