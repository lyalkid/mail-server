package ru.hse.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.hse.filters.impl.SimpleSpamFilter;
import ru.hse.model.Message;
import ru.hse.model.User;

class UserTest {
    @Test
    void testUserCreation() {
        User user = new User("Alice");
        assertEquals("Alice", user.getUserName());
    }

    @Test
    void testMessageSending() {
        User sender = new User("Bob");
        User receiver = new User("Alice");

        sender.sendMessage("Hello", "Test message", receiver);

        assertEquals(1, sender.getOutbox().size());
        assertEquals(1, receiver.getInbox().size());
    }

    @Test
    void testSpamFiltering() {
        User sender = new User("Spammer");
        User receiver = new User("Alice");
        receiver.setSpamFilter(new SimpleSpamFilter());

        sender.sendMessage("SPAM", "Buy now!", receiver);

        assertEquals(1, receiver.getSpam().size());
        assertEquals(0, receiver.getInbox().size());
    }
}