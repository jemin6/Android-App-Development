package jemin.uoregon.edu.pigver2_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Random;

public class PigGameVer2_2 extends AppCompatActivity {

    private static final int WINNING_SCORE = 10;
    private int firstPlayerScore = 0;
    private int secondPlayerScore = 0;
    private int totalTurnScore = 0;

    // define variables for the widgets
    private TextView displayName;
    private TextView displayPoint;
    private TextView playerOneScore;
    private TextView playerTwoScore;

    private EditText playerOneText;
    private EditText playerTwoText;

    private Button playerNameSaveButton;
    private Button playerNameSaveButton2;
    private Button rollButton;
    private Button endTurnButton;
    private Button newGameButton;

    private ImageView diceImage;

    private String imageName;
    private String firstPlayerName;
    private String secondPlayerName;

    private SharedPreferences setting;        // set up preferences

    private HashMap<String, Drawable> drawableMap = new HashMap<String, Drawable> (); // image to Drawable resources
    private Random random;                                 // Random number for rolling dice

    private boolean playerOneTurn = true;  // Check if it is player 1's turn
    private boolean playerOneStartGame = true;  // Check if player 1 starts the game
    private boolean rememberWinScore = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig_game_ver2_2);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        displayName = (TextView) findViewById(R.id.displayName);
        displayPoint = (TextView) findViewById(R.id.displayPoint);

        playerOneText = (EditText) findViewById(R.id.playerOneText);
        playerTwoText = (EditText) findViewById(R.id.playerTwoText);

        playerNameSaveButton = (Button) findViewById(R.id.playerNameSaveButton);
        playerNameSaveButton2 = (Button) findViewById(R.id.playerNameSaveButton2);
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

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // get default SharedPreferences object
        setting = PreferenceManager.getDefaultSharedPreferences(this);

        random = new Random();

        rollButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rollDie();
            }
        });
        endTurnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endTurn();
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newGame();
            }
        });

        // Button that save name for player 1
        playerNameSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerOneTurn){
                    displayName.setText(playerOneText.getText().toString());
                } // End if statement
            }
        });

        // Button that save name for player 2
        playerNameSaveButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!playerOneTurn){
                    displayName.setText(playerTwoText.getText().toString());
                }
            }
        });

    } // End onCreate

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

    // Setting up the menu function on the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

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
    } // End turnChange()

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("firstPlayerScore", firstPlayerScore);
        outState.putInt("secondPlayerScore", secondPlayerScore);
        outState.putInt("totalTurnScore", totalTurnScore);
        outState.putBoolean("playerOneStartGame", playerOneStartGame);
        outState.putBoolean("playerOneTurn", playerOneTurn);
        outState.putString("imageName",imageName);
    } // End onSaveInstanceState

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        updatePlayerOneScore(savedInstanceState.getInt("firstPlayerScore", 0));
        updatePlayerTwoScore(savedInstanceState.getInt("secondPlayerScore", 0));
        updatePointDisplay(savedInstanceState.getInt("totalTurnScore", 0));
        setImage(savedInstanceState.getString("imageName"));
        playerOneStartGame = savedInstanceState.getBoolean("playerOneStartGame", true);
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn", true);
        if(firstPlayerScore >= WINNING_SCORE || secondPlayerScore >= WINNING_SCORE){
            gameEnd();
        } // End if statement
        else {
            turnChange();
        } // End else statement
    } // End onRestoreInstanceState

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


    @Override
    public void onResume() {
        super.onResume();

        firstPlayerName = setting.getString("edit_text_set_player_one_name","");
        playerOneText.setText(firstPlayerName);
        secondPlayerName = setting.getString("edit_text_set_player_two_name","");
        playerTwoText.setText(secondPlayerName);
        rememberWinScore = setting.getBoolean("check_box_remember_win_score", true);
    } // End onResume()

} // public class End PigGameVer2_2
