package net.ahuffman.cli;

import net.ahuffman.common.StringHelpers;
import net.ahuffman.memorization.BlankingMemorizationGame;
import net.ahuffman.memorization.FreeRecallMemorizationGame;
import net.ahuffman.memorization.MemorizationGame;
import net.ahuffman.passage.Passage;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Command which plays the blanking game.
 */
public class LearnCommand extends Command {
    private static final String NAME = "learn";
    private static final String SYNTAX = "<game> <passage name>";
    private static final String HELP = "plays a learning game with the passage. If no games are provided, the options will be listed.\nTo play in a series over a specific tag, format like this: learn <game> tag <tag name>";

    public LearnCommand() {
        super(NAME, SYNTAX, HELP);
    }

    @Override
    public String helpText() {
        return String.format("%s\nPossible games are blank, rote.", super.helpText());
    }

    private static void cls(PrintStream writer) {
        if (writer == System.out) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            catch (IOException | InterruptedException ignored) {

            }
        }
    }

    private static void delay() {
        final int NUM_SECONDS = 3;

        try {
            TimeUnit.SECONDS.sleep(NUM_SECONDS);
        }
        catch (Exception ignored) {

        }
    }

    @Override
    public Object execute(CommandArgs args, Object[] input) throws InvalidCommandOperationException {
        final int SNIPPET_BEFORE_AFTER = 15;
        final int NUM_BLANK;

        // Algorithm:
        // 1. Argument checking. If the input list is not a print writer, scanner, and the passage list,
        // throw an internal error.
        // 2. If no arguments are given, print help.
        // 3. If one argument is given, throw.
        // 4. If the user is playing by tag...
            // a. Determine the tag, and fetch the in-order list of passages with such a tag.
            // c. Recurse on each passage, calling by name.
        // 5. If the user is playing by a specific passage name...
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

        else if (args.size() < 1) {
            throw new InvalidCommandOperationException(this);
        }

        // 4. If the user is playing by tag...

        if (args.get(1).equals("tag")) {
            String tag;
            String game;
            PassagesList passagesOfTag;

            // a. Determine the tag, and fetch the in-order list of passages with such a tag.

            // Must include tag name along with command.
            if (args.size() < 3) {
                throw new InvalidCommandOperationException(this);
            }

            tag = args.get(2);
            game = args.get(0);

            passagesOfTag = list.byTag(tag);

            // b. Recurse on each passage, calling by name.

            if (passagesOfTag.size() == 0) {
                throw new InvalidCommandOperationException("No passages of the given tag.");
            }

            passagesOfTag.forEach(e -> {
                execute(new CommandArgs(String.format("%s \"%s\"", game, e.getTitle())), input);
            });
        }

        // 5. If the user is playing by a specific passage name...

        else {
            MemorizationGame game;
            Passage toLearn;

            // a. Load the passage.

            try {
                toLearn = list.get(args.get(1));
            }
            catch (NoSuchElementException exception) {
                // Try to interpret the name as a rank

                int rank;
                try {
                    rank = Integer.parseInt(args.get(1)) - 1;
                }
                catch (NumberFormatException exception2) {
                    throw new InvalidCommandOperationException( String.format("Passage entitled \"%s\" not found.", args.get(1)) );
                }

                if (rank >= 0 && rank < list.size()) {
                    toLearn = list.at(rank);
                }
                else {
                    throw new InvalidCommandOperationException( String.format("Index %d out of bounds for %d passages.", rank, list.size()) );
                }
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
                cls(writer);
                writer.println(game);
                writer.println();
                writer.print(">> ");
                String userIn = in.nextLine();

                // If the input matches the prompt, it is correct.
                int matchResult = game.matches(userIn);

                if (matchResult == MemorizationGame.A_MATCH) {
                    writer.println("Correct!\n");
                    game.next();
                    delay();
                }

                // If the user inputs an empty string, this is a skip of the re-type routine. Ask the user if they
                // recited correctly.
                else if (userIn.equals("")) {
                    writer.println("Were you correct?\n");
                    writer.printf("Correct text: \"%s\"\n\n", toLearn.fullText());

                    do {
                        writer.print("(y/n) >> ");
                        userIn = in.nextLine();
                    } while(!(userIn.equals("y") || userIn.equals("n")));

                    if (userIn.equals("y")) {
                        writer.println("\nCorrect!\n");
                        game.next();
                        delay();
                    }
                    else {
                        writer.println("\nIncorrect.\n");
                        delay();
                    }
                }

                // Otherwise, it is incorrect.
                else {
                    writer.println("Incorrect.\n");

                    if (matchResult >= userIn.length()) {
                        writer.println("Input string is too short.");
                    }
                    else if (matchResult >= toLearn.fullText().length()) {
                        writer.println("Input string is too long.");
                    }
                    else {
                        writer.printf("Input string is incorrect at location %d, where '%s' should be '%s'.\n",
                                matchResult, userIn.charAt(matchResult), toLearn.fullText().charAt(matchResult));
                        writer.printf("%s\n\n", StringHelpers.snippet(userIn, matchResult, SNIPPET_BEFORE_AFTER, SNIPPET_BEFORE_AFTER));
                    }


                    writer.printf("Correct text: \"%s\"\n\n", toLearn.fullText());
                    writer.println("Press enter to continue.");
                    in.nextLine();
                }
            }
        }

        return null;
    }
}
