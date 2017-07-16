package jemin.uoregon.edu.pigver2_3;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


import java.util.HashMap;
import java.util.Random;

public class PigGameActivity extends AppCompatActivity
implements OnEditorActionListener, OnClickListener {

    private static final int WINNING_SCORE = 100;
    private int firstPlayerScore = 0;
    private int secondPlayerScore = 0;
    private int totalTurnScore = 0;

    // Declare variables for the widgets
    private EditText playerOneText;
    private EditText playerTwoText;

    private TextView displayName;
    private TextView displayPoint;
    private TextView playerOneScore;
    private TextView playerTwoScore;

    private Button rollButton;
    private Button endTurnButton;
    private Button newGameButton;

    private ImageView diceImage;

    private String imageName;

    private HashMap<String, Drawable> drawableMap = new HashMap<String, Drawable> (); // image to Drawable resources
    private Random random;                                 // Random number for rolling dice

    private boolean playerOneTurn = true;  // Check if it is player 1's turn
    private boolean playerOneStartGame = true;  // Check if player 1 starts the game
    private boolean setName = "";            // Setting preference

    // define the SharedPreferences object
    private SharedPreferences savedValues;

    // define instance variables that should be saved
    private String firstPlayerName = "";
    private String secondPlayerName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig_game);

        // Get references to the widgets
        playerOneText = (EditText) findViewById(R.id.playerOneText);
        playerTwoText = (EditText) findViewById(R.id.playerTwoText);

        displayName = (TextView) findViewById(R.id.displayName);
        displayPoint = (TextView) findViewById(R.id.displayPoint);
        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);

        rollButton = (Button) findViewById(R.id.rollButton);
        endTurnButton = (Button) findViewById(R.id.endTurnButton);
        newGameButton = (Button) findViewById(R.id.newGameButton);

        diceImage = (ImageView) findViewById(R.id.diceImage);

        drawableMap.put("die1", getResources().getDrawable(R.drawable.die1));
        drawableMap.put("die2", getResources().getDrawable(R.drawable.die2));
        drawableMap.put("die3", getResources().getDrawable(R.drawable.die3));
        drawableMap.put("die4", getResources().getDrawable(R.drawable.die4));
        drawableMap.put("die5", getResources().getDrawable(R.drawable.die5));
        drawableMap.put("die6", getResources().getDrawable(R.drawable.die6));

        // Set the listener
        playerOneText.setOnEditorActionListener(this);
        playerTwoText.setOnEditorActionListener(this);
        rollButton.setOnClickListener(this);
        endTurnButton.setOnClickListener(this);
        newGameButton.setOnClickListener(this);

        random = new Random();

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // get default SharedPreferences object
        savedValues = PreferenceManager.getDefaultSharedPreferences(this);

    } // End onCreate

    // To save values
    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("firstPlayerName", firstPlayerName);
        editor.putString("secondPlayerName", secondPlayerName);
        editor.putInt("firstPlayerScore", firstPlayerScore);
        editor.putInt("secondPlayerScore", secondPlayerScore);
        editor.putInt("totalTurnScore", totalTurnScore);
        editor.putBoolean("playerOneStartGame", playerOneStartGame);
        editor.putBoolean("playerOneTurn", playerOneTurn);
        editor.putString("imageName",imageName);
        editor.commit();

        super.onPause();
    } // End onPause

    // To restore values
    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        firstPlayerScore = savedValues.getInt("firstPlayerScore", 0);
        secondPlayerScore = savedValues.getInt("secondPlayerScore", 0);
        totalTurnScore = savedValues.getInt("totalTurnScore", 0);
        imageName = savedValues.getString("imageName","");
        playerOneStartGame = savedValues.getBoolean("playerOneStartGame", true);
        setName = savedValues.getBoolean("check_box_set_player_name", true);

        if(setName) {
            firstPlayerName = savedValues.getString("edit_text_set_player_one_name","");
            playerOneText.setText(firstPlayerName);
            secondPlayerName = savedValues.getString("edit_text_set_player_two_name","");
            playerTwoText.setText(secondPlayerName);
        } // End if statement
        else {
            firstPlayerName = savedValues.getString("firstPlayerName","");
            playerOneText.setText(firstPlayerName);
            secondPlayerName = savedValues.getString("secondPlayerName","");
            playerTwoText.setText(secondPlayerName);
        } // End else statement

        playerOneScore.setText(String.valueOf(firstPlayerScore));
        playerTwoScore.setText(String.valueOf(secondPlayerScore));
        displayPoint.setText(String.valueOf(totalTurnScore));

        diceImage.setImageDrawable(drawableMap.get(imageName));

        if(firstPlayerScore >= WINNING_SCORE || secondPlayerScore >= WINNING_SCORE){
            gameEnd();
        } // End if statement
        else {
            turnChange();
        } // End else statement

    } // End onResume

    private void setImage(final String newImage) {
        imageName = newImage;
        diceImage.setImageDrawable(drawableMap.get(imageName));
    } // End setImage

    private void updatePlayerOneScore(final int newScore) {
        firstPlayerScore = newScore;
        playerOneScore.setText(String.valueOf(newScore));
    } // End updatePlayerOneScore

    private void updatePlayerTwoScore(final int newScore) {
        secondPlayerScore = newScore;
        playerTwoScore.setText(String.valueOf(newScore));
    } // End updatePlayerTwoScore

    private void updatePointDisplay(final int newTotalScore) {
        totalTurnScore = newTotalScore;
        displayPoint.setText(String.valueOf(newTotalScore));
    } // End updatePointDisplay

    // Implement the interface for the listner
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.rollButton:
                rollDie();
                break;
            case R.id.endTurnButton:
                endTurn();
                break;
            case R.id.newGameButton:
                newGame();
                break;
        } // End switch statement
    } // End onClick

    private void rollDie() {
        int rollDie = random.nextInt(6) + 1;
        setImage("die" + rollDie);
        if(rollDie == 1) {
            updatePointDisplay(0);
            turnChange();
        } // End if statement
        else {
            updatePointDisplay(totalTurnScore + rollDie);
        } // End else statement
    } // End rollDie

    private void endTurn() {
        if(playerOneTurn) {
            displayName.setText(playerTwoText.getText().toString());
            updatePlayerOneScore(firstPlayerScore + totalTurnScore);
        } // End if statement
        else {
            displayName.setText(playerOneText.getText().toString());
            updatePlayerTwoScore(secondPlayerScore + totalTurnScore);
        } // End else statement
        updatePointDisplay(0);
        if(firstPlayerScore >= WINNING_SCORE || secondPlayerScore >= WINNING_SCORE) {
            gameEnd();
        } // End if statement
        else {
            turnChange();
        } // End else statement
    } // End endTurn()

    // Reset everything for new game
    private void newGame() {
        updatePlayerOneScore(0);
        updatePlayerTwoScore(0);
        updatePointDisplay(0);
        displayName.setText("");
        playerOneTurn = playerOneStartGame;
        rollButton.setEnabled(true);
        endTurnButton.setEnabled(true);
        setImage("");
    } // End newGame

    private void turnChange() {

        playerOneTurn = !playerOneTurn;
        if(playerOneTurn) {
            displayName.setText(playerOneText.getText().toString());
        }
        else {
            displayName.setText(playerTwoText.getText().toString());
        }

    } // End turnChange()

    public void gameEnd() {
        if(firstPlayerScore >= WINNING_SCORE) {
            Toast.makeText(this, "Player 1 win!", Toast.LENGTH_SHORT).show();
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);
        }
        else {
            Toast.makeText(this, "Player 2 win!", Toast.LENGTH_SHORT).show();
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);
        }
    }

    // Implement the listener
    @Override
    public boolean onEditorAction (TextView v, int actionId, KeyEvent event) {

        if(actionId == EditorInfo.IME_ACTION_DONE || actionId ==  EditorInfo.IME_ACTION_UNSPECIFIED) {
            if(playerOneTurn) {
                displayName.setText(playerOneText.getText().toString());
            } // End if statement
            else {
                displayName.setText(playerTwoText.getText().toString());
            } // End else statement
        } // End if statement
        return false;
    } // End onEdiorAction

    // Setting up the menu function on the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    } // End onCreateOptionsMenu

    // When clicking the each menu button
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.menu_about:
                Toast.makeText(this, "This is toast for the About menu", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // End switch statement
    } // End onOptionsItemSelected

} // End PigGameActivity clas
