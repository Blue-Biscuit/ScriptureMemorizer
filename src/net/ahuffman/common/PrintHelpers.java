package net.ahuffman.common;

import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

/**
 * Helper class for printing to console.
 */
public class PrintHelpers {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private static final int DEFAULT_DELAY_SECONDS = 3;

    /**
     * Prints the given text in a red color.
     * @param out The stream to print to.
     * @param toPrint The text to print.
     */
    public static void printRed(PrintStream out, String toPrint) {
        out.print(ANSI_RED + toPrint + ANSI_RESET);
    }

    /**
     * Prints the given text in a green color.
     * @param out The stream to print to.
     * @param toPrint The text to print.
     */
    public static void printGreen(PrintStream out, String toPrint) {
        out.print(ANSI_GREEN + toPrint + ANSI_RESET);
    }

    /**
     * Prints the given text in a yellow color.
     * @param out The stream to print to.
     * @param toPrint The text to print.
     */
    public static void printYellow(PrintStream out, String toPrint) {
        out.print(ANSI_YELLOW + toPrint + ANSI_RESET);
    }

    /**
     * Iff the passed input is System.out, clear the terminal.
     * @param out The output.
     */
    public static void cls(PrintStream out) {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
            else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        }
        catch (Exception ignored) {

        }
    }

    /**
     * Delays for a specified number of seconds.
     */
    public static void delay(int seconds) {

        try {
            TimeUnit.SECONDS.sleep(seconds);
        }
        catch (Exception ignored) {

        }
    }

    /**
     * Delays by a default value.
     */
    public static void delay() {
        delay(DEFAULT_DELAY_SECONDS);
    }
}
