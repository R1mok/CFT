package com;

import java.util.*;

public class Sorter {
    private Map<ReadFromFile, String> buffer = new HashMap<>();

    Map<ReadFromFile, Integer> getIntegerMap(Map<ReadFromFile, String> stringMap) {
        Map<ReadFromFile, Integer> resultMap = new HashMap<>();
        ReadFromFile lastFile = null;
        for (Map.Entry<ReadFromFile, String> entry : stringMap.entrySet()) {
            lastFile = entry.getKey();
            try {
                resultMap.put(entry.getKey(), Integer.parseInt(entry.getValue()));
            } catch (NumberFormatException e) {
                if (!lastFile.EOF) {
                    System.out.println("Can`t recognize integer from file");
                    System.out.println("Deleting line \"" + lastFile.getCurrentString() + "\" from file " + lastFile.getFile());
                    stringMap.put(lastFile, lastFile.toNextString());
                    getIntegerMap(stringMap);
                } else {
                    stringMap.remove(lastFile);
                }
            }
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
                Map<ReadFromFile, Integer> integerMap = getIntegerMap(buffer);
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
