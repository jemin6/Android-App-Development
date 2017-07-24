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

    //Computer will find random spot recursively until it finds an empty spot
    private int[] computerMoves() {
        int column;
        int row;
        row = (int)((Math.random() * 3));
        column = (int)(Math.random() * 3);
        if (gameBoard[row][column] != null && gameBoard[row][column].equals(""))
        {
            gameBoard[row][column]="O";
            return new int[]{row, column};
        }
        else
        {
            return computerMoves();
        }
    }

}
