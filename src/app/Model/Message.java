package app.Model;

public class Message {
    private String muid;
    private String message;
    private User user;
    private Channel channel;

    public Message(User user, String message){
        this.user = user;
        this.message = message;
    }

    public User getUser(){
        return user;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    public String getMessage() {
        return message;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    //pour modifier un message
    public void setMessage(String message){
        this.message = message;
    }
}
