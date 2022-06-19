package net.ahuffman.common;

import java.util.InputMismatchException;
import java.util.Locale;

public class StringHelpers {
    /**
     * Gets a snippet of a string.
     * @param string The string to snippet.
     * @param at The index to begin the snippet.
     * @param before The number of characters to include before 'at' in the snippet.
     * @param after The number of characters to include after 'at' in the snippet.
     * @return The result of the snippet.
     */
    public static String snippet(String string, int at, int before, int after) {
        int begin, end;
        final String result;

        final int strLen = string.length();

        // Algorithm:
        // 1. Loop from at to the end of the string, or until there is no more "length."
        // 2. Loop from at to the beginning of the string, or until there is no more "length."
        // 3. Return the result, formatted.

        // 1. Loop from at to the end of the string, or until there is no more "length."

        for (end = at; end < strLen && after > 0; end++, after--);

        // 2. Loop from at to the beginning of the string, or until there is no more "length."

        for (begin = at; begin > 0 && before > 0; begin--, before--);

        // 3. Return the result, formatted.

        if (begin == 0 && end == strLen) {
            result = string;
        }
        else if (begin == 0) {
            result = String.format("%s...", string.substring(begin, end));
        }
        else if (end == strLen) {
            result = String.format("...%s", string.substring(begin, end));
        }
        else {
            result = String.format("...%s...", string.substring(begin, end));
        }

        return result;
    }

    /**
     * Determines if the passed-in string can be parsed into an integer.
     * @param value The string to check.
     * @return True if this value can be parsed, false otherwise.
     */
    public static boolean isInt(String value) {
        // Algorithm:
        // 1. Try to parse the integer.
        // 2. If fail, return false.
        // 3. If pass, return true.

        // 1. Try to parse the integer.

        try {
            Integer.parseInt(value);
        }

        // 2. If fail, return false.

        catch(NumberFormatException e) {
            return false;
        }

        // 3. If pass, return true.

        return true;
    }

    /**
     * Parses a y/n value.
     * THIS ASSUMES ENGLISH LOCALE!
     * @param value The value to parse.
     * @return True if yes.
     * @throws InputMismatchException If the input wasn't a yes or a no.
     * @throws NullPointerException If the input was null.
     */
    public static boolean parseYesNo(String value) throws InputMismatchException, NullPointerException {
        // Algorithm:
        // 1. Param check.
        // 2. Format the input value correctly.
        // 3. If beginning with a 'y,' return true.
        // 4. If beginning with a 'n,' return false.
        // 5. Otherwise, throw.

        // 1. Param check.

        if (value == null) {
            throw new NullPointerException("input value was null.");
        }

        // 2. Format the input value correctly.

        value = value.toLowerCase(Locale.ENGLISH).trim();

        // 3. If an empty string, throw.

        if (value.length() == 0) {
            throw new InputMismatchException("input was empty.");
        }

        // 4. If beginning with a 'y,' return true.

        else if (value.charAt(0) == 'y') {
            return true;
        }

        // 5. If beginning with a 'n,' return false.

        else if (value.charAt(0) == 'n') {
            return false;
        }

        // 6. Otherwise, throw.

        else {
            throw new InputMismatchException("input not a yes or no value.");
        }
    }
}
