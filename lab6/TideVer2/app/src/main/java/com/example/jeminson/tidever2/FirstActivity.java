package com.example.jeminson.tidever2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.first_activity);
    } // End onCreate
} // End FirstActivity class






/*
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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class FirstActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    // For Date Picker
    private static final String TAG = "FirstActivity";

    private DataAccessLayer dal = new DataAccessLayer(this);
    Cursor cursor = null;
    String stationSelection = "97420";
    SimpleCursorAdapter adapter = null;


    private TextView mDisyplayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;


    private Spinner locationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);

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

    } // End onCreate


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        switch (position){
            case 0:
                stationSelection = "97420";
                break;
            case 1:
                stationSelection = "97365";
                break;
            case 2:
                stationSelection = "97439";
                break;
        }
        // Get the selected location
        cursor = dal.getTideByLocation(stationSelection);
        adapter.changeCursor(cursor);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
} // End FirstActivity
*/