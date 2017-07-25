package com.example.jeminson.tictactoe;

/**
 * Created by jeminson on 2017. 7. 24..
 */


import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TicTacToeGame extends AppCompatActivity {

    private TicTacToeGameBoard gameBoard = null;
    private int moveCount = 0;
    private int xLoc = 0;
    private int yLoc = 0;
    private int countWin = 0;

    private String playerMark = "X";
    private String computerMark = "O";
    private String content;

    private boolean isOver = false;

    private TextView cell;

    private TicTacToeComputerPlay computer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set XML view
        setContentView(R.layout.tttgame_activity);

        //Set up for initial variables
        gameBoard = new TicTacToeGameBoard();
        computer = new TicTacToeComputerPlay(computerMark);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    public void resetClick(View v) {
        clearBoard();
        //countWin = 0;
        if (computerMark == "X") {
            getComputerMove(gameBoard);
        } // End if
    }


    public void cellClick(View v) {
        //Get the id of the clicked object
        cell = (TextView) findViewById(v.getId());
        //Check the content and make sure the cell is empty and that the game isn't over
        content = (String) cell.getText();
        if (content.equals("") && !isOver)
        {
            //Find the X,Y location when clicked
            switch (cell.getId()) {

                case R.id.cell11:
                    xLoc = 0;
                    yLoc = 0;
                    break;
                case R.id.cell12:
                    xLoc = 0;
                    yLoc = 1;
                    break;
                case R.id.cell13:
                    xLoc = 0;
                    yLoc = 2;
                    break;
                case R.id.cell21:
                    xLoc = 1;
                    yLoc = 0;
                    break;
                case R.id.cell22:
                    xLoc = 1;
                    yLoc = 1;
                    break;
                case R.id.cell23:
                    xLoc = 1;
                    yLoc = 2;
                    break;
                case R.id.cell31:
                    xLoc = 2;
                    yLoc = 0;
                    break;
                case R.id.cell32:
                    xLoc = 2;
                    yLoc = 1;
                    break;
                case R.id.cell33:
                    xLoc = 2;
                    yLoc = 2;
                    break;

            } // End switch statement
            //Place mark on board at that point
            gameBoard.placeMark(xLoc, yLoc, playerMark);
            cell.setText(playerMark);
            //Increment move Count because a move was just made
            moveCount++;

            //Check to see if the game is over
            isOver = checkEnd(playerMark);

            //if the game isn't over, get the Computer's move
            if (!isOver) {
                getComputerMove(gameBoard);
            } // End nested if statement
        } // End if statement
    }

    private boolean checkEnd(String player) {
        //Checks if player is the winner
        if (gameBoard.isWinner(player)) {
            announce(true, player);
            return true;
        } // End if
        //it's a draw
        else if (moveCount >= 9) {
            announce(false, player);
            return true;
        }
        //If neither win or draw then the game isn't over
        return false;
    }
    private void announce(boolean endState, String player) {
        if (endState == true) {
            if (player.equals("X")) {
                countWin++;
                player = "Player Won! Total win count is: " + countWin;
            } // End nested if
            else {
                player = "Computer Won!";
            } // End nested else
        } // End if
        //If not, then it's a draw
        else {
            player = "Draw!";
        } // End else

        //Creates notification of winner
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, player, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void clearBoard() {
        //Get a list of all the cells
        int[] idList = { R.id.cell11, R.id.cell12, R.id.cell13,
                         R.id.cell21, R.id.cell22, R.id.cell23,
                         R.id.cell31, R.id.cell32, R.id.cell33 };

        for (int item : idList) {
            cell = (TextView) findViewById(item);
            cell.setText("");
        } // End for loop

        //Reset and clear the board
        isOver = false;
        moveCount = 0;
        gameBoard.clearBoard();
    }

    private void getComputerMove(TicTacToeGameBoard gameBoard) {
        //Send the board to the computer
        int[] move = computer.move(gameBoard,computerMark);
        cell = null;
        //Determine the right cell to use by id first go to the right row then the right column
        switch (move[0]) {
            case 0:
                switch (move[1]) {
                    case 0:
                        cell = (TextView) findViewById(R.id.cell11);
                        break;
                    case 1:
                        cell = (TextView) findViewById(R.id.cell12);
                        break;
                    case 2:
                        cell = (TextView) findViewById(R.id.cell13);
                        break;
                } // End nested switch statement
                break;
            case 1:
                switch (move[1]) {
                    case 0:
                        cell = (TextView) findViewById(R.id.cell21);
                        break;
                    case 1:
                        cell = (TextView) findViewById(R.id.cell22);
                        break;
                    case 2:
                        cell = (TextView) findViewById(R.id.cell23);
                        break;
                } // End nested switch
                break;
            case 2:
                switch (move[1]) {
                    case 0:
                        cell = (TextView) findViewById(R.id.cell31);
                        break;
                    case 1:
                        cell = (TextView) findViewById(R.id.cell32);
                        break;
                    case 2:
                        cell = (TextView) findViewById(R.id.cell33);
                        break;
                } // End nested switch
                break;
        } // End switch statement

        if (cell != null && cell.getText() == "") {
            gameBoard.placeMark(move[0], move[1], computerMark);
            System.out.println(cell);
            cell.setText(computerMark);
            moveCount++;
            isOver = checkEnd(computerMark);
        } // End if statement
    }



}