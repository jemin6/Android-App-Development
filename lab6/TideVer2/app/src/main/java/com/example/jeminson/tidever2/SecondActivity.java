package com.example.jeminson.tidever2;

/**
 * Created by jeminson on 2017. 7. 17..
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView dateTextView;
    //private TextView dayTextView;
    //private TextView timeTextView;
    //private TextView predInFtTextView;
    //private TextView highLowTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();


        // Get data from the FirstActivity
        Intent intent = getIntent();

        dateTextView = (TextView) findViewById(R.id.dateTextView);
        dateTextView.setText("date");
        //dayTextView = (TextView) findViewById(R.id.dayTextView);
        //timeTextView = (TextView) findViewById(R.id.timeTextView);
        //predInFtTextView = (TextView) findViewById(R.id.predInFtTextView);
        //highLowTextView = (TextView) findViewById(R.id.highLowTextView);


    }
}
