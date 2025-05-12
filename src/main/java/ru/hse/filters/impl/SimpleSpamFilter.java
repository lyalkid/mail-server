package ru.hse.filters.impl;

import ru.hse.filters.SpamFilter;
import ru.hse.model.Message;

public class SimpleSpamFilter implements SpamFilter {
    @Override
    public boolean isSpam(Message message) {
        if (message == null) {
            return false;
        }
        String messageText = (message.getCaption() + " " + message.getText()).toLowerCase();
        return messageText.contains("spam");
    }
}
