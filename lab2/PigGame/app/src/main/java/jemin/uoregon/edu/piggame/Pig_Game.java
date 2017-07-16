package jemin.uoregon.edu.piggame;

import java.util.HashMap;
import java.util.Random;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;

public class Pig_Game extends Activity {

    // Declare variables for the widgets
    private static final int WINNING_SCORE = 100;
    private int firstPlayerScore = 0;
    private int secondPlayerScore = 0;
    private int totalTurnScore = 0;

    private boolean playerOneStartGame = true;          // Check if player 1 starts the game
    private boolean playerOneTurn = true;               // Check if player 1 turn
    private String imageName = "";

    // GUI views
    private TextView playerOneScore;
    private TextView playerTwoScore;
    private TextView pointDisplay;
    private TextView displayTurn;
    private ImageView diceImage;

    // GUI edit text
    private EditText playerOneText;
    private EditText playerTwoText;

    //GUI buttons
    private Button saveNameButton;
    private Button rollButton;
    private Button turnButton;
    private Button newGameButton;

    // image to Drawable resources
    private HashMap<String, Drawable> drawableMap = new HashMap<String, Drawable> ();
    private Random random;                                 // Random number for rolling dice


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig__game);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        pointDisplay = (TextView) findViewById(R.id.pointDisplay);
        displayTurn = (TextView) findViewById(R.id.displayTurn);
        playerOneText = (EditText) findViewById(R.id.playerOneText);
        playerTwoText = (EditText) findViewById(R.id.playerTwoText);

        diceImage = (ImageView) findViewById(R.id.diceImage);
        drawableMap.put("die1", getResources().getDrawable(R.drawable.die1));
        drawableMap.put("die2", getResources().getDrawable(R.drawable.die2));
        drawableMap.put("die3", getResources().getDrawable(R.drawable.die3));
        drawableMap.put("die4", getResources().getDrawable(R.drawable.die4));
        drawableMap.put("die5", getResources().getDrawable(R.drawable.die5));
        drawableMap.put("die6", getResources().getDrawable(R.drawable.die6));

        saveNameButton = (Button) findViewById(R.id.saveNameButton);
        rollButton = (Button) findViewById(R.id.rollButton);
        turnButton = (Button) findViewById(R.id.turnButton);
        newGameButton = (Button) findViewById(R.id.newGameButton);

        random = new Random();

        saveNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerOneTurn){
                    displayTurn.setText(playerOneText.getText().toString());
                } // End if statement
                else {
                    displayTurn.setText(playerTwoText.getText().toString());
                } // End else statement
            }
        });


        rollButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rollDie();
            }
        });

        turnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                turnPlayer();
            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newGame();
            }
        });
    } // End onCreate



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
        pointDisplay.setText(String.valueOf(newTotalScore));
    } // End updatePointDisplay

    private void setImage(final String newImage) {
        imageName = newImage;
        diceImage.setImageDrawable(drawableMap.get(imageName));
    } // End setImage

    private void rollDie() {
        int rollDie = random.nextInt(6) + 1;
        setImage("die" + rollDie);
        if(rollDie == 1) {
            updatePointDisplay(0);
            turnPlayer();
        } // End if statement
        else {
            updatePointDisplay(totalTurnScore + rollDie);
        } // End else statement
    } // End rollDie

    private void turnPlayer() {
        if(playerOneTurn) {
            displayTurn.setText(playerTwoText.getText().toString());
            updatePlayerOneScore(firstPlayerScore + totalTurnScore);
        } // End if statement
        else {
            displayTurn.setText(playerOneText.getText().toString());
            updatePlayerTwoScore(secondPlayerScore + totalTurnScore);
        } // End else statement
        updatePointDisplay(0);
        if(firstPlayerScore >= WINNING_SCORE || secondPlayerScore >= WINNING_SCORE) {
            gameEnd();
        } // End if statement
        else {
            turnChange();
        } // End else statement
    } // End turnPlayer

    // Reset everything for new game
    private void newGame() {
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePlayerOneScore(0);
                updatePlayerTwoScore(0);
                updatePointDisplay(0);
                displayTurn.setText("");
                playerOneText.setText("");
                playerTwoText.setText("");
                playerOneStartGame = !playerOneStartGame;
                playerOneTurn = playerOneStartGame;
                changeButtons();
                if(playerOneTurn) {
                    setImage("");
                } // End if statement
                else {
                    playerTwoTurn();
                } // End else statement
            }
        });
    } // End newGame

    private void turnChange() {
        playerOneTurn = !playerOneTurn;
        changeButtons();
        if(!playerOneTurn) {
            playerTwoTurn();
        } // End if statement

    } // End turnChange()

    private void changeButtons() {
        turnButton.setEnabled(playerOneTurn);
        rollButton.setEnabled(playerOneTurn);
    } // End changeButtons()


    private void gameEnd() {
        String message = (!playerOneTurn)
                ? String.format("Player 2 win! ")
                : String.format("Player 1 win! ");
        message += "";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.show();
    } // End gameEnd()


    private void playerTwoTurn() {
        rollButton.setEnabled(!playerOneTurn);
        turnButton.setEnabled(!playerOneTurn);
    } // End playerTwoTurn()

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
        changeButtons();
        if(firstPlayerScore >= WINNING_SCORE || secondPlayerScore >= WINNING_SCORE){
            gameEnd();
        } // End if statement
        else if (!playerOneTurn) {
            playerTwoTurn();
        } // End else if statement
    } // End onRestoreInstanceState


} // End Pig_Game class
