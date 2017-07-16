package jemin.uoregon.edu.countnumbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public abstract class Counts extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counts);
    }

}
