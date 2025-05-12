package ru.hse.model;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.hse.model.Message;
import ru.hse.model.User;

class MessageTest {
    @Test
    void testMessageCreation() {
        User sender = new User("Bob");
        User receiver = new User("Alice");
        Message msg = new Message("Test", "Content", sender, receiver);

        assertEquals("Test", msg.getCaption());
        assertEquals("Content", msg.getText());
        assertEquals(sender, msg.getSender());
        assertEquals(receiver, msg.getReceiver());
    }
}