package csi3471.group5.gui;

import csi3471.group5.MenuCreator;
import csi3471.group5.RoomType;
import csi3471.group5.SystemHandler;
import csi3471.group5.db.DBStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The front-end interaction for logging in a guest or employee.
 * Is a CleverCards so can switch to other cards.
 */
public class LoginGUI extends CleverCards{
    private JTextField username;
    private JPasswordField password;
    private JCheckBox isEmployee;

    /**
     * Establishes places to indicate guest's or employee's username, passowrd,
     * if they are a guest or employee, and a login button.
     */
    @Override
    public void init() {
        this.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        this.setVisible(true);

        ActionListener loginAL = new ActionListener() {
            /**
             * When button is clicked, if a valid guest/employee in entered they'll be loggen in,
             * else there's an error message.
             * @param e the event to be processed
             */
            public void actionPerformed(ActionEvent e) {
                String strUsername = username.getText();
                String strPassword = password.getText();
                boolean checkIsEmployee = isEmployee.isSelected();

                boolean success = SystemHandler.handler().login(strUsername, strPassword, checkIsEmployee);
                if(success){
                    JOptionPane.showMessageDialog(null, "Success. You logged in");
                    MenuCreator.switchCard("RESERVATIONLIST");
                    SystemHandler.handler().setLoggedIn(true);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Failed. Invalid username or password");
                }
            }
        };

        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener(loginAL);

        JButton createAccButton = new JButton("CREATE NEW GUEST ACCOUNT!");
        createAccButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel gui = new AddGuestUI();
                gui.setPreferredSize(new Dimension(500, 500));
                JOptionPane.showMessageDialog(null, gui, "Create Guest Account", JOptionPane.PLAIN_MESSAGE);
            }
        });

        username = new JTextField(16);
        password = new JPasswordField(16);
        isEmployee = new JCheckBox("I'm an employee!");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        username.setMaximumSize(new Dimension(Integer.MAX_VALUE, username.getPreferredSize().height));
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, password.getPreferredSize().height));


        // Add buttons to the frame
        this.add(usernameLabel);
        this.add(username);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(passLabel);
        this.add(password);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(isEmployee);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(loginButton);
        this.add(Box.createRigidArea(new Dimension(0,80)));

        JLabel newHere = new JLabel("I'm new here:");
        this.add(newHere);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(createAccButton);
    }
}
