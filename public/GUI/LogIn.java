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
                if(pseudo.isBlank()) {
                    inexistantUserError.setVisible(true);
                    return;
                }
                if(password.isBlank()) {
                    wrongPasswordError.setVisible(true);
                    return;
                }
                boolean success = system.connect(pseudo, password);
                if(success) {
                    MainFrame main = MainFrame.getMainFrame();
                    main.goToUserMain();
                } else {
                    inexistantUserError.setVisible(true);
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
                    boolean success = system.connect(pseudo, password);
                    if(pseudo.isBlank()) {
                        inexistantUserError.setVisible(true);
                        return;
                    }
                    if(password.isBlank()) {
                        wrongPasswordError.setVisible(true);
                        return;
                    }
                    if(success) {
                        MainFrame main = MainFrame.getMainFrame();
                        main.goToUserMain();
                    } else {
                        inexistantUserError.setVisible(true);
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
