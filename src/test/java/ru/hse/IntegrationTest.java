package ru.hse;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.junit.jupiter.api.Test;
import ru.hse.filters.impl.KeywordsSpamFilter;
import ru.hse.model.*;

class IntegrationTest {
    @Test
    void testFullFlow() {
        UserStorage storage = new UserStorage();
        storage.addUser("Alice");
        storage.addUser("Bob");

        User alice = storage.getUserByName("Alice");
        User bob = storage.getUserByName("Bob");
        bob.setSpamFilter(new KeywordsSpamFilter(Set.of("ad")));

        alice.sendMessage("Hello", "How are you?", bob);
        assertEquals(1, bob.getInbox().size());

        alice.sendMessage("AD", "Special offer!", bob);
        assertEquals(1, bob.getSpam().size());
    }
}
