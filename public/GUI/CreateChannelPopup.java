package GUI;

import client.LocalSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreateChannelPopup {
    private JTextField channelNameInput;
    private JButton createButton;
    private JPanel createChannelPanel;
    private JLabel popupLabel;
    private JLabel createChannelError;
    private LocalSystem system = LocalSystem.getSystem();


    public CreateChannelPopup() {
        this.createChannelError.setVisible(false);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!system.checkChannelExistence(channelNameInput.getText())) {
                    system.createChannel(channelNameInput.getText());
                    ((JFrame) createChannelPanel.getTopLevelAncestor()).dispose();
                } else {
                    createChannelError.setVisible(true);
                }
            }
        });
        channelNameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (channelNameInput.getText().length() > 0) {
                        if (!system.checkChannelExistence(channelNameInput.getText())) {
                            system.createChannel(channelNameInput.getText());
                            ((JFrame) createChannelPanel.getTopLevelAncestor()).dispose();
                        } else {
                            createChannelError.setVisible(true);
                        }
                    }
                }
            }
        });
    }

    public void setErrorVisible() {
        createChannelError.setVisible(true);
    }

    public JPanel getCreateChannelPanel() {
        return createChannelPanel;
    }
}
