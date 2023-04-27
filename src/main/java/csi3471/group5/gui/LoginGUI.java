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
 *
 */
public class LoginGUI extends CleverCards{
    private JTextField username;
    private JPasswordField password;
    private JCheckBox isEmployee;

    @Override
    public void init() {

        this.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        this.setVisible(true);

        ActionListener loginAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strUsername = username.getText();
                String strPassword = password.getText();
                boolean checkIsEmployee = isEmployee.isSelected();

                boolean success = SystemHandler.handler().login(strUsername, strPassword, checkIsEmployee);
                if(success){
                    Object[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, "Success. You logged in",
                            "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[0]);
                    MenuCreator.switchCard("RESERVATIONLIST");
                }
                else{
                    Object[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, "Failed. Invalid username or password",
                            "Failure", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[0]);
                }

            }
        };

        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener(loginAL);
        //loginButton.addActionListener(new LoginGUI.RegActionListener());

        username = new JTextField(16);
        password = new JPasswordField(16);
        isEmployee = new JCheckBox("I'm an employee!");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        username.setMaximumSize(new Dimension(Integer.MAX_VALUE, username.getPreferredSize().height));
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, password.getPreferredSize().height));

//        this.add(MenuCreator.createMenuBar());

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
    }
}
