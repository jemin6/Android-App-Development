package com.example.jeminson.tidever21;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View.OnClickListener;


public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    private DataAccessLayer dal = new DataAccessLayer(this);
    Cursor cursor = null;
    String locationSelection = "97420";
    SimpleCursorAdapter adapter = null;

    private Button showTideButton;

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
        adapter = new SimpleCursorAdapter(this, R.layout.listview_items, cursor, new String[]{
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

        showTideButton = (Button) findViewById(R.id.showTideButton);
        showTideButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.showTideButton) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }
    } // End onClick

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
