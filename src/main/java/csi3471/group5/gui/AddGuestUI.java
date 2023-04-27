package csi3471.group5.gui;

import csi3471.group5.MenuCreator;
import csi3471.group5.SystemHandler;
import csi3471.group5.db.DBStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddGuestUI extends CleverCards {
    private ArrayList<String> textBoxInputs;
    private static JTextField username, password, phone;
    public ArrayList<String> getTextBoxInputs() {
        return textBoxInputs;
    }

    @Override
    public void init() {
        JPanel mainContent = new JPanel();
        this.setLayout(new BorderLayout());
        mainContent.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(mainContent, BoxLayout.Y_AXIS);
        mainContent.setLayout(boxLayout);
        mainContent.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        mainContent.setVisible(true);

        JButton registerButton = new JButton("REGISTER");
        registerButton.addActionListener(new RegActionListener());

        username = new JTextField(16);
        password = new JTextField(16);
        phone = new JTextField(16);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel phoneLabel = new JLabel("Phone Number:");

        this.add(MenuCreator.createMenuBar(),BorderLayout.NORTH);

        //adjust format
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        username.setMaximumSize(new Dimension(Integer.MAX_VALUE, username.getPreferredSize().height));
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, password.getPreferredSize().height));
        phone.setMaximumSize(new Dimension(Integer.MAX_VALUE, phone.getPreferredSize().height));



        // Add buttons to the frame (and spaces between buttons)
        mainContent.add(usernameLabel);
        mainContent.add(username);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        mainContent.add(passLabel);
        mainContent.add(password);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        mainContent.add(phoneLabel);
        mainContent.add(phone);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        mainContent.add(registerButton);
        this.add(mainContent, BorderLayout.CENTER);
    }

    private static final class RegActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String strUsername = username.getText();
            String strPassword = password.getText();
            String strPhone = phone.getText();

            boolean success = SystemHandler.handler().registerGuest(strUsername, strPassword, strPhone);
            if(success == true){
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, "Guest is now registered",
                        "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
            }
            else{
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, "Failed. Username already in use",
                        "Failure", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
            }

        }
    }
}
