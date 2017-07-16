package com.example.jeminson.tidetable11;

/**
 * Created by jeminson on 2017. 7. 15..
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

    public RSSFeed getItems() {
        return feed;
    } // End getItems()

    @Override
    public void startDocument() throws SAXException {
        feed = new RSSFeed();
        item = new RSSItem();
    } // End startDocument

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if (qName.equals("item")) {
            item = new RSSItem();
        }
        else if(qName.equals("date")) {
            isDate = true;
        }
        else if(qName.equals("day")) {
            isDay = true;
        }
        else if(qName.equals("time")) {
            isTime = true;
        }
        else if(qName.equals("pred_in_ft")) {
            isPredInFt = true;
        }
        else if(qName.equals("pred_in_cm")) {
            isPredInCm = true;
        }
    } // End startElement

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (qName.equals("item")) {
            feed.add(item);
            return;
        } // End if
    } // End endElement

    @Override
    public void characters(char ch[], int start, int length) {
        String s = new String(ch, start, length);
        if (isDate) {
            item.setDate(s);
            isDate = false;
        } // End if
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
    } // End characters

} //End RSSFeedHandler class
