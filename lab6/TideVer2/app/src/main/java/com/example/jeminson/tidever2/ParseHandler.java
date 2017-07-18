package com.example.jeminson.tidever2;

/**
 * Created by jeminson on 2017. 7. 17..
 */


import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

public class ParseHandler extends DefaultHandler {

    private TideItems tideItems;
    private TideItem item;

    private boolean isStationName = false;
    private boolean isDate = false;
    private boolean isDay = false;
    private boolean isTime = false;
    private boolean isPredInFt = false;
    private boolean isHighLow = false;

    public TideItems getItems() { return tideItems; }

    @Override
    public void startDocument() throws SAXException {
        tideItems = new TideItems();
        item = new TideItem();
    }

    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {

        if(qName.equals(TideSQLiteHelper.STATION_NAME)) {
            isStationName = true;
        }
        else if(qName.equals(TideSQLiteHelper.ITEM)) {
            item = new TideItem();
        }
        else if(qName.equals(TideSQLiteHelper.DATE)) {
            isDate = true;
        }
        else if(qName.equals(TideSQLiteHelper.DAY)) {
            isDay = true;
        }
        else if(qName.equals(TideSQLiteHelper.TIME)) {
            isTime = true;
        }
        else if(qName.equals(TideSQLiteHelper.PRED_IN_FT)) {
            isPredInFt = true;
        }
        else if(qName.equals(TideSQLiteHelper.HIGH_LOW)) {
            isHighLow = true;
        }

    }

    @Override
    public void endElement(String namespaceURI, String localName,
                           String qName) throws SAXException
    {
        if (qName.equals("item")) {
            tideItems.add(item);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {
        String s = new String(ch, start, length);

        if(isStationName) {
            tideItems.setStationName(s);
            isStationName = false;
        }
        else if(isDate) {
            item.setTideDate(s);
            isDate = false;
        }
        else if(isDay) {
            item.setDay(s);
            isDay = false;
        }
        else if(isTime) {
            item.setTime(s);
            isTime = false;
        }
        else if(isPredInFt) {
            item.setPredInFt(s);
            isPredInFt = false;
        }
        else if(isHighLow) {
            item.setHighLow(s);
            isHighLow = false;
        }

    }

}
