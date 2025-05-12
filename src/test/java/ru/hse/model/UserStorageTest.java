package ru.hse.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.hse.model.UserStorage;

class UserStorageTest {
    @Test
    void testAddAndRetrieveUser() {
        UserStorage storage = new UserStorage();
        storage.addUser("Alice");

        assertTrue(storage.userExists("Alice"));
        assertNotNull(storage.getUserByName("Alice"));
    }

    @Test
    void testDuplicateUser() {
        UserStorage storage = new UserStorage();
        storage.addUser("Bob");
        storage.addUser("Bob");

        assertEquals(1, storage.getAllUsers().size());
    }

    @Test
    void testNonExistingUser() {
        UserStorage storage = new UserStorage();
        assertNull(storage.getUserByName("Unknown"));
        assertFalse(storage.userExists("Ghost"));
    }
}