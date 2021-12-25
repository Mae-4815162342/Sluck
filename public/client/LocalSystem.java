package client;

import java.util.ArrayList;

import GUI.MainFrame;
import Network.*;

public class LocalSystem {
    private ArrayList<User> users;
    private ArrayList<Channel> channels;
    private User currentUser;
    private static LocalSystem system = new LocalSystem();

    private LocalSystem() {
        users = Network.fetchUsers();
        channels = null;
        currentUser = null;
    }

    public static LocalSystem getSystem() {
        if(system == null) system = new LocalSystem();
        return system;
    }

    public boolean checkUserExistence(String pseudo) {
        users = Network.fetchUsers();
        for(User user : users) {
            if(user.getPseudo().equals(pseudo)) return true;
        }
        return false;
    }

    public boolean addUser(String pseudo, String password) {
        try {
            User newUser = new User(pseudo, password);
            users.add(newUser);
            Network.updateUsers(newUser);
            currentUser = newUser;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public User getUser(String pseudo) {
        users = Network.fetchUsers();
        for(User user : users) {
            if(user.getPseudo().equals(pseudo)) return user;
        }
        return null;
    }

    public Channel getChannel(String name) {
        channels = Network.fetchChannels();
        for(Channel chan : channels) {
            if(chan.getName().equals(name)) {
                Network.setCurrentChannel(chan);
                chan.setMessages(Network.fetchMessages());
                return chan;
            }
        }
        return null;
    }

    public void connect(User user) {
        currentUser = user;
        Network.notifyConnection(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getUsers(){
        users = Network.fetchUsers();
        return users;
    }

    public ArrayList<Channel> getChannels() {
        channels = Network.fetchChannels();
        return channels;
    }

    public void sendMessage(String message, Channel outChannel) {
        Network.sendMessage(message, outChannel);
        //pas besoin de faire d'appel pour ajouter le message au channel dans le système, le serveur va renvoyer le message
        //à tous les utilisateurs du channel : même pour l'envoyeur, il sera ajouté quand il sera reçu (sinon risque de doublon)

        //à des fins de test, on appelle ici la méthode receiveMessage, qui devra être supprimée à l'implémentation du réseau
        this.receiveMessage(message, currentUser, outChannel);
    }

    public void receiveMessage(String message, User user, Channel inChannel) {
        inChannel.addMessage(user, message);
        MainFrame main = MainFrame.getMainFrame();
        main.updateChannel(inChannel);
    }

    public void createChannel(String channelName) {
        Network.sendChannelCreation(channelName);
        //idem que pour les messages, le channel sera créé dans la base de données

        //pour les tests, appel à la méthode de réception du channel
        this.receiveNewChannel(channelName, currentUser);
    }

    public void receiveNewChannel(String channelName, User owner) {
        MainFrame main = MainFrame.getMainFrame();
        Channel newChannel = new Channel(channelName, owner);
        channels.add(newChannel);
        main.updateChannelList();
    }

    public boolean checkChannelExistence(String channelName) {
        return getChannel(channelName) != null;
    }
}
