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
        this.setVisible(true);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                LocalSystem system = LocalSystem.getSystem();
                if(system.getCurrentUser() != null) system.disconnect();
            }
        });
    }

    public void goToMenu() {
        this.setContentPane(connexionMenuPanel);
        this.revalidate();
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
    }

    public void updateChannelList() {
        userMain.setChannelList();
<<<<<<< HEAD
        userMainPanel.repaint();
=======
>>>>>>> 637257c8c19db448a43bef82e3ef97b1e9fda296
        this.revalidate();
    }

    public void updateUserList() {
        userMain.setUserList();
<<<<<<< HEAD
        userMainPanel.repaint();
=======
>>>>>>> 637257c8c19db448a43bef82e3ef97b1e9fda296
        this.revalidate();
    }

    public void updateMessageList() {
        userMain.resetMessageList();
        userMainPanel.repaint();
        this.revalidate();
    }

<<<<<<< HEAD
=======
    public UserMain getUserMain() {
        return userMain;
    }

>>>>>>> 637257c8c19db448a43bef82e3ef97b1e9fda296
    public Channel getCurrentChannel() {
        return userMain.getCurrentChannel();
    }

    public void setCurrentChannel(Channel channel) {
        userMain.setCurrentChannel(channel);
    }

    public static void main(String[] args) {
        try {
            mainFrame = new MainFrame();
            Network.connexion();
            mainFrame.goToMenu();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Zut alors ça marche pas");
        }
    }

}
