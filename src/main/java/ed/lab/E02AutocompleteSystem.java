package ed.lab;

import java.util.*;
import java.util.stream.Collectors;

public class E02AutocompleteSystem {
    private final Map<String, Integer> sentenceCount;
    private String currentInput;

    public E02AutocompleteSystem(String[] sentences, int[] times) {
        sentenceCount = new HashMap<>();
        currentInput = "";
        for (int i = 0; i < sentences.length; i++) {
            sentenceCount.merge(sentences[i], times[i], Integer::sum);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            sentenceCount.merge(currentInput, 1, Integer::sum);
            currentInput = "";
            return Collections.emptyList();
        }

        currentInput += c;
        String prefix = currentInput;

        return sentenceCount.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(prefix))
                .sorted((e1, e2) -> {
                    int freqCompare = e2.getValue().compareTo(e1.getValue());
                    if (freqCompare != 0) {
                        return freqCompare;
                    }
                    return e1.getKey().compareTo(e2.getKey());
                })
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}