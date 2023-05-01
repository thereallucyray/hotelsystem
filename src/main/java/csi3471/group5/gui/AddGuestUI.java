package csi3471.group5.gui;

import csi3471.group5.MenuCreator;
import csi3471.group5.SystemHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class creates the user interface to add a guest or employee account
 */
public class AddGuestUI extends CleverCards {
    private JTextField username, phone;
    private JPasswordField password;
    private JComboBox guestoemployee;
    private JCheckBox isAdmin;
    private boolean guestSelected = true;

    /**
     * This method initializes the Swing Card display for the Add User GUI
     */
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

        if(guestoemployee == null) {
            guestoemployee = new JComboBox<String>(new String[]{"Guest", "Employee"});
            guestoemployee.setSelectedIndex(0);
            guestSelected = true;
        }
        guestoemployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(guestoemployee.getSelectedItem().equals("Guest") != guestSelected) {
                    guestSelected = !guestSelected;
                    refresh();
                }
            }
        });

        if(!isAdmin()) {
            guestSelected = true;
        }

        username = new JTextField(16);
        password = new JPasswordField(16);
        phone = new JTextField(16);

        isAdmin = new JCheckBox("is an admin");

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel phoneLabel = new JLabel("Phone Number:");

        if(SystemHandler.handler().isLoggedIn()) {
            this.add(MenuCreator.createMenuBar(), BorderLayout.NORTH);
        }
        //adjust format
        guestoemployee.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        phoneLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        isAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);

        guestoemployee.setMaximumSize(new Dimension(Integer.MAX_VALUE, guestoemployee.getPreferredSize().height));
        username.setMaximumSize(new Dimension(Integer.MAX_VALUE, username.getPreferredSize().height));
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, password.getPreferredSize().height));
        phone.setMaximumSize(new Dimension(Integer.MAX_VALUE, phone.getPreferredSize().height));

        // Add buttons to the frame (and spaces between buttons)
        if(isAdmin()) {
            mainContent.add(guestoemployee);
        }
        mainContent.add(usernameLabel);
        mainContent.add(username);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        mainContent.add(passLabel);
        mainContent.add(password);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        if(guestSelected) {
            mainContent.add(phoneLabel);
            mainContent.add(phone);
            mainContent.add(Box.createRigidArea(new Dimension(0, 10)));
        } else {
            mainContent.add(isAdmin);
            mainContent.add(Box.createRigidArea(new Dimension(0,10)));
        }
        mainContent.add(registerButton);
        this.add(mainContent, BorderLayout.CENTER);
    }

    /**
     * This class creates an action listener object to be used when the user clicks the "register"
     * button, causing all fields to be checked for correctness and then creating a new guest, admin
     * or employee account.
     */
    private final class RegActionListener implements ActionListener {
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

            if(guestSelected) {
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
            } else {
                boolean admin = isAdmin.isSelected();
                boolean success = SystemHandler.handler().registerEmployee(strUsername, strPassword, admin);
                if (success == true) {
                    JOptionPane.showMessageDialog(null, "Employee is now registered");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed. Username already in use");
                }
            }
        }
    }
}
