package jemin.uoregon.edu.lab1_part2;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;    // Import the classes for the widgets
import android.widget.Button;      // Import the classes for the widgets
import android.view.View.OnClickListener; // Import click event

public class Count extends AppCompatActivity
implements OnClickListener {

    // Declare variables for the widgets
    private int countNum = 0;
    private TextView count;
    public Button addOneButton;
    public Button resetButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        // Saving the data so it doesn't reset when the device is rotated
        if(savedInstanceState != null) {
            countNum = savedInstanceState.getInt("num");
        }

        // Get referenes to the widgets
        count = (TextView) findViewById(R.id.count);
        addOneButton = (Button) findViewById(R.id.addOneButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        count.setText("" + countNum);
        addOneButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.addOneButton:
                countNum++;                     // incrementing 1 each time
                count.setText(""+countNum);
                break;
            case R.id.resetButton:             // Reset to 0
                countNum = 0;
                count.setText(""+countNum);
                break;
        }
    }

    // Saving when the Activity is destored
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("num", countNum);
    }
}

