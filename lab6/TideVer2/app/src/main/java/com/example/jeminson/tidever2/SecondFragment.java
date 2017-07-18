package com.example.jeminson.tidever2;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by jeminson on 2017. 7. 18..
 */

public class SecondFragment extends Fragment {

    // set up preferences
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        // get the default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // turn on the options menu
        setHasOptionsMenu(false);
    } // End onCreate

}
