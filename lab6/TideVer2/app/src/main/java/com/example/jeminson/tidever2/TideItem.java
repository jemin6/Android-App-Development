package com.example.jeminson.tidever2;

/**
 * Created by jeminson on 2017. 7. 17..
 */

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TideItem {

    private String tideDate = null;
    private String tideDay = null;
    private String tideTime = null;
    private String predInFt = null;
    private String predInCm	 = null;
    private String highLow = null;

    // This is the format used in the XML file
    private SimpleDateFormat dateInFormat = new SimpleDateFormat("yyyy/MM/dd");  // <Date>2017/01/01</Date>

    // This is the format we want in our output
    private SimpleDateFormat dateOutFormat = new SimpleDateFormat("yyyy/MM/dd");

    public String getTideDateFormatted() {
        try {
            Date date = dateInFormat.parse(tideDate.trim());
            String tideDateFormatted = dateOutFormat.format(date);
            return tideDateFormatted;
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTideDate(String tideDate) {
        this.tideDate = tideDate;
    }
    public String getTideDate() {
        return tideDate;
    }

    public void setTideDay(String tideDay) {
        this.tideDay = tideDay;
    }
    public String getTideDay() {
        return tideDay;
    }

    public void setTideTime(String tideTime) {
        this.tideTime = tideTime;
    }
    public String getTideTime() {
        return tideTime;
    }

    public void setPredInFt(String predInFt) {
        this.predInFt = predInFt;
    }
    public String getPredInFt() {
        return predInFt;
    }

    public void setPredInCm(String predInCm) {
        this.predInCm = predInCm;
    }
    public String getPredInCm() {
        return predInCm;
    }

    public void setHighLow(String highLow) { this.highLow = highLow; }
    public String getHighLow() {
        return highLow;
    }

}
