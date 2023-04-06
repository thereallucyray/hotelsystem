package csi3471.group5.gui;

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
        this.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        this.setVisible(true);

        JButton registerButton = new JButton("REGISTER");
        registerButton.addActionListener(new RegActionListener());

        username = new JTextField(16);
        password = new JTextField(16);
        phone = new JTextField(16);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel phoneLabel = new JLabel("Phone Number:");

        // Add buttons to the frame (and spaces between buttons)
        this.add(usernameLabel);
        this.add(username);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(passLabel);
        this.add(password);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(phoneLabel);
        this.add(phone);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(registerButton);
    }

    public AddGuestUI() {
        init();
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
