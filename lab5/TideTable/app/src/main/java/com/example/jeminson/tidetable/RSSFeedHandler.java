package com.example.jeminson.tidetable;

/**
 * Created by jeminson on 2017. 7. 16..
 */

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

public class RSSFeedHandler extends DefaultHandler {
    private RSSFeed feed;
    private RSSItem item;

    private boolean isDate = false;
    private boolean isDay = false;
    private boolean isTime = false;
    private boolean isPredInFt = false;
    private boolean isPredInCm = false;
    private boolean isHighLow = false;

    public RSSFeed getItems() {
        return feed;
    }

    @Override
    public void startDocument() throws SAXException {
        feed = new RSSFeed();
    }

    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {

        if (qName.equals("item")) {
            item = new RSSItem();
        }
        else if (qName.equals("date")) {
            isDate = true;
        }
        else if(qName.equals("day")) {
            isDay = true;
        }
        else if(qName.equals("time")) {
            isTime = true;
            return;
        }
        else if(qName.equals("pred_in_ft")) {
            isPredInFt = true;
            return;
        }
        else if(qName.equals("pred_in_cm")) {
            isPredInCm = true;
            return;
        }
        else if(qName.equals("highlow")){
            isHighLow = true;
            return;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName,
                           String qName) throws SAXException
    {
        if (qName.equals("item")) {
            feed.add(item);
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
    {
        String s = new String(ch, start, length);
        if (isDate) {
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
        else if(isPredInCm) {
            item.setPredInCm(s);
            isPredInCm = false;
        }
        else if(isHighLow) {
            item.setHighLow(s);
            isHighLow = false;
        }
    }
}
