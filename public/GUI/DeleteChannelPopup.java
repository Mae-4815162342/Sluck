package GUI;

import client.LocalSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteChannelPopup {
    private JButton cancelButton;
    private JPanel deletePanel;
    private JButton confirmButton;

    private String channelName;

    private LocalSystem system = LocalSystem.getSystem();

    public DeleteChannelPopup(String channel) {
        channelName = channel;
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((JFrame) deletePanel.getTopLevelAncestor()).dispose();
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                system.deleteChannel(channelName);
                MainFrame main = MainFrame.getMainFrame();
                main.getUserMain().setDeleting(false);
                ((JFrame) deletePanel.getTopLevelAncestor()).dispose();
            }
        });
    }

    public JPanel getDeletePanel() {
        return deletePanel;
    }
}
