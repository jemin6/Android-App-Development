package com.example.jeminson.tidever21;

/**
 * Created by jeminson on 2017. 7. 18..
 */

import java.util.ArrayList;

public class TideItems extends ArrayList<TideItem> {
    // Extending ArrayList to facilitate possible future features

    // Default Serial ID
    private static final long serialVersionUID = 1L;

    // Info that applies to the whole forecast
    private String state = "";
    private String city = "";

    public String getState() { return state; }
    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

}


