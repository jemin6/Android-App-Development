package com.example.jeminson.tidever2;

/**
 * Created by jeminson on 2017. 7. 17..
 */

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;

public class SecondActivity extends AppCompatActivity {

    private DataAccessLayer dal = new DataAccessLayer(this);
    Cursor cursor = null;
    String stationSelection = "9432845";
    SimpleCursorAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.second_activity);

        cursor = dal.getTideByLocation(stationSelection);

        // Set up the adapter for the ListView to display the forecast
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.listview_items,
                cursor,
                new String[]{TideSQLiteHelper.DATE,
                        TideSQLiteHelper.DAY,
                        TideSQLiteHelper.TIME,
                        TideSQLiteHelper.PRED_IN_FT,
                        TideSQLiteHelper.HIGH_LOW},
                new int[]{
                        R.id.dateTextView,
                        R.id.dayTextView,
                        R.id.timeTextView,
                        R.id.predInFtTextView,
                        R.id.highLowTextView
                },
                0 );	// no flags

        android.widget.ListView itemsListView = (ListView)findViewById(R.id.tideListView);
        itemsListView.setAdapter(adapter);
        itemsListView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }
}
