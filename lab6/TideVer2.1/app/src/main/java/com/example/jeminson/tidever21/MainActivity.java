package com.example.jeminson.tidever21;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.util.Date;


import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    private static final String TAG = "FirstActivity";
    private SimpleDateFormat dateOutFormat = new SimpleDateFormat("yyyy/MM/dd");


    // Used for setting location on the MainActivity
    private DataAccessLayer dal = new DataAccessLayer(this);
    Cursor cursor = null;
    String locationSelection = "97420";
    SimpleCursorAdapter adapter = null;

    // Button that goes to SecondActivity
    private Button showTideButton;

    // TextView that shows the selected date from the picker
    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    String dateString;

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

        // Set up the adapter for the ListView to display
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

        // For date picker
        displayDate = (TextView) findViewById(R.id.displayDate);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        }); // End onClick

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                year = year - 1900;
                Log.d(TAG, "onDateSet: yyyy/MM/dd: " + year + "/" + month + "/" + day);

                Date date = new Date(year, month, day);
                dateString = dateOutFormat.format(date);

                displayDate.setText(dateString);
            }
        };

    } // End onCreate

    // onClick which send saved data from spinner to SecondActivity when press the showTideButton
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.showTideButton) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("location", locationSelection);
            intent.putExtra("date", dateString);
            startActivity(intent);
        } // End if
    } // End onClick

    // Spinner selection
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                locationSelection = "97420";
                break;
            case 1:
                locationSelection = "97439";
                break;
            case 2:
                locationSelection = "97365";
                break;
        }
        // Get the selected location
        cursor = dal.getTideByLocation(locationSelection);
        adapter.changeCursor(cursor);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}
