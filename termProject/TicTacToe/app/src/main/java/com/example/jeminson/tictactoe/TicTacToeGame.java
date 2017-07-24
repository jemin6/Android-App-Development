package com.example.jeminson.tictactoe;

/**
 * Created by jeminson on 2017. 7. 24..
 */


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeGame extends AppCompatActivity {

    private TicTacToeGameBoard gameBoard = null;
    private int moveCount = 0, xLoc = 0, yLoc = 0;
    private String mark = "X", compMark = "O";
    private boolean isOver = false;
    private TicTacToeComputerPlay comp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set XML view
        setContentView(R.layout.tttgame_activity);

        //Set up a new board and computer and assign the initial variables
        gameBoard = new TicTacToeGameBoard();
        comp = new TicTacToeComputerPlay(compMark);
    }

    public void resetClick(View v) {
        clear();
        if (compMark == "X") getComputerMove(gameBoard);

    }

    public void cellClick(View v) {
        //Get the id of the clicked object and assign it to a TextView variable
        TextView cell = (TextView) findViewById(v.getId());
        //Check the content and make sure the cell is empty and that the game isn't over
        String content = (String) cell.getText();
        if (content.equals("") && !isOver)
        {
            //Find the X,Y location values of the particular cell that was clicked
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

            }
            //Place mark on board at that point
            gameBoard.placeMark(xLoc, yLoc, mark);
            cell.setText(mark);
            //Increment move Count because a move was just made
            moveCount++;

            //Check to see if the game is over
            isOver = checkEnd(mark);

            //if the game isn't over, get the Computer's move
            if (!isOver)
                getComputerMove(gameBoard);
        }
    }
    private boolean checkEnd(String player)
    {
        //Checks the  board for a winner. If there's a winner, announce it with the correct player
        if (gameBoard.isWinner(player))
        {
            announce(true, player);
            return true;
        }

        //Check to see if the move count has maxed out, meaning it's a draw
        else if (moveCount >= 9)
        {
            announce(false, player);
            return true;
        }
        //If neither win or draw then the game isn't over
        return false;
    }
    private void announce(boolean endState, String player)
    {
        //Check for if it's a win or a draw. if it's a win, commend the respective player
        if (endState == true)
        {
            if (player.equals("X"))
            {
                player = "You win!";
            }
            else
            {
                player = "The computer wins!";
            }
        }
        //If not, then it's a draw
        else {
            player = "It's a draw!";
        }

        //Creates notification of winner
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, player, Toast.LENGTH_LONG);
        toast.show();
    }
    private void clear()
    {
        //Get a list of all TextView cells
        int[] idList = { R.id.cell11, R.id.cell12, R.id.cell13, R.id.cell21, R.id.cell22, R.id.cell23, R.id.cell31, R.id.cell32, R.id.cell33 };
        TextView cell;
        //For each cell clear the text with an empty string
        for (int item : idList)
        {
            cell = (TextView) findViewById(item);
            cell.setText("");
        }
        //Reset the game state and clear the virtual board
        isOver = false;
        moveCount = 0;
        gameBoard.clear();
    }
    private void getComputerMove(TicTacToeGameBoard gameBoard)
    {
        //Send the board to the computer for it to determine and return the move in an array {x,y}
        int[] move = comp.move(gameBoard,compMark);
        TextView cell = null;
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

                }
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

                }
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

                }
                break;

        }
        //Make sure there's nothing already in the cell
        //then place the mark with the ai's Mark, increment move count
        //and check to see if the game's over
        if (cell != null && cell.getText() == "")
        {
            gameBoard.placeMark(move[0], move[1], compMark);
            System.out.println(cell);
            cell.setText(compMark);
            moveCount++;
            isOver = checkEnd(compMark);

        }
    }
}