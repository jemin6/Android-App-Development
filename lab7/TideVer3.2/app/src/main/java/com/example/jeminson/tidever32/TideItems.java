package com.example.jeminson.tidever32;

import java.util.ArrayList;

/**
 * Created by jeminson on 2017. 7. 23..
 */

public class TideItems extends ArrayList<TideItem> {

    // Default Serial ID
    private static final long serialVersionUID = 1L;

    // Info that applies
    private String stationId = "";
    private String beginDate = "";
    private String endDate = "";

    private String city = "";

    public String getStationId() { return stationId; }
    public void setStationId(String stationId) {this.stationId = stationId; }

    public String getBeginDate() { return beginDate; }
    public void setBeginDate(String beginDate) {this.beginDate = beginDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) {this.endDate = endDate; }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
