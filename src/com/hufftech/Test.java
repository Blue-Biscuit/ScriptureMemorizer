package com.hufftech;

import com.hufftech.passage.Passage;
import com.hufftech.passage.StringPassage;

public class Test {
    public static void main(String[] args) {
        Passage p = new StringPassage("This is my passage: Hello World!");

        System.out.println("\"" + p + "\"");

        for (String word : p) {
            System.out.println("\"" + word + "\"");
        }

    }
}
