package csi3471.group5.gui;

import csi3471.group5.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Allows a user to modify their own profile (username/password)
 */
public class ModifyProfileGUI extends CleverCards{
    private static JTextField username, phone;
    private static JPasswordField password;
    LoginUser user = null;
    boolean isGuest;

    /**
     * Constructor to create an instance of ModifyProfileGUI
     */
    public ModifyProfileGUI() {}

    /**
     * Sets the information for the current user
     * @param user username of the current user
     * @param isGuest ensuring that user is not an employee
     */
    public ModifyProfileGUI(LoginUser user, boolean isGuest) {
        this.user = user;
        this.isGuest = isGuest;
        refresh();
    }

    /**
     * @return true, if you are modifying yourself
     *         false, otherwise
     */
    private boolean modSelf() {return user == null;}

    /**
     * @return guest user, if username was in guest database
     */
    private Guest getGuest() {return (Guest)user;}

    /**
     * @return employee user, if username was in employee database
     */
    private Employee getEmployee() {return (Employee)user;}

    /**
     * initializes the front-end Swing screen for modifying own profile
     */
    @Override
    public void init() {
        JPanel mainPanel;
        this.setLayout(new BorderLayout());
        if(modSelf()) {
            this.add(MenuCreator.createMenuBar(), BorderLayout.NORTH);
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
        this.add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * @param guest The guest for whom the //FIXME
     * @return
     */
    private JPanel modGuestFields(Guest guest) {
        JPanel mainPanel = new JPanel();
//        if(modSelf()) {
//            mainPanel.add(MenuCreator.createMenuBar());
//        }
        mainPanel.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxLayout);
        mainPanel.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        mainPanel.setVisible(true);

        JButton modifyButton = new JButton("MODIFY");
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(guest.getUsername().equals(username.getText()) || SystemHandler.handler().validGuest(username.getText()) == null) {
                    guest.setUsername(username.getText());
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Successfully Modified!",
                            "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                            options, options[0]);
                }else{
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Guest username already in use.",
                            "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                }

                if(modSelf() || isAdmin()) {
                    if(password.getText() != null && !password.getText().equals("")) {
                        System.out.println("Setting Password");
                        guest.setPassword(password.getText());
                    }
                }
                guest.setPhoneNumber(phone.getText());
            }
        } );

        username = new JTextField(16);
        username.setText(guest.getUsername());
        password = new JPasswordField(16);
        phone = new JTextField(16);
        phone.setText(guest.getPhoneNumber());

        JLabel usernameLabel = new JLabel("New Username:");
        JLabel passLabel = new JLabel("New Password:");
        JLabel phoneLabel = new JLabel("New Phone Number:");

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
        BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxLayout);
        mainPanel.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        mainPanel.setVisible(true);

        JButton modifyButton = new JButton("MODIFY");
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(employee.getUsername().equals(username.getText()) || SystemHandler.handler().validEmployee(username.getText()) == null) {
                    employee.setUsername(username.getText());
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Successfully modified",
                            "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[0]);
                } else{
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Employee username already in use.",
                            "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                }
                if(modSelf() || isAdmin()) {
                    if(password.getText() != null && !password.getText().equals("")) {
                        employee.setPassword(password.getText());
                        Object[] options = {"OK"};
                    }
                }
            }
        } );

        username = new JTextField(16);
        username.setText(employee.getUsername());

        password = new JPasswordField(16);

        JLabel usernameLabel = new JLabel("New Username:");
        JLabel passLabel = new JLabel("New Password:");


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