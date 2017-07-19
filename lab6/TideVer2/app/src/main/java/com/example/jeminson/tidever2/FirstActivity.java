package com.example.jeminson.tidever2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class FirstActivity extends AppCompatActivity implements OnItemSelectedListener{

    private static final String TAG = "FirstActivity";

    private DataAccessLayer dal = new DataAccessLayer(this);
    Cursor cursor = null;
    String locationSelection = "97420";
    SimpleCursorAdapter adapter = null;

    /*
    private TextView mDisyplayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private Button showTideButton;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        // Set up location selection spinner
        Spinner locationSpinner = (Spinner)findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);

        // Initialize the database
        dal.loadTestData("97420");

        // Get Forecast for the default location
        cursor = dal.getTideByLocation(locationSelection);


        /*
        mDisyplayDate = (TextView) findViewById(R.id.mDisyplayDate);

        mDisyplayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        FirstActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisyplayDate.setText(date);
            }
        };

        showTideButton = (Button) findViewById(R.id.showTideButton);
        showTideButton.setOnClickListener(this);
        */


        locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);

        cursor = dal.getTideByLocation(locationSelection);

/*
        // Set up the adapter for the ListView to display the forecast
        adapter = new SimpleCursorAdapter(this, R.layout.listview_items, cursor,
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

        ListView itemsListView = (ListView)findViewById(R.id.tideListView);
        itemsListView.setAdapter(adapter);
*/
    } // End onCreate

    /*
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.showTideButton) {
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            startActivity(intent);
        }
    } // End onClick
    */

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
} // End FirstActivity
