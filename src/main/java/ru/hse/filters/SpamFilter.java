package ru.hse.filters;

import ru.hse.model.Message;

public interface SpamFilter {
    boolean isSpam(Message message);
}
