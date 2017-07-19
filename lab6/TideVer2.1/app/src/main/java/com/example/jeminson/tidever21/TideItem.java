package com.example.jeminson.tidever21;

/**
 * Created by jeminson on 2017. 7. 18..
 */

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class TideItem {

    private String tideDate = null;
    private String tideDay = null;
    private String tideTime = null;
    private String predInFt = null;
    private String predInCm	 = null;
    private String highLow = null;


    public void setTideDate(String tideDate) {
        this.tideDate = tideDate;
    }
    public String getTideDate() { return tideDate; }

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