package com.example.jeminson.tidever2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class FirstActivity extends AppCompatActivity implements OnClickListener{

    private Button showTideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        showTideButton = (Button) findViewById(R.id.showTideButton);
        showTideButton.setOnClickListener(this);

    } // End onCreate

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.showTideButton) {
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            startActivity(intent);
        }
    } // End onClick
} // End FirstActivity
