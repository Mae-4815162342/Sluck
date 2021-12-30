package app.Model;

import java.util.UUID;

public class User {
    private UUID uuid;
    private String username;
    private String password;

    public User(){
    }

    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername(){return username;}
}
