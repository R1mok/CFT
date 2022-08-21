package com;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    static void parse(String[] args) {
        int currentArgs = 0;
        if (args.length < 3)
            System.out.println("Incorrect count of parameters");
        else {
            for (int i = 0; i < 2; ++i) {
                if (args[currentArgs].equalsIgnoreCase("-a")) {
                    Main.isDeskSort = false;
                    ++currentArgs;
                }
                if (args[currentArgs].equalsIgnoreCase("-d")) {
                    Main.isDeskSort = true;
                    ++currentArgs;
                }
                if (args[currentArgs].equalsIgnoreCase("-s")) {
                    Main.isString = true;
                    ++currentArgs;
                }
                if (args[currentArgs].equalsIgnoreCase("-i")) {
                    Main.isString = false;
                    ++currentArgs;
                }
            }
        }
        Main.outputFile = new WriteToFile(new File(args[currentArgs++]));
        List<String> inputFiles = new ArrayList<>(Arrays.asList(args).subList(currentArgs, args.length));
        if (inputFiles.size() < 1) {
            System.out.println("No input files");
        }
        Main.inputFiles = new ArrayList<>();
        for (String file : inputFiles) {
            Main.inputFiles.add(new ReadFromFile(new File(file)));
        }
    }
}
