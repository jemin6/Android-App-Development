package com.example.jeminson.tidever32;

/**
 * Created by jeminson on 2017. 7. 23..
 */

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TideItem {

    private String tideDate = null;
    private String time = null;
    private String pred = null;
    private String type = null;

    public void setTideDate(String tideDate) {
        this.tideDate = tideDate;
    }
    public String getTideDate() { return tideDate; }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() { return time; }

    public void setPred(String pred) {
        this.pred = pred;
    }
    public String getPred() { return pred; }

    public void setType(String type) {
        this.tideDate = type;
    }
    public String getType() { return type; }

} // End TideItem

