package com.example.jeminson.tidever32;

/**
 * Created by jeminson on 2017. 7. 23..
 */


import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import static com.example.jeminson.tidever32.TideSQLiteHelper.*;

public class ParseHandler extends DefaultHandler {
    private TideItems tideItems;
    private TideItem item;

    private boolean isTime = false;
    private boolean isPred = false;
    private boolean isType = false;
    private boolean isDone = false;

    public TideItems getItems() {
        return tideItems;
    }

    @Override
    public void startDocument() throws SAXException {
        tideItems = new TideItems();
        item = new TideItem();
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if (isDone) {
            // Do nothing because we've parsed everything we need
        } else if (qName.equals(TideSQLiteHelper.DATA)) {
            item = new TideItem();
        } else if (qName.equals(TideSQLiteHelper.TIME)) {
            isTime = true;
        } else if (qName.equals(TideSQLiteHelper.PRED)) {
            isPred = true;
        } else if (qName.equals(TideSQLiteHelper.TYPE)) {
            isType = true;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (qName.equals("item")) {
            tideItems.add(item);
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
    {
        String s = new String(ch, start, length);
        if (isTime) {
            item.setTime(s);
            isTime = false;
        }
        else if (isPred) {
            item.setPred(s);
            isPred = false;
        }
        else if (isType) {
            item.setType(s);
            isType = false;
        }
    }

}