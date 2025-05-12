package ru.hse.filters.impl;

import java.util.Set;
import ru.hse.filters.SpamFilter;
import ru.hse.model.Message;

public class KeywordsSpamFilter implements SpamFilter {
    private final Set<String> keywords;

    public KeywordsSpamFilter(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean isSpam(Message message) {
        if (message == null) {
            return false;
        }
        String messageText = (message.getCaption() + " " + message.getText()).toLowerCase();
        for (String word : keywords) {
            if (messageText.contains(word.toLowerCase()))
                return true;
        }
        return false;
    }
}
