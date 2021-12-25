package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnexionMenu {
    private JPanel connexionMenuPanel;
    private JLabel connexionMenuLogo;
    private JButton logInButton;
    private JButton signUpButton;

    public ConnexionMenu() {
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame main = MainFrame.getMainFrame();
                main.goToLogIn();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame main = MainFrame.getMainFrame();
                main.goToSignUp();
            }
        });
    }

    public JPanel getPanel() {
        return connexionMenuPanel;
    }
}
