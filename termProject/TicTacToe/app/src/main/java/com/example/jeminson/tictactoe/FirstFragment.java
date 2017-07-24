package com.example.jeminson.tictactoe;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by jeminson on 2017. 7. 21..
 */

public class FirstFragment extends Fragment implements OnClickListener {

    RockPaperScissorsGame rpsGame = new RockPaperScissorsGame();

    private TextView playerName;
    private TextView computerChoiceText;
    private TextView winnerText;
    private EditText rpsChoiceText;
    private ImageView rpsImage;
    private Button rpsPlayButton;
    private Button goToButton;           // This button lets to Tic Tac Toe activity

    // set up preferences
    private SharedPreferences prefs;

    // if true, images are displayed for the computer's hand choices
    boolean showImages;

    private String playerNameText = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        // get the default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // turn on the options menu
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_fragment, container, false);

        rpsChoiceText = (EditText) view.findViewById(R.id.rpsChoiceText);
        playerName = (TextView) view.findViewById(R.id.playerName);
        computerChoiceText = (TextView) view.findViewById(R.id.computerChoiceText);
        winnerText = (TextView) view.findViewById(R.id.winnerText);
        rpsPlayButton = (Button) view.findViewById(R.id.rpsPlayButton);
        rpsPlayButton.setOnClickListener(this);

        goToButton = (Button) view.findViewById(R.id.goToButton);
        goToButton.setOnClickListener(this);

        rpsImage = (ImageView) view.findViewById(R.id.rpsImage);


        return view;
    } // End onCreateView

    // Implement the interface
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToButton:
                goToPlay();
                break;
            case R.id.rpsPlayButton:
                String check_text = rpsChoiceText.getText().toString().trim();
                if(check_text.isEmpty() || check_text.length() == 0 || check_text.equals("")){
                    Toast.makeText(getActivity(), "You did not enter rock, paper, or scissor", Toast.LENGTH_SHORT).show();
                    computerChoiceText.setText("");
                    winnerText.setText("");
                    goToButton.setEnabled(false);
                } else {
                    playGame();
                    rpsPlayButton.setEnabled(false);
                    goToButton.setEnabled(true);
                }
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
        winnerText.setText(rpsGame.whoWon(compHand, playerHand).toString());
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
        editor.putString("playerNameText", playerNameText);
        //editor.putString("rpsWinner",rpsWinner);
        editor.commit();

        super.onPause();
    } // End onPause

    // To restore values
    @Override
    public void onResume() {
        super.onResume();

        showImages = prefs.getBoolean(getResources().getString(R.string.show_images), true);

        if (!showImages)
            rpsImage.setVisibility(View.GONE);
        else
            rpsImage.setVisibility(View.VISIBLE);

        playerNameText = prefs.getString("edit_text_set_player_name", "");
        playerName.setText(playerNameText);
        //rpsWinner = prefs.getString("winner_text","");
        //winnerText.setText(rpsWinner);

    } // End OnResume()

}
