package com.example.jeminson.tidever3;

/**
 * Created by jeminson on 2017. 7. 19..
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

    private TextView showLocation;
    //private TextView showDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        showLocation = (TextView) findViewById(R.id.showLocation);
        //showDate = (TextView) findViewById(R.id.showDate);
    } // End onCreate

    @Override
    protected void onResume() {
        super.onResume();

        // Get data from the MainActivity
        Intent intent = getIntent();
        String value = intent.getExtras().getString("location");
        //String date = intent.getExtras().getString("date");

        showLocation.setText(value);
        //showDate.setText(date);
    }

} // End SecondActivity
