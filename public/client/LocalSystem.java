package client;

import java.util.ArrayList;

import GUI.MainFrame;
import Network.*;

public class LocalSystem {
    private ArrayList<User> users;
    private ArrayList<Channel> channels;
    private ArrayList<Message> messages;
    private User currentUser;
    private static LocalSystem system = new LocalSystem();

    private LocalSystem() {
        users = null;
        channels = null;
        currentUser = null;
    }

    public void updateUsers() {
        Network.setUsersRequestInProcess(true);
        Network.fetchUsers();
        while(Network.isUsersRequestInProcess()) {
            users = Network.getUsers();
        }
    }

    public void updateChannels() {
        Network.setChannelsRequestInProcess(true);
        Network.fetchChannels();
        while(Network.isChannelsRequestInProcess()) {
            channels = Network.getChannels();
        }
    }

    public void updateMessages() {
        Network.setMessagesRequestInProcess(true);
        Network.fetchMessages();
        while(Network.isMessagesRequestInProcess()) {
            messages = Network.getMessages();
        }
    }

    public static LocalSystem getSystem() {
        if(system == null) system = new LocalSystem();
        return system;
    }

    public boolean addUser(String pseudo, String password) {
        Network.setSignupRequestInProcess(true);
        Network.signup(pseudo, password);
        while(Network.isSignupRequestInProcess()) {
            currentUser = Network.getCurrentUser();
        }
        if(currentUser == null) {
            return false;
        }
        return true;
    }

    public User getUserById(int uuid) {
        for(User user : users) {
            if(user.getUid() == uuid) return user;
        }
        return null;
    }

    public Channel getChannel(String name) {
        for(Channel chan : channels) {
            if(chan.getName().equals(name)) {
                Network.setCurrentChannel(chan);
                chan.setMessages(getMessagesFor(chan.getCuid()));
                return chan;
            }
        }
        return null;
    }

    public ArrayList<Message> getMessagesFor(int cuid) {
        ArrayList<Message> res = new ArrayList<>();
        for (Message mes : messages) {
            if(mes.getCuid() == cuid) res.add(mes);
        }
        return res;
    }

    public Channel getChannelById(int cuid) {
        for(Channel chan: channels) {
            if(chan.getCuid() == cuid) {
                return chan;
            }
        }
        return null;
    }

    public boolean connect(String pseudo, String password) {
        Network.setSigninRequestInProcess(true);
        Network.signin(pseudo, password);
        while(Network.isSigninRequestInProcess()) {
            currentUser = Network.getCurrentUser();
        }
        if(currentUser == null) {
            return false;
        }
        return true;
    }

    public void disconnect() {
        Network.signout();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        MainFrame main = MainFrame.getMainFrame();
        main.goToUserMain();
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void deleteChannel(String channelName) {
        Channel toDeleteChannel = getChannel(channelName);
        if(toDeleteChannel != null) {
            Network.delete(toDeleteChannel);
        }
    }

    public void receiveDeleteChannel(int cuid) {
        Channel deletedChannel = getChannelById(cuid);
        if(deletedChannel != null) {
            channels.remove(deletedChannel);
        }
        MainFrame main = MainFrame.getMainFrame();
        main.updateChannelList();
    }

    public void sendMessage(String message, Channel outChannel) {
        Network.sendMessage(message, outChannel, currentUser);
    }

    public void createChannel(String channelName) {
        Network.sendChannelCreation(channelName, currentUser);
    }

    public void receiveMessage(Message message) {
        MainFrame main = MainFrame.getMainFrame();
        Channel inChannel = getChannelById(message.getCuid());
        if(inChannel == null) return;
        inChannel.addMessage(message);
        messages.add(message);
        if(main.getCurrentChannel().getCuid() == inChannel.getCuid()){
            main.updateMessageList();
        }
    }

    public void receiveNewChannel(Channel newChannel) {
        MainFrame main = MainFrame.getMainFrame();
        channels.add(newChannel);
        newChannel.setMessages(new ArrayList<>());
        System.out.println(newChannel.getCuid());
        System.out.println(newChannel.getMessages());
        main.updateChannelList();
    }

    public void receiveNewUser(User user) {
        MainFrame main = MainFrame.getMainFrame();
        users.add(user);
        main.updateUserList();
    }

    public void suppressUser(User user) {
        MainFrame main = MainFrame.getMainFrame();
        users.remove(user);
        main.updateUserList();
    }

    public boolean checkChannelExistence(String channelName) {
        return getChannel(channelName) != null;
    }

    public void handleNetworkError(Exception e) {
        //à implémenter
        e.printStackTrace();
    }
}
