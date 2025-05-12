package ru.hse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.hse.filters.SpamFilter;
import ru.hse.filters.impl.SimpleSpamFilter;

public class User {
    private final String userName;
    private final List<Message> inbox = new ArrayList<Message>();
    private final List<Message> outbox = new ArrayList<>();
    private final List<Message> spam = new ArrayList<>();
    private SpamFilter spamFilter = new SimpleSpamFilter();

    public User(String userName) {
        this.userName = userName;
    }

    public void sendMessage(String caption, String text, User receiver) {
        Message message = new Message(caption, text, this, receiver);
        outbox.add(message);
        if (receiver.spamFilter.isSpam(message)) {
            receiver.spam.add(message);
        } else {
            receiver.inbox.add(message);
        }
    }

    public String getUserName() {
        return userName;
    }

    public List<Message> getInbox() {
        return inbox;
    }

    public List<Message> getOutbox() {
        return outbox;
    }

    public List<Message> getSpam() {
        return spam;
    }

    public void setSpamFilter(SpamFilter spamFilter) {
        this.spamFilter = spamFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }
}
