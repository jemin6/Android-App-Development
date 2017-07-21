package com.example.jeminson.tidever3;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    // Button that goes to SecondActivity
    private Button showTideButton;

    // Spinner to select the location
    private Spinner locationSpinner;

    // TextView that shows the selected date from the picker
    private static final String TAG = "FirstActivity";
    private SimpleDateFormat dateOutFormat = new SimpleDateFormat("yyyy/MM/dd");
    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    String dateString;

    /******* ------  Instance Variables -------------- *************/
    private DataAccessLayer dal = new DataAccessLayer(this);
    Cursor cursor = null;
    String locationSelection = "Wauna";
    String dateSelection = "07/20/2017";
    SimpleCursorAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up location selection spinner
        locationSpinner = (Spinner)findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);

        showTideButton = (Button) findViewById(R.id.showTideButton);
        showTideButton.setOnClickListener(this);

        //cursor = dal.getTideByFromDb(locationSelection, dateSelection);

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

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, year,month,day);
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
    }

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
                locationSelection = "Wauna";
                break;
            case 1:
                locationSelection = "Garibaldi";
                break;
            case 2:
                locationSelection = "Skamokawa";
                break;
        }
        // Get the selected location
        //getTide(stateSelection, locationSelection, dateSelection);

        // Get the selected location
        //cursor = dal.getTideByFromDb(locationSelection, dateSelection);
        //adapter.changeCursor(cursor);
    } // End onItemSelected

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }

} // End MainActivity
