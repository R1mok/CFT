package com.r1mok;

import java.util.*;

public class Sorter {
    private Map<ReadFromFile, String> buffer = new LinkedHashMap<>();

    Map<ReadFromFile, Integer> getIntegerMap(Map<ReadFromFile, String> stringMap, List<ReadFromFile> deletedLinesList) {
        Map<ReadFromFile, Integer> resultMap = new LinkedHashMap<>();
        ReadFromFile lastFile = null;
        for (Map.Entry<ReadFromFile, String> entry : stringMap.entrySet()) {
            lastFile = entry.getKey();
            try {
                resultMap.put(entry.getKey(), Integer.parseInt(entry.getValue()));
            } catch (NumberFormatException e) {
                String incorrectString = lastFile.getCurrentString();
                if (!lastFile.EOF) {
                    stringMap.put(lastFile, lastFile.toNextString());
                    System.out.println("Can`t recognize integer from file");
                    System.out.println("Deleting line \"" + incorrectString + "\" from file " + lastFile.getFile());
                    getIntegerMap(stringMap, deletedLinesList);
                } else {
                    deletedLinesList.add(lastFile);
                }
            }
        }
        for (ReadFromFile readFromFile : deletedLinesList) {
            resultMap.remove(readFromFile);
        }
        return resultMap;
    }

    void sort() {
        for (ReadFromFile file : Main.inputFiles) {
            if (!file.EOF) {
                buffer.put(file, file.getCurrentString());
            }
        }

        while (!buffer.isEmpty()) {
            Map.Entry<ReadFromFile, ?> entry;
            if (Main.isString) {
                if (Main.isDeskSort) {
                    entry = Collections.max(buffer.entrySet(), Map.Entry.comparingByValue());
                } else {
                    entry = Collections.min(buffer.entrySet(), Map.Entry.comparingByValue());
                }
            } else {
                List<ReadFromFile> deletedLinesList = new ArrayList<>();
                Map<ReadFromFile, Integer> integerMap = getIntegerMap(buffer, deletedLinesList);
                for (ReadFromFile deletedLine : deletedLinesList) {
                    buffer.remove(deletedLine);
                }
                if (integerMap.isEmpty()) {
                    continue;
                }
                if (Main.isDeskSort) {
                    entry = Collections.max(integerMap.entrySet(), Map.Entry.comparingByValue());
                } else {
                    entry = Collections.min(integerMap.entrySet(), Map.Entry.comparingByValue());
                }
            }
            if (!entry.getKey().EOF) {
                Main.outputFile.write(String.valueOf(entry.getValue()));
                String nextElem = entry.getKey().toNextString();
                buffer.put(entry.getKey(), nextElem);
            } else {
                buffer.remove(entry.getKey());
            }
        }
    }
}
