package client;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {
    private String ipAdress;
    private String pseudo;
    private String password;

    public User(String pseudo, String password) throws UnknownHostException {
        this.ipAdress = InetAddress.getLocalHost().getHostAddress();
        this.password = password;
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public String getIpAdress() { return this.ipAdress; }

    public String getPassword() { return this.password; }
}
