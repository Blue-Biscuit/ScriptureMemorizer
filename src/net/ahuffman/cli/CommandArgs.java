package net.ahuffman.cli;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * A class to parse command arguments.
 */
public class CommandArgs implements Iterable<String> {
    /**
     * Constructor.
     * @param args A list of arguments given as input by the user.
     */
    public CommandArgs(String args) {
        _list = new ArrayList<>();

        load(args);
    }

    /**
     * Default constructor.
     */
    public CommandArgs() {
        _list = new ArrayList<>();
    }

    /**
     * Gets the command argument at the index.
     * @param rank the rank at which to get.
     * @return The command argument requested.
     * @throws IndexOutOfBoundsException If the given index is out of bounds.
     */
    public String get(int rank) throws IndexOutOfBoundsException {
        return _list.get(rank);
    }

    /**
     * Gets the size of the argument list.
     * @return The size of the list.
     */
    public int size() {
        return _list.size();
    }

    /**
     * If the list is empty, return true.
     * @return True if empty, false otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Private helper method to load the command from a string.
     * @param text The text to load from.
     */
    private void load(String text) {
        int begin = -1;
        int end;
        boolean inArg = false;
        boolean inQuotes = false;

        int len = text.length();

        // Algorithm:
        // 1. Iterate over each character.
            // a. If a beginning character is found, flag the beginning of an argument.
            // b. If an end character is found, flag the end and add to the list.
        // 2. If in an argument, add it.

        // 1. Iterate over each character.

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);


            // a. If a beginning character is found, flag the beginning of an argument.

            if (!(inArg || Character.isWhitespace(c))) {
                inArg = true;

                if (c == '"') {
                    inQuotes = true;

                    begin = i + 1;
                    i++;
                }
                else {
                    begin = i;
                }

            }

            // b. If an end character is found, flag the end and add to the list.

            else if (inArg && ((inQuotes && c == '"') || (!inQuotes && Character.isWhitespace(c)))) {
                end = i;

                _list.add(text.substring(begin, end));

                inArg = false;
                inQuotes = false;
            }
        }

        // 2. If in an argument, add it.

        if (inArg) {
            _list.add(text.substring(begin));
        }

    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return _list.iterator();
    }

    private final ArrayList<String> _list;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter a line to test: ");
        String input = s.nextLine();

        CommandArgs cArgs = new CommandArgs(input);

        for (String arg : cArgs) {
            System.out.println(arg);
        }
    }
}
