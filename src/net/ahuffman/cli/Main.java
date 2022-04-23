package net.ahuffman.cli;

import net.ahuffman.passage.Passage;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void introPrint() {
        System.out.println("PassageMem. 2022.");
        System.out.println("By Andrew Huffman.");
        System.out.println();
    }

    public static Command inputCommand(String name, Command[] commands) {
        for(var e : commands) {
            if (e.getName().equals(name)) {
                return e;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Command[] commands = new Command[] {new NewPassageCommand()};
        ArrayList<Passage> passages = new ArrayList<>();
        introPrint();

        String input;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print(">>> ");
            input = s.nextLine();
            String[] arguments = input.split("\s");

            Command c = inputCommand(arguments[0], commands);
            c.call(arguments, passages);
        }
    }
}
