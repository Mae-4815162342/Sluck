package app.Model;

import java.io.Serializable;
import java.util.List;

public class Channel implements Serializable{
    private String name;
    private int cuid;
    private int adminUuid;
    private List<Message> messages;
    private User admin;

    public Channel(){
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCuid() {
        return cuid;
    }

    public void setCuid(int cuid) {
        this.cuid = cuid;
    }
    public int getAdminUuid() {
        return adminUuid;
    }

    public void setAdminUuid(int adminUuid) {
        this.adminUuid = adminUuid;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public User getAdmin(){
        return admin;
    }

    public List<Message> getMessages(){     
        return messages;
    }
    public String toString(){
        return "CUID : " + cuid + ", adminID : "+ adminUuid +", #" + name;
    }
}
