package ru.hse.filters.impl;

import java.util.*;
import ru.hse.filters.SpamFilter;
import ru.hse.model.Message;

public class RepetitionSpamFilter implements SpamFilter {
    private final int maxRepetitions;

    public RepetitionSpamFilter(int maxRepetitions) {
        this.maxRepetitions = maxRepetitions;
    }

    @Override
    public boolean isSpam(Message message) {
        if (message == null) {
            return false;
        }
        String messageText = (message.getCaption() + " " + message.getText());
        List<String> words = new ArrayList<>(Arrays.asList(messageText.split(" ")));
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            if (wordCount.containsKey(word)) {
                wordCount.put(word, wordCount.get(word) + 1);
                if (wordCount.get(word) > maxRepetitions) {
                    return true;
                }
            } else {
                wordCount.put(word, 1);
            }
        }
        return false;
    }
}
