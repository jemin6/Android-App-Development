package com.example.jeminson.tictactoe;

import java.util.Random;

/**
 * Created by jeminson on 2017. 7. 22..
 */

public class RockPaperScissorsGame {

    //Instance Variables
    private Random random = new Random();

    // Computer and human moves need to be made before calling this method
    public Winner whoWon(RockPaperScissors computerHand, RockPaperScissors playerHand)
    {
        Winner win;
        if (computerHand == playerHand)
            win = Winner.tie;
        else if ((computerHand == RockPaperScissors.rock && playerHand == RockPaperScissors.scissors) ||
                (computerHand == RockPaperScissors.paper && playerHand == RockPaperScissors.rock) ||
                (computerHand == RockPaperScissors.scissors && playerHand == RockPaperScissors.paper)) {
            win = Winner.computer;

        }
        else {
            win = Winner.player;
        }
        return win;
    }

    // This generates a random move for the computer
    public RockPaperScissors computerMove()
    {
        return RockPaperScissors.values()[random.nextInt(3)];
    }

}
