package app.Model;

import java.util.ArrayList;
import java.util.List;
import app.Repo.ChannelRepository;

public class Channel {
    private final int id;
    private String tag;
    private User admin;
    private List<Message> messages;
    private List<User> users;

    public Channel(String tag, User user){
        this.tag = tag;
        this.admin = user;
        this.messages = new ArrayList<Message>();
        this.users = new ArrayList<User>();
        this.users.add(admin);
        System.out.println(ChannelRepository.getNbChannel());
        id = ChannelRepository.getNbChannel() + 1;
    }

    public Channel(int id, String tag, User user){
        this.tag = tag;
        this.admin = user;
        this.messages = new ArrayList<Message>();
        this.users = new ArrayList<User>();
        this.users.add(admin);
        this.id = id;
    }
    
    public User getAdmin(){return admin;}

    public List<User> getListOfUsers(){return users;}

    public String getTag(){return tag;}
    
    public List<Message> getMessages(){return messages;}

    public void setMessages(List<Message> l){
        messages = l;
    }

    public int getId(){return id;}
}
