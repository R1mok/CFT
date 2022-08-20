package org.example;

import java.sql.Ref;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Sorter {
    private boolean isString;
    private boolean isDeskSort;
    private List<ReadFromFile> inputFiles;
    private WriteToFile outputFile;
    private Map<ReadFromFile, String> buffer = new HashMap<>();

    public Sorter(boolean isString, boolean isDeskSort, List<ReadFromFile> inputFiles, WriteToFile outputFile) {
        this.inputFiles = inputFiles;
        this.isDeskSort = isDeskSort;
        this.isString = isString;
        this.outputFile = outputFile;
    }

    Map<ReadFromFile, Integer> getIntegerMap(Map<ReadFromFile, String> stringMap) {
        Map<ReadFromFile, Integer> resultMap = null;
        try {
            resultMap = stringMap.entrySet().stream().collect(Collectors.toMap(
                    Map.Entry::getKey, entry -> Integer.parseInt(entry.getValue())
            ));
        } catch (NumberFormatException e) {
            System.out.println("Can`t recognize integer from file");
            throw new RuntimeException(e.getMessage());
        }
        return resultMap;
    }

    void sort() {
        for (ReadFromFile file : inputFiles) {
            if (!file.EOF) {
                buffer.put(file, file.getCurrentString());
            }
        }

        while (!buffer.isEmpty()) {
            Map.Entry<ReadFromFile, ?> entry;
            if (isString) {
                if (isDeskSort) {
                    entry = Collections.max(buffer.entrySet(), Map.Entry.comparingByValue());
                } else {
                    entry = Collections.min(buffer.entrySet(), Map.Entry.comparingByValue());
                }
            } else {
                Map<ReadFromFile, Integer> integerMap = getIntegerMap(buffer);
                if (isDeskSort) {
                    entry = Collections.max(integerMap.entrySet(), Map.Entry.comparingByValue());
                } else {
                    entry = Collections.min(integerMap.entrySet(), Map.Entry.comparingByValue());
                }
            }
            if (!entry.getKey().EOF) {
                outputFile.write(String.valueOf(entry.getValue()));
                String nextElem = entry.getKey().toNextString();
                buffer.put(entry.getKey(), nextElem);
            } else {
                buffer.remove(entry.getKey());
            }
        }
    }
}
