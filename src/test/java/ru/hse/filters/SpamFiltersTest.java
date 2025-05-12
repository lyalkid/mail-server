package ru.hse.filters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import ru.hse.filters.impl.*;
import ru.hse.model.Message;
import ru.hse.model.User;

class SpamFiltersTest {
    @Test
    void testRepetitionFilterEdgeCases() {
        RepetitionSpamFilter filter = new RepetitionSpamFilter(3);
        Message msg1 = new Message("", "test test test", null, null);
        assertFalse(filter.isSpam(msg1));

        Message msg2 = new Message("", "test test test test", null, null);
        assertTrue(filter.isSpam(msg2));

        Message msg3 = new Message("", "TEST Test test", null, null);
        assertFalse(filter.isSpam(msg3));
    }
    @Test
    void testSimpleFilter() {
        SimpleSpamFilter filter = new SimpleSpamFilter();
        Message msg = new Message("SPAM", "Important", null, null);

        assertTrue(filter.isSpam(msg));
    }

    @Test
    void testKeywordsFilter() {
        KeywordsSpamFilter filter = new KeywordsSpamFilter(Set.of("urgent", "important"));
        Message msg = new Message("Alert", "URGENT meeting", null, null);

        assertTrue(filter.isSpam(msg));
    }

    @Test
    void testKeywordsPartialMatch() {
        KeywordsSpamFilter filter = new KeywordsSpamFilter(Set.of("urgent"));
        Message msg = new Message("", "This is urgent!", null, null);
        assertTrue(filter.isSpam(msg));

        Message msg2 = new Message("", "Urgency required", null, null);
        assertFalse(filter.isSpam(msg2));
    }

    @Test
    void testCompositeWithMultipleFilters() {
        CompositeSpamFilter filter =
            new CompositeSpamFilter(List.of(new SimpleSpamFilter(), new SenderSpamFilter("Spammer")));

        Message msg1 = new Message("SPAM", "Hello", null, null);
        assertTrue(filter.isSpam(msg1));

        User spammer = new User("Spammer");
        Message msg2 = new Message("Normal", "Hi", spammer, null);
        assertTrue(filter.isSpam(msg2));
    }

    @Test
    void testRepetitionFilter() {
        RepetitionSpamFilter filter = new RepetitionSpamFilter(3);
        Message msg = new Message("", "test test test test", null, null);

        assertTrue(filter.isSpam(msg));
    }

    @Test
    void testSenderFilter() {
        User blocked = new User("Spammer");
        SenderSpamFilter filter = new SenderSpamFilter("Spammer");
        Message msg = new Message("", "Hi", blocked, null);

        assertTrue(filter.isSpam(msg));
    }

    @Test
    void testCompositeFilter() {
        CompositeSpamFilter filter =
            new CompositeSpamFilter(List.of(new SimpleSpamFilter(), new KeywordsSpamFilter(Set.of("virus"))));

        Message msg1 = new Message("", "spam message", null, null);
        Message msg2 = new Message("Warning", "virus detected", null, null);

        assertTrue(filter.isSpam(msg1));
        assertTrue(filter.isSpam(msg2));
    }
}
