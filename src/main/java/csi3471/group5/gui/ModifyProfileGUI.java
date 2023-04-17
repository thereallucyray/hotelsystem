package csi3471.group5.gui;

import csi3471.group5.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModifyProfileGUI extends CleverCards{
    private ArrayList<String> textBoxInputs;
    private static JTextField username, password, phone;
    public ArrayList<String> getTextBoxInputs() {
        return textBoxInputs;
    }
    LoginUser user = null;
    boolean isGuest;

    public ModifyProfileGUI() {}
    public ModifyProfileGUI(LoginUser user, boolean isGuest) {
        this.user = user;
        this.isGuest = isGuest;
    }
    private boolean modSelf() {return user == null;}
    private Guest getGuest() {return (Guest)user;}
    private Employee getEmployee() {return (Employee)user;}
    @Override
    public void init() {
        JPanel mainPanel;
        if(modSelf()) {
            if(SystemHandler.handler().isEmployeeFacing()) {
                mainPanel = modEmployeeFields((Employee) SystemHandler.handler().getLoggedInUser());
            } else {
                mainPanel = modGuestFields((Guest) SystemHandler.handler().getLoggedInUser());
            }
        } else {
            if(isGuest) {
                mainPanel = modGuestFields(getGuest());
            } else {
                mainPanel = modEmployeeFields(getEmployee());
            }
        }

        this.add(mainPanel);
    }

    private JPanel modGuestFields(Guest guest) {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxLayout);
        mainPanel.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        mainPanel.setVisible(true);

        JButton modifyButton = new JButton("MODIFY");
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(SystemHandler.handler().validGuest(username.getText()) == null) {
                    guest.setUsername(username.getText());
                }else{
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Guest username already in use.",
                            "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                }

                if(modSelf() || isAdmin()) {
                    if(password.getText() != null) {
                        guest.setPassword(password.getText());
                    }
                }
                guest.setPhoneNumber(phone.getText());
            }
        } );

        username = new JTextField(16);
        username.setText(guest.getUsername());
        password = new JTextField(16);
        phone = new JTextField(16);
        phone.setText(guest.getPhoneNumber());

        JLabel usernameLabel = new JLabel("New Username:");
        JLabel passLabel = new JLabel("New Password:");
        JLabel phoneLabel = new JLabel("New Phone Number:");

        mainPanel.add(MenuCreator.createMenuBar());

        // Add buttons to the frame (and spaces between buttons)
        mainPanel.add(usernameLabel);
        mainPanel.add(username);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        if(modSelf() || isAdmin()) {
            mainPanel.add(passLabel);
            mainPanel.add(password);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        mainPanel.add(phoneLabel);
        mainPanel.add(phone);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(modifyButton);

        return mainPanel;
    }
    private JPanel modEmployeeFields(Employee employee) {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxLayout);
        mainPanel.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        mainPanel.setVisible(true);

        JButton modifyButton = new JButton("MODIFY");
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(SystemHandler.handler().validEmployee(username.getText()) == null) {
                    employee.setUsername(username.getText());
                } else{
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Employee username already in use.",
                            "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                }
                if(modSelf() || isAdmin()) {
                    if(password.getText() != null) {
                        employee.setPassword(password.getText());
                    }
                }
            }
        } );

        username = new JTextField(16);
        username.setText(employee.getUsername());

        password = new JTextField(16);

        JLabel usernameLabel = new JLabel("New Username:");
        JLabel passLabel = new JLabel("New Password:");

        mainPanel.add(MenuCreator.createMenuBar());

        // Add buttons to the frame (and spaces between buttons)
        mainPanel.add(usernameLabel);
        mainPanel.add(username);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        if(modSelf() || isAdmin()) {
            mainPanel.add(passLabel);
            mainPanel.add(password);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        mainPanel.add(modifyButton);

        return mainPanel;
    }
}