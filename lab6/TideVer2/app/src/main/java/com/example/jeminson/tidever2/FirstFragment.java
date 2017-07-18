package com.example.jeminson.tidever2;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.icu.util.Calendar;



import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by jeminson on 2017. 7. 18..
 */

public class FirstFragment extends Fragment implements OnClickListener {

    // For Date Picker
    private static final String TAG = "FirstFragment";

    private Button showTideButton;

    // set up preferences
    private SharedPreferences prefs;

    private TextView mDisyplayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(getActivity(),
                R.xml.preferences, false);

        // get the default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // turn on the options menu
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_fragment, container, false);

        showTideButton = (Button) view.findViewById(R.id.showTideButton);
        showTideButton.setOnClickListener(this);

        mDisyplayDate = (TextView) view.findViewById(R.id.mDisyplayDate);

        mDisyplayDate.setOnClickListener(new View.OnClickListener() {
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

        return view;
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.showTideButton) {
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            startActivity(intent);
        }
    } // End onClick

}
