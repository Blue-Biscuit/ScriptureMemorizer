package net.ahuffman.cli;

import net.ahuffman.memorization.BlankingMemorizationGame;
import net.ahuffman.memorization.FreeRecallMemorizationGame;
import net.ahuffman.memorization.MemorizationGame;
import net.ahuffman.passage.Passage;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Command which plays the blanking game.
 */
public class LearnCommand extends Command {
    private static final String NAME = "learn";
    private static final String SYNTAX = "<game> <passage name>";
    private static final String HELP = "plays a learning game with the passage. If no games are provided, the options will be listed.";

    public LearnCommand() {
        super(NAME, SYNTAX, HELP);
    }

    @Override
    public String helpText() {
        return String.format("%s\nPossible games are blank, rote.", super.helpText());
    }

    @Override
    public Object execute(CommandArgs args, Object[] input) throws InvalidCommandOperationException {

        // Algorithm:
        // 1. Argument checking. If the input list is not a print writer, scanner, and the passage list,
        // throw an internal error.
        // 2. If no arguments are given, print help.
        // 3. If one argument is given, throw.
        // 4. Otherwise, play the given game.
            // a. Load the passage.
            // b. Load the game.
            // c. Play the game.

        // 1. Argument checking. If the input list is not a print writer, scanner, and the passage list,
        // throw an internal error.

        if (input.length < 3 || !(input[0] instanceof PassagesList list) || !(input[1] instanceof PrintStream writer)
            || !(input[2] instanceof Scanner in)) {
            throw new InvalidCommandOperationException("Internal error.");
        }

        // 2. If no arguments are given, print help.
        // 3. If one argument is given, throw.

        else if (args.isEmpty() || args.size() == 1) {
            throw new InvalidCommandOperationException(this);
        }

        // 4. Otherwise, play the given game.

        else {
            MemorizationGame game;
            Passage toLearn = null;

            // a. Load the passage.

            for (Passage e : list) {
                if (e.getTitle().equals(args.get(1))) {
                    toLearn = e;
                    break;
                }
            }
            if (toLearn == null) {
                throw new InvalidCommandOperationException( String.format("Passage entitled \"%s\" not found.", args.get(1)) );
            }

            // b. Load the game.

            String gameRequested = args.get(0);
            if (gameRequested.equals("blank")) {
                game = new BlankingMemorizationGame(toLearn);
            }
            else if (gameRequested.equals("rote")) {
                game = new FreeRecallMemorizationGame(toLearn);
            }
            else {
                throw new InvalidCommandOperationException(this);
            }

            // c. Play the game.

            while (!game.done()) {
                writer.println(game);
                writer.print(">> ");
                String userIn = in.nextLine();

                if (game.matches(userIn)) {
                    writer.println("Correct!\n");
                    game.next();
                }
                else {
                    writer.println("Incorrect.\n");
                    writer.printf("Correct text: \"%s\"\n\n", toLearn.fullText());
                }
            }
        }

        return null;
    }
}
