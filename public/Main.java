import java.sql.*;

import app.Model.Channel;
import app.Model.Message;
import app.Model.User;
import app.Repo.ChannelRepository;
import app.Repo.UserRepository;

public class Main {
    public static Connection con = null;

    public static void main(String[] args) {
        try {
            connexion();
            ChannelRepository.setConnexion(con);
            UserRepository.setConnexion(con);
        } catch (Exception e) {
            System.out.println(e);
        }

        User one =  new User("GRud", "password");
        UserRepository.addUser(one);
        User two =  new User("Froti", "password");
        UserRepository.addUser(two);
        User ter =  new User("Frota", "password");
        UserRepository.addUser(ter);
        User t = new User("EpuiSanVa", "password");
        UserRepository.addUser(t);

        Channel c = ChannelRepository.getChannel("ChannelTestBis");

        ChannelRepository.addMessage(new Message(one, "Heloo everyone !", c));
        ChannelRepository.addMessage(new Message(ter, "whats up", c));
        ChannelRepository.addMessage(new Message(one, "fine ::)", c));
        ChannelRepository.addMessage(new Message(one, "where are the others ?", c));
        ChannelRepository.addMessage(new Message(ter, "idk", c));
        ChannelRepository.addMessage(new Message(two, "i'm here !!!!", c));
        ChannelRepository.addMessage(new Message(t, "me too SRY !", c));
        ChannelRepository.addMessage(new Message(t, "i was poopinggg", c));
        ChannelRepository.addMessage(new Message(ter, "nice ._.", c));


        
    }

    public static Connection connexion() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
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
  
}
