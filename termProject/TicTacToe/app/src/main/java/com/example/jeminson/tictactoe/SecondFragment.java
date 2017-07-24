package com.example.jeminson.tictactoe;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.RatingBar;
import android.widget.Toast;


/**
 * Created by jeminson on 2017. 7. 21..
 */

public class SecondFragment extends Fragment implements OnClickListener {

    // set up preferences
    private SharedPreferences prefs;

    private Button gotoTTTGameButton;
    private RatingBar ratingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        // get the default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // turn on the options menu
        setHasOptionsMenu(false);
    } // End onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.second_fragment, container, false);

        gotoTTTGameButton = (Button) view.findViewById(R.id.gotoTTTGameButton);
        gotoTTTGameButton.setOnClickListener(this);

        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    Toast.makeText(ratingBar.getContext(), "Your Selected Ratings  : " + String.valueOf(rating), Toast.LENGTH_SHORT).show();
                } // End if statement
            } // End onRatingChaged
        });

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gotoTTTGameButton:
                Intent intent = new Intent(getActivity(), TicTacToeGame.class);
                startActivity(intent);
                break;
        }
        /*
        if(v.getId() == R.id.gotoTTTGameButton) {
            Intent intent = new Intent(getActivity(), TicTacToeGame.class);
            startActivity(intent);
        } // End if statement
        */
    }
/*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("computerChoiceTextView",computerChoiceTextView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            computerChoiceTextView = savedInstanceState.getString("computerChoiceTextView","");
        }
    }
*/

    /*
    public void onPause() {

    }

    // To restore values
    @Override
    public void onResume() {
        super.onResume();

    }
    */
}
