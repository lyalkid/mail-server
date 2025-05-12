package ru.hse.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStorage {
    private final Map<String, User> users = new HashMap<>();

    public void addUser(String name) {
        users.putIfAbsent(name, new User(name));
    }

    public User getUserByName(String name) {
        return users.get(name);
    }

    public boolean userExists(String name) {
        return users.containsKey(name);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
