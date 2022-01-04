package client;
import java.net.UnknownHostException;

public class User {
    private int uid;
    private String pseudo;
    private String password;

    public User(String pseudo, String password) throws UnknownHostException {
        this.password = password;
        this.pseudo = pseudo;
    }

    public User(app.Model.User user) {
        this.uid = user.getUuid();
        this.pseudo = user.getUsername();
        this.password = user.getPassword();
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public String getPassword() { return this.password; }

    public int getUid() { return this.uid; }
}
