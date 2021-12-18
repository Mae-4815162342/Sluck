package app.Repo;

import java.util.List;

import app.Model.*;

public class ChannelRepository {
    public static void addMessage(Channel channel, Message message){
        //TODO : add message to DB | update DB
        List<Message> messages = channel.getMessages();
        messages.add(message);
    }

    public static void addUser(Channel channel, User user){
        //TODO : add user to DB
        List<User> users = channel.getListOfUsers();
        users.add(user);
    }

    public static Channel getChannel(String channelTag){
        //TODO : find channelTag in Data Base or return null
        return null;
    }

    public static void addChannel(Channel channel){
        //TODO : add channel in DataBase
    }
}
