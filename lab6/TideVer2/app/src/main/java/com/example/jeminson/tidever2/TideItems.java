package com.example.jeminson.tidever2;

/**
 * Created by jeminson on 2017. 7. 17..
 */

import java.util.ArrayList;

public class TideItems extends ArrayList<TideItem> {
    // Extending ArrayList to facilitate possible future features

    // Default Serial ID
    private static final long serialVersionUID = 1L;

    // Info that applies to the whole tide
    private String stationid = "";
    private String stationname = "";

    public String getStationId() { return stationid; }
    public void setStationId(String stationid) { this.stationid = stationid; }

    public String getStationName() { return stationname; }
    public void setStationName(String stationname) { this.stationname = stationname; }

}
