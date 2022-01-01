package client;

public class Message {
    private User user;
    private String content;

    public Message(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }
}
