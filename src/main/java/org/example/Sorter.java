package org.example;

import java.util.List;

public class Sorter {
    private boolean isString;
    private boolean isDeskSort;
    private List<ReadFromFile> inputFiles;
    private WriteToFile outputFile;
    public Sorter(boolean isString, boolean isDeskSort, List<ReadFromFile> inputFiles, WriteToFile outputFile) {
        this.inputFiles = inputFiles;
        this.isDeskSort = isDeskSort;
        this.isString = isString;
        this.outputFile = outputFile;
    }
    void sort() {

    }
}
