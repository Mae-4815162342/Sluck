package Network;
import client.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class Network {
    //données de test (à supprimer)
    public static ArrayList<User> users = initUser();
    public static ArrayList<Channel> channels = initChannel();
    public static LocalSystem system = LocalSystem.getSystem();

    public static Channel currentChannel = null;


    public static void connexion() {
        //établissement de la connexion avec le serveur
        sleep(10);
    }

    public static ArrayList<User> initUser() {
        //à supprimer à l'établissement de la connexion
        ArrayList<User> res = new ArrayList<>();
        try {
            res.add(new User("Winston", "1234"));
            res.add(new User("Anto du 24", "1234"));
            res.add(new User("Carla", "1234"));
            res.add(new User("Amy", "1234"));
            res.add(new User("JuliaTheBest", "1234"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ArrayList<Channel> initChannel() {
        //à supprimer à l'établissement de la connexion
        ArrayList<Channel> res = new ArrayList<>();
        try {
            User winston = new User("Winston", "1234");
            User julia = new User("JuliaTheBest", "1234");
            User amy = new User("Amy", "1234");
            User carla = new User("Carla", "1234");
            User anto = new User("Anto du 24", "1234");
            Channel winstonChannel = new Channel("Winston'sChannel", winston);
            Channel antoChannel = new Channel("AntoFaitDesVidéos", anto);
            res.add(antoChannel);
            res.add(winstonChannel);
            res.add(new Channel("CatsAreNice", carla));
            res.add(new Channel("YouHadOneJob", amy));
            res.add(new Channel("Unexpected", julia));
            antoChannel.addMessage(anto, "Yo everyone ! This is my new channel, where we can discuss about me");
            antoChannel.addMessage(julia, "That's so great! I love what you do on Tutube!");
            antoChannel.addMessage(anto, "Thank you for your support, it means a lot to me!");
            antoChannel.addMessage(winston, "Guys, checkout my channel! it's Winston'sChannel!");
            winstonChannel.addMessage(winston, "No one's here yet :,(");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ArrayList<User> fetchUsers() {
        return users;
    }

    public static ArrayList<Channel> fetchChannels() {
        return channels;
    }

    public static  ArrayList<Message> fetchMessages() {
        if(currentChannel != null) return currentChannel.getMessages();
        return null;
    }

    public static void updateUsers(User newUser) {
        users.add(newUser);
    }

    public static void notifyConnection(User user) {
        System.out.println(user.getPseudo()+" is connected!");
    }

    public static Channel getCurrentChannel() {
        return currentChannel;
    }

    public static void setCurrentChannel(Channel channel) {
        //ajouter la notification de la base de données : connexion au Thread
        currentChannel = channel;
    }

    private static void sleep(int i) {
        int res;
        for(int j = 0; j < i * 1_000_000_000; j+=2)
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
            res = 200000 *23456 ;
    }

    public static void sendMessage(String message, Channel outChannel) {
        if(outChannel.getName().equals(currentChannel.getName())) {
            //notification de la base de données: envoie de /send *message*
            System.out.println("Envoi de : /send " + message);
        }
        //en cas d'échec, gestion d'erreur avec un try/catch
    }

    public static void sendChannelCreation(String channel) {
        //notification de la base de données: envoie de /create *channel*
        System.out.println("Envoi de : /create " + channel);
        //en cas d'échec, gestion d'erreur avec un try/catch
    }


}
