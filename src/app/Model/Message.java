package app.Model;

public class Message {
    private int muid;
    private String message;
    private User user;
    private Channel channel;

    public Message(int muid,User user, String message){
        this.muid = muid; 
        this.user = user;
        this.message = message;
    }

    public User getUser(){
        return user;
    }

    public int getMuid() {
        return muid;
    }

    public void setMuid(int muid) {
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
