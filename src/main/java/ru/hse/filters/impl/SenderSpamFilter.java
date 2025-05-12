package ru.hse.filters.impl;

import ru.hse.filters.SpamFilter;
import ru.hse.model.Message;

public class SenderSpamFilter implements SpamFilter {
    private final String senderName;

    public SenderSpamFilter(String senderName) {
        this.senderName = senderName;
    }

    @Override
    public boolean isSpam(Message message) {
        return message.getSender().getUserName().equals(senderName);
    }
}
