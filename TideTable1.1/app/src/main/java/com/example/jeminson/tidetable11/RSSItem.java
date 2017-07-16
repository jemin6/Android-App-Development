package com.example.jeminson.tidetable11;

/**
 * Created by jeminson on 2017. 7. 15..
 */

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class RSSItem {

    private String date = null;
    private String day = null;
    private String time = null;
    private String predInFt = null;
    private String predInCm = null;

    // This is the format used in the weather XML file
    private SimpleDateFormat dateInFormat = new SimpleDateFormat("yyyy-MM-dd");  // <Date>2014-06-28T00:00:00</Date>

    // This is the format we want in our output
    private SimpleDateFormat dateOutFormat = new SimpleDateFormat("yyyy.MM.dd ''EEEE" );

    public String getDateFormatted() {
        try {
            Date d = dateInFormat.parse(date.trim());
            String dateFormatted = dateOutFormat.format(d);
            return dateFormatted;
        } // End try
        catch (ParseException e) {
            throw new RuntimeException(e);
        } // End catch
    } // End getDateFormatted


    public void setDate(String pubDate) { this.date = pubDate; }
    public String getDate() { return date; }

    public void setDay(String day) { this.day = day; }
    public String getDay() { return day; }

    public void setTime(String time) { this.time = time; }
    public String getTime() { return time; }

    public void setPredInFt(String predInFt) { this.predInFt = predInFt; }
    public String getPredInFt() { return predInFt; }

    public void setPredInCm(String predInCm) { this.predInCm = predInCm; }
    public String getPredInCm() { return predInCm; }

} // End RSSItem class
