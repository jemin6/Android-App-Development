package com.example.jeminson.tictactoe;

/**
 * Created by jeminson on 2017. 7. 23..
 */

public class TicTacToeComputerPlay {
    protected String playerMark;
    protected String computerMakr;
    protected String[][] gameBoard;

    TicTacToeComputerPlay(String marker) {
        this.playerMark = "X";
        this.computerMakr ="O";
    }

    public int[] move(TicTacToeGameBoard gameBoard, String marker) {
        this.playerMark = "X";
        this.computerMakr = "O";
        this.gameBoard = gameBoard.getBoard();
        int[] result = computerMoves();
        return new int[] { result[0], result[1] };
    }

    //Computer finds random spot recursively
    private int[] computerMoves() {
        int column = (int)(Math.random() * 3);
        int row = (int)((Math.random() * 3));
        if (gameBoard[row][column] != null && gameBoard[row][column].equals(""))
        {
            gameBoard[row][column]="O";
            return new int[]{row, column};
        } // End if statement
        else
        {
            return computerMoves();
        } // End else statement
    } // End computerMoves

} // End TicTacToeComputerPlay
