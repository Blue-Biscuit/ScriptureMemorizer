package net.ahuffman.cli;

import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
    public static void loadCommands(ArrayList<Command> commands) {
        commands.add(new NewPassageCommand());
    }

    public static String getInput(String promptPattern, Scanner s) {
        // Get input.
        System.out.print(promptPattern);
        String input = s.nextLine();

        // Clean input.
        input = input.trim();

        return input;
    }

    public static void main(String[] args) {
        ArrayList<Command> commands = new ArrayList<>();
        Scanner s;
        PassagesList p;

        // Setup scanner.
        s = new Scanner(System.in);

        // Load the commands.
        loadCommands(commands);

        // Setup list of passages.
        p = new PassagesList();

        // Command loop.
        while (true) {
            String input = getInput(">>> ", s);
            Command c = null;
            int firstSpace = input.indexOf("\s");
            String commandName;
            String arguments;

            // Parse the command string.
            if (firstSpace == -1) {
                commandName = input;
                arguments = "";
            }
            else {
                commandName = input.substring(0, firstSpace);
                arguments = input.substring(firstSpace + 1);
            }

            for (Command e : commands) {
                if (e.getName().equals(commandName)) {
                    c = e;
                    break;
                }
            }

            if (c == null) {

            }
            else {
                c.execute(arguments, p);
            }
        }
    }
}
