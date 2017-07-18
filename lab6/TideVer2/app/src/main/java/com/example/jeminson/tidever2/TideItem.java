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
    private String day = null;
    private String time = null;
    private String pred_in_ft = null;
    private String highlow = null;

    // This is the format used in the XML file
    private SimpleDateFormat dateInFormat = new SimpleDateFormat("yyyy/MM/dd");  // <Date>2017/01/01</Date>

    // This is the format we want in our output
    private SimpleDateFormat dateOutFormat = new SimpleDateFormat("yyyy/MM/dd, EEEE, 'T'HH:mm a");

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

    public void setTideDate(String pubDate) {
        this.tideDate = pubDate;
    }
    public String getTideDate() {
        return tideDate;
    }

    public void setDay(String day) { this.day = day;}
    public String getDay() { return day; }

    public void setTime(String time) { this.time = time;}
    public String getTime() { return time; }

    public void setPredInFt(String pred_in_ft) { this.pred_in_ft = pred_in_ft; }
    public String getPredInFt() { return pred_in_ft; }

    public void setHighLow(String highlow) { this.highlow = highlow; }
    public String getHighLow() { return highlow; }

}
