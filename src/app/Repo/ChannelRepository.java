package app.Repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import app.Model.*;

public class ChannelRepository {
    private static Connection con = null;

    public static Connection connexion() throws Exception {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = null;
        //pscale_pw_dqyb4d5E_Eb0gdSxd4mwOH3LZekskdPIcVC5fylFNMc
        //main-2022-jan-3-yg8awu
        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://16ekkl1kiyxk.eu-west-2.psdb.cloud/sluckdb?sslMode=VERIFY_IDENTITY",
                "8zeoxqw0gspt", "pscale_pw_dqyb4d5E_Eb0gdSxd4mwOH3LZekskdPIcVC5fylFNMc");
            System.out.println("Connexion avec la base de donnees etablie");
        } catch (SQLException e) {
            throw e;
        }
        return con;
    }

    public static void setConnexion(Connection c){
        con = c;
    }


    public static void addMessage(Message message){
       
        try {
            if (con == null) {
                con = connexion();
            }
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO Message VALUES(" + message.getId() + ",'" + message.getMessages() + "'," + message.getUser().getId() + ","+ message.getChannel().getId() +")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int getNbMessage(){
        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            ResultSet usersRes = stmt.executeQuery("SELECT COUNT(*) FROM Message");
            usersRes.next();
            return usersRes.getInt("COUNT(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return -1;
	}

    public static Channel getChannel(String channelTag){
        Channel ch = null;
        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select * from Channel where Channel.name = '"+ channelTag+"'");
            while (res.next()) {
                ch = new Channel(res.getInt("cuid"),res.getString("name"),UserRepository.getUserById(res.getInt("adminId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ch;
    }

    public static void updateChannelFromDB(Channel channel){
        //update le channel du user en cherchant les nouveaux messages se trouvants dans la BDD
        /*
        // TODO : mettre à jour la liste de message locale du channel
        */

        List<Message> l = new ArrayList<>();

        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM Message WHERE channelId = " + channel.getId());
            while (res.next()) {
                l.add(new Message(UserRepository.getUserById(res.getInt("userId")),res.getString("text"),channel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        channel.setMessages(l);
    }

    /*
        SELECT Channel.name, User.username, Message.text 
        FROM Message
        INNER JOIN User
        ON User.uuid = Message.userId
        INNER JOIN Channel
        ON Channel.cuid = Message.channelId AND Channel.cuid = 1;
    */

    public static void addChannel(Channel channel){
        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO Channel VALUES("+channel.getId()+",'"+channel.getTag()+"',"+channel.getAdmin().getId()+")");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteChannel(Channel channel){
        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM Channel WHERE cuid = " + channel.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getNbChannel(){
        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            ResultSet channelRes = stmt.executeQuery("SELECT COUNT(*) FROM Channel");
            channelRes.next();
            int nbChannels = channelRes.getInt("COUNT(*)");
            return nbChannels;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return -1;
    }

    public static void updateChannel(Channel channel){
        //update le channel DB avec les messages venant d'etre envoyés par le user | Il faudrait une liste de nouveau message qui sont ensuite envoyés à cette fonction
        /*

        for(m in channel.getMessages()){
            addMessage(channel,m);
        }
        
        
        */
    }
}
