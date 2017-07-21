package com.example.jeminson.tidever3;

/**
 * Created by jeminson on 2017. 7. 20..
 */

import java.util.ArrayList;

public class TideItems extends ArrayList<TideItem> {
    // Extending ArrayList to facilitate possible future features

    // Default Serial ID
    private static final long serialVersionUID = 1L;

    // Info that applies
    private String zip = "";
    private String city = "";

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
