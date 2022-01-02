package client;

import java.util.ArrayList;

public class Channel {
    private int cuid;
    private int ownerUid;
    private User owner;
    private String name;
    private ArrayList<Message> messages = new ArrayList<>();

    public Channel(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Channel(app.Model.Channel channel) {
        this.cuid = channel.getCuid();
        this.ownerUid = channel.getAdminUuid();
        LocalSystem system = LocalSystem.getSystem();
        this.owner = system.getUserById(this.ownerUid);
        this.name = channel.getName();
    }

    public void addMessage(Message message) {
        this.messages.add(message);
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

    public int getCuid() {
        return cuid;
    }

    public int getOwnerUid() {
        return ownerUid;
    }
}
