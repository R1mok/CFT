package org.example;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static boolean isString;
    public static boolean isDeskSort = false;
    public static List<ReadFromFile> inputFiles;
    public static WriteToFile outputFile;

    public static void main(String[] args) {
        Parser.parse(args);
        Sorter sorter = new Sorter(isString, isDeskSort, inputFiles, outputFile);
        sorter.sort();
    }
}