package com.example.jeminson.tictactoe;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


/**
 * Created by jeminson on 2017. 7. 21..
 */

public class FirstFragment extends Fragment implements OnClickListener {

    RockPaperScissorsGame rpsGame = new RockPaperScissorsGame();

    private TextView computerChoiceText;
    private EditText rpsChoiceText;
    private ImageView rpsImage;
    private Button rpsPlayButton;
    private Button goToButton;           // This button lets to Tic Tac Toe activity

    // set up preferences
    private SharedPreferences prefs;

    // if true, images are displayed for the computer's hand choices
    boolean showImages;

    private String imageName;
    private HashMap<String, Drawable> drawableMap = new HashMap<String, Drawable> (); // image to Drawable resources


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

        rpsChoiceText = (EditText) view.findViewById(R.id.rpsChoiceText);
        computerChoiceText = (TextView) view.findViewById(R.id.computerChoiceText);
        rpsPlayButton = (Button) view.findViewById(R.id.rpsPlayButton);
        rpsPlayButton.setOnClickListener(this);

        goToButton = (Button) view.findViewById(R.id.goToButton);
        goToButton.setOnClickListener(this);

        rpsImage = (ImageView) view.findViewById(R.id.rpsImage);

        drawableMap.put("rock", getResources().getDrawable(R.drawable.rock));
        drawableMap.put("paper", getResources().getDrawable(R.drawable.paper));
        drawableMap.put("scissors", getResources().getDrawable(R.drawable.scissors));

        return view;
    } // End onCreateView

    // Implement the interface for the listner
    @Override
    public void onClick(View v) {
        /*
        if(v.getId() == R.id.goToButton) {
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            startActivity(intent);
        } // End if statement
        if(v.getId() == R.id.rpsPlayButton) {
            play(v);
        }
        */
        switch (v.getId()) {
            case R.id.goToButton:
                goToPlay();
                break;
            case R.id.rpsPlayButton:
                playGame();
                break;
        }

    } // End onClick

    private void goToPlay() {
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        startActivity(intent);
    }

    // Menu & Settings
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // attempt to get the fragment
        SettingsFragment settingsFragment = (SettingsFragment) getFragmentManager().findFragmentById(R.id.settings_fragment);

        // if the fragment is null, display the appropriate menu
        if (settingsFragment == null) {
            inflater.inflate(R.menu.menu, menu);
        } else {
            inflater.inflate(R.menu.menu_twopane, menu);
        }
    } // End onCreateOptionsMenu

    // Buttons that works on the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // End switch
    } // End onOptionsItemSelected


    public void playGame() {

        // Close the soft keyboard
        //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        RockPaperScissors playerHand;
        // The user might enter an invalid choice, so catch it and propmt for the right choices
        try {
            playerHand = RockPaperScissors.valueOf(rpsChoiceText.getText().toString().toLowerCase());
        }
        catch(Exception e)
        {
            Toast.makeText(getActivity(), "Only enter rock, paper, or scissors!", Toast.LENGTH_LONG).show();
            return;
        }

        // Android makes a random hand choice and the winner is determined
        RockPaperScissors compHand = rpsGame.computerMove();
        computerChoiceText.setText(compHand.toString());
        if (showImages) {
            displayImage(compHand);
        }
        //winnerText.setText( rpsGame.whoWon(compHand, playerHand).toString());
    }

    private void displayImage(RockPaperScissors rps) {
        int id = 0;

        switch (rps) {
            case rock:
                id = R.drawable.rock;
                break;
            case paper:
                id = R.drawable.paper;
                break;
            case scissors:
                id = R.drawable.scissors;
                break;
        }
        rpsImage.setImageResource(id);
    }


        // To save values
    @Override
    public void onPause() {
        // save the instance variables
        SharedPreferences.Editor editor = prefs.edit();

        editor.commit();

        super.onPause();
    } // End onPause

    // To restore values
    @Override
    public void onResume() {
        super.onResume();

    } // End OnResume()

}
