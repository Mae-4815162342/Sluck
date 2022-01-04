package client;

public class Message {
    private User user;
    private String content;
    private int muid;
    private int uuid;
    private int cuid;

    public Message(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public Message(app.Model.Message message) {
        this.uuid = message.getUuid();
        this.muid = message.getMuid();
        this.cuid = message.getCuid();
        LocalSystem system = LocalSystem.getSystem();
        this.user = system.getUserById(uuid);
        this.content = message.getMessage();
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        LocalSystem system = LocalSystem.getSystem();
        return user = system.getUserById(uuid);
    }

    public int getCuid() {
        return cuid;
    }

    public int getUuid() {
        return uuid;
    }
}
