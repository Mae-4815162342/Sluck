package Network;
import GUI.UserMain;
import client.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Network {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Channel> channels = new ArrayList<>();
    private static  ArrayList<Message> messages = new ArrayList<>();

    public static LocalSystem system = LocalSystem.getSystem();
    public static Channel currentChannel = null;
    public static User currentUser = null;

    private static boolean usersRequestInProcess = false;
    private static boolean channelsRequestInProcess = false;
    private static boolean messagesRequestInProcess = false;
    private static boolean signinRequestInProcess = false;
    private static boolean signupRequestInProcess = false;

    public static void connexion() {
        Thread networkThread = new Thread() {
            public void run() {
                try {
                    new UserConnection("").run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        networkThread.start();
    }

    public static void fetchUsers() {
        try {
            UserConnection.sendRequest("get_users");
        } catch (Exception e) {
            if(system != null) { system.handleNetworkError(e); }
            else {
                e.printStackTrace();
            }
        }
    }

    public static void updateUserList(List<app.Model.User> user) {
        users = new ArrayList<>();
        for(app.Model.User u : user) {
            System.out.println(user);
            users.add(new User(u));
        }
        setUsersRequestInProcess(false);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setCurrentUser(app.Model.User user) {
        if(user != null ) currentUser = new User(user);
        else currentUser = null;
        Network.setSigninRequestInProcess(false);
        Network.setSignupRequestInProcess(false);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void fetchChannels() {
        try {
            UserConnection.sendRequest("get_channels");
        } catch (Exception e) {
            if(system != null) { system.handleNetworkError(e); }
            else {
                e.printStackTrace();
            }
        }
    }

    public static void updateChannelList(List<app.Model.Channel> channelList) {
        channels = new ArrayList<>();
        for(app.Model.Channel c : channelList) {
            channels.add(new Channel(c));
        }
        setChannelsRequestInProcess(false);
    }

    public static ArrayList<Channel> getChannels() {
        return channels;
    }

    public static void fetchMessages() {
        messages = null;
        try {
            UserConnection.sendRequest("get_messages");
        } catch (Exception e) {
            if(system != null) { system.handleNetworkError(e); }
            else {
                e.printStackTrace();
            }
        }
    }

    public static void updateMessageList(List<app.Model.Message> messageList) {
        messages = new ArrayList<>();
        for(app.Model.Message m: messageList) {
            messages.add(new Message(m));
        }
        setMessagesRequestInProcess(false);
    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }

    public static void signup(String pseudo, String password) {
        try {
            UserConnection.sendRequest("signup " + pseudo + " " + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void signin(String pseudo, String password) {
        try {
            UserConnection.sendRequest("signin " + pseudo + " " + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void signout() {
        try {
            UserConnection.sendRequest("signout");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setSignout(app.Model.User user) {
        User u = new User(user);
        if(currentUser.equals(u)){
            system.setCurrentUser(null);
            setAllRequestError();
        } else {
            system.suppressUser(u);
        }
    }

    public static Channel getCurrentChannel() {
        return currentChannel;
    }

    public static void setCurrentChannel(Channel channel) {
        //ajouter la notification de la base de données : connexion au Thread
        currentChannel = channel;
    }

    public static void sendMessage(String message, Channel outChannel, User user) {
        if(outChannel.getName().equals(currentChannel.getName())) {
            try {
                UserConnection.sendRequest("create_message " + outChannel.getCuid() + " " + user.getUid() + " " + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendChannelCreation(String channel, User owner) {
        try {
            UserConnection.sendRequest("create_channel " + channel + " " + owner.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void receiveChannel(app.Model.Channel channel) {
        system.receiveNewChannel(new Channel(channel));
    }

    public static void receiveMessage(app.Model.Message message) {
        system.receiveMessage(new Message(message));
    }

    public static void addUser(app.Model.User user) {
        if( user != null ) {
            system.receiveNewUser(new User(user));
        }
    }

    public static void setUsersRequestInProcess(boolean usersRequestInProcess) {
        Network.usersRequestInProcess = usersRequestInProcess;
    }

    public static void setChannelsRequestInProcess(boolean channelsRequestInProcess) {
        Network.channelsRequestInProcess = channelsRequestInProcess;
    }

    public static void setMessagesRequestInProcess(boolean messagesRequestInProcess) {
        Network.messagesRequestInProcess = messagesRequestInProcess;
    }

    public static boolean isUsersRequestInProcess() {
        return usersRequestInProcess;
    }

    public static boolean isChannelsRequestInProcess() {
        return channelsRequestInProcess;
    }

    public static boolean isMessagesRequestInProcess() {
        return messagesRequestInProcess;
    }

    public static void setSigninRequestInProcess(boolean signinRequestInProcess) {
        Network.signinRequestInProcess = signinRequestInProcess;
    }

    public static boolean isSigninRequestInProcess() {
        return signinRequestInProcess;
    }

    public static void setSignupRequestInProcess(boolean signupRequestInProcess) {
        Network.signupRequestInProcess = signupRequestInProcess;
    }

    public static boolean isSignupRequestInProcess() {
        return signupRequestInProcess;
    }

    public static void setAllRequestError() {
        setSignupRequestInProcess(false);
        setSigninRequestInProcess(false);
        setChannelsRequestInProcess(false);
        setMessagesRequestInProcess(false);
        setSignupRequestInProcess(false);
    }
}
