package com.example.jeminson.tidever21;

/**
 * Created by jeminson on 2017. 7. 18..
 */

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class TideItem {

    private String tideDate = null;
    private String tideTime = null;
    private String tidePred = null;
    private String tideType = null;


    public void setTideDate(String tideDate) {
        this.tideDate = tideDate;
    }
    public String getTideDate() { return tideDate; }

    public void setTideTime(String tideTime) {
        this.tideTime = tideTime;
    }
    public String getTideTime() {
        return tideTime;
    }

    public void setTidePred(String tidePred) {
        this.tidePred = tidePred;
    }
    public String getTidePred() {
        return tidePred;
    }

    public void setTideType(String tideType) { this.tideType = tideType; }
    public String getTideType() {
        return tideType;
    }

}