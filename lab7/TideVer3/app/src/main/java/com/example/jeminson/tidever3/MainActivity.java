package com.example.jeminson.tidever3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    // Button that goes to SecondActivity
    private Button showTideButton;
    private Spinner locationSpinner;

    // Used for setting location on the MainActivity
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
    }


    // onClick which send saved data from spinner to SecondActivity when press the showTideButton
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.showTideButton) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("location", locationSelection);
            //intent.putExtra("date", dateString);
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
    } // End onItemSelected

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }

} // End MainActivity
