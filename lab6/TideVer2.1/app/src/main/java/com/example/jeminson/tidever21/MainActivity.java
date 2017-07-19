package com.example.jeminson.tidever21;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;


public class MainActivity extends Activity implements OnItemSelectedListener {

    private DataAccessLayer dal = new DataAccessLayer(this);
    Cursor cursor = null;
    String locationSelection = "97420";
    SimpleCursorAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up location selection spinner
        Spinner locationSpinner = (Spinner)findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);

        // Initialize the database
        dal.loadTestData("97420");

        // Get Forecast for the default location
        cursor = dal.getTideByLocation(locationSelection);

        // Set up the adapter for the ListView to display the forecast
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.listview_items,
                cursor,
                new String[]{
                        TideSQLiteHelper.DATE,
                        TideSQLiteHelper.DAY,
                        TideSQLiteHelper.TIME,
                        TideSQLiteHelper.PRED_IN_FT,
                        TideSQLiteHelper.PRED_IN_CM,
                        TideSQLiteHelper.HIGH_LOW
                },
                new int[]{
                        R.id.dateTextView,
                        R.id.dayTextView,
                        R.id.timeTextView,
                        R.id.predInFtView,
                        R.id.predInCmView,
                        R.id.highLowView
                },
                0 );	// no flags

        ListView itemsListView = (ListView)findViewById(R.id.tideListView);
        itemsListView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        switch (position){
            case 0:
                locationSelection = "97420";
                break;
            case 1:
                locationSelection = "97439";
                break;
            case 2:
                locationSelection = "97365";
        }
        // Get a weather forecast the selected location
        cursor = dal.getTideByLocation(locationSelection);
        adapter.changeCursor(cursor);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}
