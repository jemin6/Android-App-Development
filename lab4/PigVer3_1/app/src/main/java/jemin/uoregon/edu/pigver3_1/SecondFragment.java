package jemin.uoregon.edu.pigver3_1;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Je on 7/12/2017.
 */

public class SecondFragment extends Fragment implements OnClickListener {

    private static final int WINNING_SCORE = 100;
    private int firstPlayerScore = 0;
    private int secondPlayerScore = 0;
    private int totalTurnScore = 0;

    private TextView displayName;
    private TextView displayPoint;
    private TextView playerOneScore;
    private TextView playerTwoScore;
    private TextView playerOneName;
    private TextView playerTwoName;

    private Button rollButton;
    private Button endTurnButton;
    private Button newGameButton;

    private ImageView diceImage;

    private String imageName;

    private HashMap<String, Drawable> drawableMap = new HashMap<String, Drawable> (); // image to Drawable resources
    private Random random;                                 // Random number for rolling dice

    private boolean playerOneTurn = true;  // Check if it is player 1's turn
    private boolean playerOneStartGame = true;  // Check if player 1 starts the game

    // set up preferences
    private SharedPreferences prefs;

    // define instance variables that should be saved
    private String firstPlayerName = "";
    private String secondPlayerName = "";

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

        // get references to the widgets
        displayName = (TextView) view.findViewById(R.id.displayName);
        displayPoint = (TextView) view.findViewById(R.id.displayPoint);
        playerOneScore = (TextView) view.findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) view.findViewById(R.id.playerTwoScore);
        playerOneName = (TextView) view.findViewById(R.id.playerOneName);
        playerTwoName = (TextView) view.findViewById(R.id.playerTwoName);

        rollButton = (Button) view.findViewById(R.id.rollButton);
        endTurnButton = (Button) view.findViewById(R.id.endTurnButton);
        newGameButton = (Button) view.findViewById(R.id.newGameButton);

        diceImage = (ImageView) view.findViewById(R.id.diceImage);

        drawableMap.put("die1", getResources().getDrawable(R.drawable.die1));
        drawableMap.put("die2", getResources().getDrawable(R.drawable.die2));
        drawableMap.put("die3", getResources().getDrawable(R.drawable.die3));
        drawableMap.put("die4", getResources().getDrawable(R.drawable.die4));
        drawableMap.put("die5", getResources().getDrawable(R.drawable.die5));
        drawableMap.put("die6", getResources().getDrawable(R.drawable.die6));

        random = new Random();

        rollButton.setOnClickListener(this);
        endTurnButton.setOnClickListener(this);
        newGameButton.setOnClickListener(this);

        return view;
    } // End onCreateView

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
            displayName.setText(playerTwoName.getText().toString());
            updatePlayerOneScore(firstPlayerScore + totalTurnScore);
        } // End if statement
        else {
            displayName.setText(playerOneName.getText().toString());
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
        displayName.setText(playerOneName.getText().toString());
        playerOneTurn = playerOneStartGame;
        rollButton.setEnabled(true);
        endTurnButton.setEnabled(true);
        setImage("");
    } // End newGame

    private void turnChange() {

        playerOneTurn = !playerOneTurn;
        if(playerOneTurn) {
            displayName.setText(playerOneName.getText().toString());
        } //End if
        else {
            displayName.setText(playerTwoName.getText().toString());
        } // End else
    } // End turnChange()

    public void gameEnd() {
        if(firstPlayerScore >= WINNING_SCORE) {
            Toast.makeText(getActivity(), "Player 1 win!", Toast.LENGTH_SHORT).show();
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);
        } // End if statement
        else {
            Toast.makeText(getActivity(), "Player 2 win!", Toast.LENGTH_SHORT).show();
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);
        } // End else statement
    } // End gameEnd()

    // To save values
    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = prefs.edit();
        editor.putString("firstPlayerName", firstPlayerName);
        editor.putString("secondPlayerName", secondPlayerName);
        editor.putInt("firstPlayerScore", firstPlayerScore);
        editor.putInt("secondPlayerScore", secondPlayerScore);
        editor.putInt("totalTurnScore", totalTurnScore);
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
        firstPlayerScore = prefs.getInt("firstPlayerScore", 0);
        secondPlayerScore = prefs.getInt("secondPlayerScore", 0);
        totalTurnScore = prefs.getInt("totalTurnScore", 0);
        imageName = prefs.getString("imageName","");
        playerOneTurn = prefs.getBoolean("playerOneTurn",true);

        firstPlayerName = prefs.getString("edit_text_set_player_one_name","");
        playerOneName.setText(firstPlayerName);
        secondPlayerName = prefs.getString("edit_text_set_player_two_name","");
        playerTwoName.setText(secondPlayerName);

        playerOneScore.setText(String.valueOf(firstPlayerScore));
        playerTwoScore.setText(String.valueOf(secondPlayerScore));
        displayPoint.setText(String.valueOf(totalTurnScore));

        diceImage.setImageDrawable(drawableMap.get(imageName));

        if(playerOneTurn) {
            displayName.setText(playerOneName.getText().toString());
        } //End if
        else {
            displayName.setText(playerTwoName.getText().toString());
        } // End else

    } // End OnResume()

} // End SecondFragment
