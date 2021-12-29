package app.Model;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    private String name;
    private int cuid;
    private List<Message> messages;
    private List<User> users;
    private User admin;

    public Channel(String name, User user, int cuid){
        this.cuid = cuid;
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

    public int getCuid() {
        return cuid;
    }

    public void setCuid(int cuid) {
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
