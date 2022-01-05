package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Locale;

import client.*;

public class UserMain {
    private JPanel userMainPanel;
    private JButton addChannelButton;
    private JButton searchButton;
    private JTextField searchTextField;
    private JTextField userInputField;
    private JButton sendButton;
    private JList channelList;
    private JList userList;
    private JList messageList;
    private JLabel Channels;
    private JLabel Users;
    private JScrollPane channelScroll;
    private JScrollPane userScroll;
    private JScrollPane messageScroll;
    private JButton disconnectButton;
    private JButton deleteChannelButton;
    private JLabel deleteLabel;
    private JLabel notYourChannelErrorLabel;
    private static Channel currentChannel;
    private LocalSystem system = LocalSystem.getSystem();
    private boolean isDeleting = false;

    public UserMain() {
        DefaultListModel<String> messages = new DefaultListModel<>();
        messages.addElement("   Select a channel to start chatting !");
        messageList.setModel(messages);
        deleteLabel.setVisible(false);
        notYourChannelErrorLabel.setVisible(false);
        channelList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    notYourChannelErrorLabel.setVisible(false);
                    String selected = (String) channelList.getSelectedValue();
                    if(selected.isBlank()) return;
                    selected = selected.split("#")[1];
                    System.out.println("Est selectionné " + selected);
                    if(isDeleting) {
                        if(system.getChannel(selected).getOwnerUid() == system.getCurrentUser().getUid()) {
                            JFrame popup = new JFrame();
                            DeleteChannelPopup panel = new DeleteChannelPopup(selected);
                            popup.setContentPane(panel.getDeletePanel());
                            popup.setTitle("Delete " + selected + " ?");
                            Image icon = Toolkit.getDefaultToolkit().getImage("public/GUI/src/icon.JPG");
                            popup.setIconImage(icon);
                            popup.setSize(300, 200);
                            popup.setLocationRelativeTo(null);
                            popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            try {
                                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            popup.setVisible(true);
                            popup.addWindowListener(new WindowAdapter(){
                                public void windowClosing(WindowEvent e){
                                    isDeleting = false;
                                }
                            });
                        } else {
                            notYourChannelErrorLabel.setVisible(true);
                        }
                    } else {
                        setMessageList(selected);
                    }
                }
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userInputField.getText();
                if(input.length() > 0 && currentChannel != null) {
                    system.sendMessage(input, currentChannel);
                    userInputField.setText(null);
                }
            }
        });
        KeyAdapter enterPressed = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String input = userInputField.getText();
                    if(input.length() > 0 && currentChannel != null) {
                        system.sendMessage(input, currentChannel);
                        userInputField.setText(null);
                    }
                }
            }
        };
        userInputField.addKeyListener(enterPressed);
        sendButton.addKeyListener(enterPressed);
        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(searchTextField.getText().length() == 0) setChannelList();
                else setChannelListFiltered(searchTextField.getText());
            }
        });
        addChannelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popup = new JFrame();
                CreateChannelPopup panel = new CreateChannelPopup();
                popup.setContentPane(panel.getCreateChannelPanel());
                popup.setTitle("Create my Channel !");
                Image icon = Toolkit.getDefaultToolkit().getImage("public/GUI/src/icon.JPG");
                popup.setIconImage(icon);
                popup.setSize(300, 200);
                popup.setLocationRelativeTo(null);
                popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                popup.setVisible(true);
            }
        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                system.disconnect();
                MainFrame main = MainFrame.getMainFrame();
                main.goToMenu();
            }
        });
        deleteChannelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDeleting = ! isDeleting;
                deleteLabel.setVisible(isDeleting);
            }
        });
    }

    public void setUserList() {
        DefaultListModel<String> users = new DefaultListModel<>();
        for(User user: system.getUsers()) {
            if(!user.getPseudo().equals(system.getCurrentUser().getPseudo())) {
                users.addElement("  " + user.getPseudo());
            }
        }
        if(users.isEmpty()) users.addElement("  No one else here");
        userList.setModel(users);
    }

    public void setChannelList() {
        DefaultListModel<String> channels = new DefaultListModel<>();
        for (Channel chan : system.getChannels()) {
            channels.addElement("  #" + chan.getName());
        }
        if(channels.isEmpty()) channels.addElement("    No channel found");
        channelList.setModel(channels);
    }

    public void setChannelListFiltered(String filter) {
        DefaultListModel<String> channels = new DefaultListModel<>();
        for(Channel chan: system.getChannels()) {
            if(chan.getName().toLowerCase(Locale.ROOT).contains(filter.toLowerCase(Locale.ROOT))) channels.addElement("  #" + chan.getName());
        }
        if(channels.isEmpty()) channels.addElement("    No channel found");
        channelList.setModel(channels);
    }

    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setMessageList(String channel) {
        DefaultListModel<String> messages = new DefaultListModel<>();
        currentChannel = system.getChannel(channel);
        if(currentChannel == null) {
            messages.addElement("    Error when retrieving the channel, please try again. We apologise for the inconvenience");
        } else {
            for (Message mes : currentChannel.getMessages()) {
                if (mes.getUsername().equals(system.getCurrentUser().getPseudo())) {
                    messages.addElement("me : " + mes.getContent());
                } else {
                    messages.addElement(mes.getUsername() + " : " + mes.getContent());
                }
            }
            if(messages.isEmpty()) {
                messages.addElement("    No messages have been send yet !");
            }
        }
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.white), channel);
        title.setTitleColor(Color.white);
        messageScroll.setBorder(title);
        messageList.setModel(messages);
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
        channelList.setSelectedIndex(channelList.getMaxSelectionIndex());
    }

    public JPanel getPanel() {
        return userMainPanel;
    }

    public void setDeleting(boolean deleting) {
        isDeleting = deleting;
        deleteLabel.setVisible(false);
    }
}
