package client;

public class Message {
    private User user;
    private String content;
    private int muid;
    private String username;
    private int cuid;

    public Message(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public Message(app.Model.Message message) {
        this.username = message.getUsername();
        this.muid = message.getMuid();
        this.cuid = message.getCuid();
        this.content = message.getMessage();
    }

    public String getContent() {
        return content;
    }

    public int getCuid() {
        return cuid;
    }

    public String getUsername() {
        return username;
    }
}
