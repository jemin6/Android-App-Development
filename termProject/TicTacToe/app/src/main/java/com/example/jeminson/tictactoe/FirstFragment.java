package com.example.jeminson.tictactoe;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by jeminson on 2017. 7. 21..
 */

public class FirstFragment extends Fragment implements OnClickListener {

    private Button rpsPlayButton;

    // set up preferences
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(getActivity(),
                R.xml.preferences, false);

        // get the default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // turn on the options menu
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_fragment, container, false);

        rpsPlayButton = (Button) view.findViewById(R.id.rpsPlayButton);
        rpsPlayButton.setOnClickListener(this);

        return view;
    } // End onCreateView

    // Implement the interface for the listner
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rpsPlayButton) {
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            startActivity(intent);
        } // End if statement
    } // End onClick

    // Menu & Settings
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // attempt to get the fragment
        SettingsFragment settingsFragment = (SettingsFragment) getFragmentManager().findFragmentById(R.id.settings_fragment);

        // if the fragment is null, display the appropriate menu
        if (settingsFragment == null) {
            inflater.inflate(R.menu.menu, menu);
        } else {
            inflater.inflate(R.menu.menu_twopane, menu);
        }
    } // End onCreateOptionsMenu

    // Buttons that works on the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // End switch
    } // End onOptionsItemSelected

    // To save values
    @Override
    public void onPause() {
        // save the instance variables
        SharedPreferences.Editor editor = prefs.edit();

        editor.commit();

        super.onPause();
    } // End onPause

    // To restore values
    @Override
    public void onResume() {
        super.onResume();

    } // End OnResume()

}
