package app.Model;

public class Message {
    private User from;
    private String message;

    public Message(User from, String message){
        this.from = from;
        this.message = message;
    }

    public User getUser(){return from;}

    public String getMessages(){return message;}

    //pour modifier un message
    public void setMessage(String message){this.message = message;}
}
