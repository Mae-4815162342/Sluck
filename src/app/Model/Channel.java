package app.Model;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    private String tag;
    private List<Message> messages;
    private List<User> users;
    private User admin;

    public Channel(String tag, User user){
        this.tag = tag;
        this.admin = user;
        this.messages = new ArrayList<Message>();
        this.users = new ArrayList<User>();
        this.users.add(admin);
    }
    
    public User getAdmin(){return admin;}

    public List<User> getListOfUsers(){return users;}

    public String getTag(){return tag;}
    
    public List<Message> getMessages(){return messages;}
}
