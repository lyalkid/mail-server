package ru.hse.app;

import ru.hse.filters.SpamFilter;
import ru.hse.filters.impl.*;
import ru.hse.model.Message;
import ru.hse.model.User;
import ru.hse.model.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

public class App {
    private static final Scanner input = new Scanner(System.in);
    private static final UserStorage userStorage = new UserStorage();

    public void execute() {
        while (true) {
            System.out.print("> ");
            String command = input.nextLine().trim();
            if (command.isEmpty()) continue;
            switch (command) {
                case "add":
                    handleAdd();
                    break;
                case "list":
                    handleList();
                    break;
                case "send":
                    handleSend();
                    break;
                case "inbox":
                    handleInbox();
                    break;
                case "spam":
                    handleSpam();
                    break;
                case "outbox":
                    handleOutbox();
                    break;
                case "setfilter":
                    handleSetFilter();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown command");
            }
        }
    }

    public void handleAdd() {
        System.out.print("Enter user name: ");
        String userName = input.nextLine().trim();
        if (userStorage.userExists(userName)) {
            System.out.printf("User '%s' already exists\n", userName);

        } else {
            userStorage.addUser(userName);
            System.out.printf("User '%s' added\n", userName);
        }
    }

    private void handleList() {
        List<User> users = userStorage.getAllUsers();
        for (User user : users) {
            System.out.printf("* %s\n", user.getUserName());
        }
        System.out.printf("Total: %d users\n", userStorage.getAllUsers().size());
    }

    private void handleSend() {

        String senderName = null;
        String receiverName = null;
        String caption = null;
        String text = null;


        while (true) {
            System.out.print("Enter sender name (or 'cancel'): ");
            senderName = input.nextLine().trim();

            if (senderName.equalsIgnoreCase("cancel")) return;
            if (!userStorage.userExists(senderName)) {
                System.out.println("Sender not found");
                continue;
            }
            break;
        }


        while (true) {
            System.out.print("Enter receiver name (or 'cancel'): ");
            receiverName = input.nextLine().trim();

            if (receiverName.equalsIgnoreCase("cancel")) return;
            if (!userStorage.userExists(receiverName)) {
                System.out.println("Receiver not found");
                continue;
            }
            break;
        }


        while (true) {
            System.out.println("Caption (or 'cancel'):");
            caption = input.nextLine().trim();

            if (caption.equalsIgnoreCase("cancel")) return;
            if (caption.isEmpty()) {
                System.out.println("Caption cannot be empty");
                continue;
            }
            break;
        }


        while (true) {
            System.out.println("Text (or 'cancel'):");
            text = input.nextLine().trim();

            if (text.equalsIgnoreCase("cancel")) return;
            if (text.isEmpty()) {
                System.out.println("Text cannot be empty");
                continue;
            }
            break;
        }

        User sender = userStorage.getUserByName(senderName);
        User receiver = userStorage.getUserByName(receiverName);
        sender.sendMessage(caption, text, receiver);
        System.out.println("Message sent");
    }


    private void handleInbox() {
        while (true) {
            System.out.print("Enter user name (or 'cancel'): ");
            String userName = input.nextLine().trim();
            if (userName.equalsIgnoreCase("cancel")) return;

            User user = userStorage.getUserByName(userName);
            if (user == null) {
                System.out.println("User not found");
                continue;
            }

            System.out.println("\n=== Inbox ===");
            user.getInbox().forEach(msg -> printMessage(msg));
            return;
        }
    }

    private void printMessage(Message msg) {
        System.out.println("From: " + msg.getSender().getUserName());
        System.out.println("Caption: " + msg.getCaption());
        System.out.println("Text: " + msg.getText());
        System.out.println("----------------------");
    }


    private void handleOutbox() {
        while (true) {
            System.out.print("Enter user name (or 'cancel'): ");
            String userName = input.nextLine().trim();
            if (userName.equalsIgnoreCase("cancel")) return;

            User user = userStorage.getUserByName(userName);
            if (user == null) {
                System.out.println("User not found");
                continue;
            }

            System.out.println("\n=== Outbox ===");
            user.getOutbox().forEach(msg -> printMessage(msg));
            return;
        }
    }

