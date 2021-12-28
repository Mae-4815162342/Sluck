package app.Model;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    private String name;
    private String cuid;
    private List<Message> messages;
    private List<User> users;
    private User admin;

    public Channel(String name, User user){
        this.name = name;
        this.admin = user;
        this.messages = new ArrayList<Message>();
        this.users = new ArrayList<User>();
        this.users.add(admin);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public User getAdmin(){
        return admin;
    }

    public List<User> getListOfUsers(){
        return users;
    }
    
    public List<Message> getMessages(){
        return messages;
    }
}
