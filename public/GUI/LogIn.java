package GUI;

import client.LocalSystem;
import client.User;

import javax.swing.*;
import java.awt.event.*;

public class LogIn {
    private JLabel logoLabel;
    private JTextField pseudoTextField;
    private JPasswordField passwordTextField;
    private JButton connexionButton;
    private JPanel logInPanel;
    private JLabel pseudoLabel;
    private JLabel passwordLabel;
    private JButton returnButton;
    private JLabel wrongPasswordError;
    private JLabel inexistantUserError;

    public LogIn() {
        wrongPasswordError.setVisible(false);
        inexistantUserError.setVisible(false);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame main = MainFrame.getMainFrame();
                main.goToMenu();
            }
        });
        connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wrongPasswordError.setVisible(false);
                inexistantUserError.setVisible(false);
                String pseudo = pseudoTextField.getText();
                String password = new String(passwordTextField.getPassword());
                LocalSystem system = LocalSystem.getSystem();
                User user = system.getUser(pseudo);
                if(user == null) {
                    inexistantUserError.setVisible(true);
                } else {
                    if(!user.getPassword().equals(password)) {
                        wrongPasswordError.setVisible(true);
                    } else {
                        system = LocalSystem.getSystem();
                        system.connect(user);
                        MainFrame main = MainFrame.getMainFrame();
                        main.goToUserMain();
                    }
                }
            }
        });
        KeyAdapter enterPressed = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    wrongPasswordError.setVisible(false);
                    inexistantUserError.setVisible(false);
                    String pseudo = pseudoTextField.getText();
                    String password = new String(passwordTextField.getPassword());
                    LocalSystem system = LocalSystem.getSystem();
                    User user = system.getUser(pseudo);
                    if (user == null) {
                        inexistantUserError.setVisible(true);
                    } else {
                        if (!user.getPassword().equals(password)) {
                            wrongPasswordError.setVisible(true);
                        } else {
                            system = LocalSystem.getSystem();
                            system.connect(user);
                            MainFrame main = MainFrame.getMainFrame();
                            main.goToUserMain();
                        }
                    }
                }
            }
        };
        pseudoTextField.addKeyListener(enterPressed);
        passwordTextField.addKeyListener(enterPressed);
        connexionButton.addKeyListener(enterPressed);
    }

    public JPanel getPanel() {
        return logInPanel;
    }
}
