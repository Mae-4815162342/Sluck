package app.Model;

import java.io.Serializable;

public class User implements Serializable {
    private int uuid;
    private String username;
    private String password;

    public User(){
    }

    public int getUuid() {
        return uuid;
    }
    public void setUuid(int uuid) {
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
    public String toString(){
        return "UUID : " + uuid + ", Username : " + username;
    }
}
