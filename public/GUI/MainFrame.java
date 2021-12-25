package GUI;

import Network.Network;
import client.*;

import javax.swing.*;
import java.awt.*;

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

    public static MainFrame getMainFrame() {
        if(mainFrame == null)
            mainFrame = new MainFrame();
        return mainFrame;
    }

    private MainFrame() {
        this.setContentPane(loadingPanel);
        this.setTitle("Sluck");
        Image icon = Toolkit.getDefaultToolkit().getImage("GUI/src/icon.JPG");
        this.setIconImage(icon);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setVisible(true);
    }

    public void goToMenu() {
        this.setContentPane(connexionMenuPanel);
        this.revalidate();
    }

    public void goToLogIn() {
        this.setContentPane(logInPanel);
        this.revalidate();
    }

    public void goToSignUp() {
        this.setContentPane(signUpPanel);
        this.revalidate();
    }

    public void goToUserMain() {
        userMain.setUserList();
        userMain.setChannelList();
        this.setContentPane(userMainPanel);
        this.revalidate();
    }

    public void updateChannel(Channel channel) {
        userMain.setMessageList(channel.getName());
        this.revalidate();
    }

    public void updateChannelList() {
        userMain.setChannelList();
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
