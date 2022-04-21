package com.hufftech;

import com.hufftech.gameplay.MemorizationGamePlayer;
import com.hufftech.memorization.BlankingMemorizationGame;
import com.hufftech.memorization.MemorizationGame;
import com.hufftech.passage.Passage;
import com.hufftech.passage.StringPassage;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.print("Enter the passage to memorize: ");
        String input = s.nextLine();

        Passage myPassage = new StringPassage(input);
        BlankingMemorizationGame myGame = new BlankingMemorizationGame(myPassage);
        MemorizationGamePlayer player = new MemorizationGamePlayer(myGame);

        while (!myGame.fullyBlanked()) {
            System.out.println("Turn " + player.getTurn());
            System.out.println(myGame);
            System.out.println();
            System.out.print(">>> ");

            input = s.nextLine();
            boolean result = player.next(input);

            if (result) {
                System.out.println("Hooray!!");
            }
            else {
                System.out.println("Ohhh...");
            }

            System.out.println();
        }

        System.out.println("Statistics: ");
        System.out.println("\tTurns taken: " + player.getTurn());
        System.out.println("\tSuccesses: " + player.getSuccesses());
        System.out.println("\tFails: " + player.getFails());
    }
}
