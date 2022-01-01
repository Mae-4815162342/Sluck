package client;

import java.util.ArrayList;

public class Channel {
    private User owner;
    private String name;
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    public Channel(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addMessage(User user, String message) {
        this.messages.add(new Message(user, message));
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
