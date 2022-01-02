package GUI;

import javax.swing.*;
import java.awt.event.*;
import client.*;

public class SignUp {
    private JTextField pseudoTF;
    private JPasswordField passwordTF;
    private JPasswordField confirmTF;
    private JPanel signUpPanel;
    private JLabel pseudoLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JButton connexionButton;
    private JButton returnButton;
    private JLabel differentPasswordError;
    private JLabel pseudoError;
    private JLabel emptyError;

    public SignUp() {
        differentPasswordError.setVisible(false);
        pseudoError.setVisible(false);
        emptyError.setVisible(false);
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
                differentPasswordError.setVisible(false);
                pseudoError.setVisible(false);
                emptyError.setVisible(false);
                String pseudo = pseudoTF.getText();
                String password = new String(passwordTF.getPassword());
                String confirmation = new String(confirmTF.getPassword());
                if(pseudo.length() == 0 || password.length() == 0 || confirmation.length() == 0) {
                    emptyError.setVisible(true);
                    return;
                }
                if(!password.equals(confirmation)) {
                    differentPasswordError.setVisible(true);
                } else {
                    LocalSystem system = LocalSystem.getSystem();
                    boolean success = system.addUser(pseudo, password);
                    if(success) {
                        MainFrame main = MainFrame.getMainFrame();
                        main.goToUserMain();
                    } else {
                        pseudoError.setVisible(true);
                    }
                }
            }
        });
    }

    public JPanel getPanel() {
        return signUpPanel;
    }
}
