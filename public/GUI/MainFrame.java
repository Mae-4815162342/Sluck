package GUI;

import Network.Network;
import client.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.CharsetEncoder;

public class MainFrame extends JFrame {

    private Loading load = new Loading();
    private ConnexionMenu menu = new ConnexionMenu();
    private LogIn logIn = new LogIn();
    private SignUp signUp = new SignUp();
    private UserMain userMain = new UserMain();
    private JPanel loadingPanel = load.getPanel();
    private JPanel connexionMenuPanel = menu.getPanel();
    private JPanel logInPanel = logIn.getPanel();
    private JPanel signUpPanel = signUp.getPanel();
    private JPanel userMainPanel = userMain.getPanel();

    private static MainFrame mainFrame;
    private static boolean hasBeenInitialized = false;
    private static boolean hasMessagesInitialized = false;
    private static boolean hasChannelsInitialized = false;

    public static MainFrame getMainFrame() {
        if(mainFrame == null)
            mainFrame = new MainFrame();
        return mainFrame;
    }

    private MainFrame() {
        this.setContentPane(loadingPanel);
        this.setTitle("Sluck");
        Image icon = Toolkit.getDefaultToolkit().getImage("public/GUI/src/icon.JPG");
        this.setIconImage(icon);
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                LocalSystem system = LocalSystem.getSystem();
                if(system.getCurrentUser() != null) system.disconnect();
            }
        });
        this.setVisible(true);
    }

    public void goToMenu() {
        this.setContentPane(connexionMenuPanel);
    }

    public void goToLogIn() {
        LocalSystem system = LocalSystem.getSystem();
        if(!hasBeenInitialized) {
            system.updateUsers();
            hasBeenInitialized = true;
        }
        this.setContentPane(logInPanel);
        this.revalidate();
    }

    public void goToSignUp() {
        LocalSystem system = LocalSystem.getSystem();
        if(!hasBeenInitialized) {
            system.updateUsers();
            hasBeenInitialized = true;
        }
        this.setContentPane(signUpPanel);
        this.revalidate();
    }

    public void goToUserMain() {
        LocalSystem system = LocalSystem.getSystem();
        if(!hasMessagesInitialized){
            system.updateMessages();
            hasMessagesInitialized = true;
        }
        if(!hasChannelsInitialized) {
            system.updateChannels();
            hasChannelsInitialized = true;
        }
        userMain.setUserList();
        userMain.setChannelList();
        this.setContentPane(userMainPanel);
        this.revalidate();
        this.repaint();
    }

    public void updateChannelList() {
        userMain.setChannelList();
    }

    public void updateUserList() {
        userMain.setUserList();
    }

    public void updateMessageList() {
        userMain.setMessageList(getCurrentChannel().getName());
    }

    public UserMain getUserMain() {
        return userMain;
    }

    public Channel getCurrentChannel() {
        return userMain.getCurrentChannel();
    }

    public void setCurrentChannel(Channel channel) {
        userMain.setCurrentChannel(channel);
    }

    public static void main(String[] args) {
        try {
            mainFrame = new MainFrame();
            mainFrame.goToMenu();
            Network.connexion();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Zut alors ??a marche pas");
        }
    }

}