    private void handleSpam() {
        while (true) {
            System.out.print("Enter user name (or 'cancel' to exit): ");
            String userName = input.nextLine().trim();


            if (userName.equalsIgnoreCase("cancel")) {
                System.out.println("Operation canceled");
                return;
            }


            if (!userStorage.userExists(userName)) {
                System.out.println("Error: User '" + userName + "' not found");
                continue;
            }

            User user = userStorage.getUserByName(userName);
            List<Message> spamMessages = user.getSpam();


            System.out.println("\n=== Spam messages for " + userName + " ===");
            if (spamMessages.isEmpty()) {
                System.out.println("No spam messages found");
            } else {
                spamMessages.forEach(msg -> {
                    System.out.println("From: " + msg.getSender().getUserName());
                    System.out.println("Caption: " + msg.getCaption());
                    System.out.println("Text: " + msg.getText());
                    System.out.println("---------------------------");
                });
            }
            System.out.println("Total spam messages: " + spamMessages.size());
            break;
        }
    }

    private void handleSetFilter() {

        String userName = null;
        while (true) {
            System.out.print("Enter user name (or 'cancel'): ");
            userName = input.nextLine().trim();

            if (userName.equalsIgnoreCase("cancel")) {
                System.out.println("Operation canceled");
                return;
            }

            if (!userStorage.userExists(userName)) {
                System.out.println("User '" + userName + "' not found");
                continue;
            }
            break;
        }

        User user = userStorage.getUserByName(userName);
        List<SpamFilter> filters = new ArrayList<>();


        System.out.println("\nAvailable filter types:");
        System.out.println("1. simple - messages containing 'spam'");
        System.out.println("2. keywords - messages with specific keywords");
        System.out.println("3. repetition - messages with repeated words");
        System.out.println("4. sender - messages from specific sender");
        System.out.println("Enter 'done' to finish, 'cancel' to abort");

        
        while (true) {
            System.out.print("\nSelect filter type: ");
            String filterType = input.nextLine().trim().toLowerCase();


            if (filterType.equals("done")) {
                if (filters.isEmpty()) {
                    System.out.println("Warning: No filters added!");
                } else {
                    user.setSpamFilter(new CompositeSpamFilter(filters));
                    System.out.println("Successfully set " + filters.size() + " filters");
                }
                return;
            }


            if (filterType.equals("cancel")) {
                System.out.println("Operation canceled");
                return;
            }


            switch (filterType) {
                case "simple":
                    filters.add(new SimpleSpamFilter());
                    System.out.println("Added simple filter");
                    break;

                case "keywords":
                    Set<String> keywords = new HashSet<>();
                    while (true) {
                        System.out.print("Enter keywords (space-separated): ");
                        String inputLine = input.nextLine().trim();

                        if (inputLine.equalsIgnoreCase("cancel")) {
                            System.out.println("Keywords selection canceled");
                            continue ;
                        }

                        if (inputLine.isEmpty()) {
                            System.out.println("Error: At least one keyword required");
                            continue;
                        }

                        keywords = Arrays.stream(inputLine.split("\\s+"))
                                .map(String::toLowerCase)
                                .collect(Collectors.toSet());
                        break;
                    }
                    filters.add(new KeywordsSpamFilter(keywords));
                    System.out.println("Added keywords filter: " + keywords);
                    break;

                case "repetition":
                    int maxRepetitions = 0;
                    while (true) {
                        System.out.print("Enter maximum allowed repetitions: ");
                        String inputLine = input.nextLine().trim();

                        if (inputLine.equalsIgnoreCase("cancel")) {
                            System.out.println("Repetition filter canceled");
                            continue ;
                        }

                        try {
                            maxRepetitions = Integer.parseInt(inputLine);
                            if (maxRepetitions < 1) {
                                System.out.println("Error: Value must be at least 1");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid number format");
                        }
                    }
                    filters.add(new RepetitionSpamFilter(maxRepetitions));
                    System.out.println("Added repetition filter: max " + maxRepetitions);
                    break;

                case "sender":
                    String senderName = null;
                    while (true) {
                        System.out.print("Enter sender name to block: ");
                        senderName = input.nextLine().trim();

                        if (senderName.equalsIgnoreCase("cancel")) {
                            System.out.println("Sender filter canceled");
                            continue ;
                        }

                        if (!userStorage.userExists(senderName)) {
                            System.out.println("Error: Sender '" + senderName + "' not found");
                            continue;
                        }
                        break;
                    }
                    filters.add(new SenderSpamFilter(senderName));
                    System.out.println("Added sender filter: blocking " + senderName);
                    break;

                default:
                    System.out.println("Unknown filter type. Available options:");
                    System.out.println("simple, keywords, repetition, sender");
            }
        }
    }
}