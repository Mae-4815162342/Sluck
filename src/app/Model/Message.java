package app.Model;

import javax.swing.event.ChangeEvent;

import app.Repo.ChannelRepository;

public class Message {
    private final int muid;
    private User from;
    private Channel channel;
    private String message;

    public Message(User from, String message, Channel channel){
        this.from = from;
        this.message = message;
        this.channel = channel;
        this.muid = ChannelRepository.getNbMessage() +1;
    }

    public int getId(){return muid;}
    public User getUser(){return from;}

    public Channel getChannel(){return channel;}

    public String getMessages(){return message;}

    //pour modifier un message
    public void setMessage(String message){this.message = message;}
}
