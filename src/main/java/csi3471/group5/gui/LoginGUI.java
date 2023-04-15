package csi3471.group5.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class LoginGUI extends CleverCards{
    private ArrayList<String> textBoxInputs;
    private static JTextField username, password;
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

        JButton loginButton = new JButton("LOGIN");
        //loginButton.addActionListener(new LoginGUI.RegActionListener());
/*
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
         */
    }

    //make action listener function
}
