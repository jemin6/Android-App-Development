package com.example.jeminson.tidever21;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by jeminson on 2017. 7. 18..
 */

public class SecondActivity extends AppCompatActivity {
    private DataAccessLayer dal = new DataAccessLayer(this);
    String locationSelection = "97420";

    Cursor cursor = null;

    SimpleCursorAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get data from the MainActivity
        Intent intent = getIntent();

        // Initialize the database
        //dal.loadTestData("97420");

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

        ListView itemsListView = (ListView)findViewById(R.id.tideListView);
        itemsListView.setAdapter(adapter);

    }
}
