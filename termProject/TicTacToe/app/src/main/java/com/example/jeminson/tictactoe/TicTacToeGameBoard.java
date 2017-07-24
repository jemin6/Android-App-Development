package com.example.jeminson.tictactoe;

/**
 * Created by jeminson on 2017. 7. 23..
 */

public class TicTacToeGameBoard {

    private String[][] board = new String[3][3];
    int x, y;
    int i;

    //Initiate the game board with blanks
    TicTacToeGameBoard() {
        for (x = 0; x < 3; x++) {

            for (y = 0; y < 3; y++) {
                board[x][y] = "";
            } // End nested for loop
        } // End for loop
    } // End TicTacToeGameBoard

    //Clear the board
    public void clearBoard() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board[x][y] = "";
            } // End nested for loop
        } // End for loop
    }

    //Return the current board
    public String[][] getBoard() {
        return board;
    }

    public void placeMark(int xLoc, int yLoc, String mark)
    {
        if (board[xLoc][yLoc] == "") {
            board[xLoc][yLoc] = mark;
        }
    }
    //Check to see if there is a winner
    public boolean isWinner(String player) {
        // Check Diagonals
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) &&
                !board[0][0].equals("") && board[0][0].equals(player)) {
            return true;
        } // End if statement
        if (board[2][0].equals(board[1][1]) && board[2][0].equals(board[0][2]) &&
                !board[2][0].equals("") && board[2][0].equals(player)) {
            return true;
        } // End if statement
        for (i = 0; i < 3; i++) {
            // Check Rows
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) &&
                    board[i][0].equals("") && board[i][0].equals(player)) {
                return true;
            } // End nested if
            // Check Columns
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) &&
                    !board[0][i].equals("") && board[0][i].equals(player)) {
                return true;
            } // End nested if
        } // End for loop
        return false;
    }
}
