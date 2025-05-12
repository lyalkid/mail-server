package ru.hse.filters.impl;

import java.util.List;
import ru.hse.filters.SpamFilter;
import ru.hse.model.Message;

public class CompositeSpamFilter implements SpamFilter {
    private final List<SpamFilter> filters;

    public CompositeSpamFilter(List<SpamFilter> filters) {
        this.filters = filters;
    }

    @Override
    public boolean isSpam(Message message) {
        for (SpamFilter filter : filters) {
            if (filter.isSpam(message))
                return true;
        }
        return false;
    }
}
