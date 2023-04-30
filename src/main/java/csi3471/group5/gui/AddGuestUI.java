package csi3471.group5.gui;

import csi3471.group5.MenuCreator;
import csi3471.group5.SystemHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGuestUI extends CleverCards {
    private static JTextField username, password, phone;

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
            if(username.getText().matches(".*\\s.*")){ //"I think this is it" -  Brendon
                JOptionPane.showMessageDialog(null, "Username can't contain whitespace");
                return;
            }
            if(username.getText().length() == 0){ //"I think this is it" -  Brendon
                JOptionPane.showMessageDialog(null, "Invalid Username");
                return;
            }
            if(username.getText().contains(",")){ //"I think this is it" -  Brendon
                JOptionPane.showMessageDialog(null, "Username can't contain commas");
                return;
            }
            if(password.getText().matches(".*\\s.*")){
                JOptionPane.showMessageDialog(null, "Password can't contain whitespace");
                return;
            }
            if(password.getText().length() == 0){ //"I think this is it" -  Brendon
                JOptionPane.showMessageDialog(null, "Invalid Password");
                return;
            }

            boolean validNum = false;
            if(strPhone.matches("[0-9]+") && strPhone.length() == 10){
                validNum = true;
            }

            if(validNum) {
                boolean success = SystemHandler.handler().registerGuest(strUsername, strPassword, strPhone);
                if (success == true) {
                    JOptionPane.showMessageDialog(null, "Guest is now registered");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed. Username already in use");
                }
            } else{
                JOptionPane.showMessageDialog(null, "Please enter a valid phone number. Do not include the international extension");
            }
        }
    }
}
