package csi3471.group5.gui;

import csi3471.group5.Employee;
import csi3471.group5.Guest;
import csi3471.group5.MenuCreator;
import csi3471.group5.SystemHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyOtherProfileGUI extends CleverCards{

    @Override
    protected void init() {
        this.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        this.setVisible(true);

        JTextField username = new JTextField(16);
        JLabel usernameLabel = new JLabel("Search Username:");

        String[] optionStrings = { "Guest", "Employee"};

        //Create the combo box, select item at index 4.
        //Indices start at 0, so 4 specifies the pig.
        JLabel whatType = new JLabel("Guest or Employee?");
        JComboBox personList = new JComboBox(optionStrings);
        personList.setSelectedIndex(0);


        JButton modifyButton = new JButton("MODIFY");
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchName = username.getText();

                //if the person is a guest
                if(personList.getSelectedIndex() == 0){
                    Guest g = SystemHandler.handler().validGuest(searchName);
                    if(g!= null) {
                        JPanel gui = new ModifyProfileGUI(g, true);
                        gui.setPreferredSize(new Dimension(500, 500));
                        JOptionPane.showMessageDialog(null, gui, "Modify Guest", JOptionPane.PLAIN_MESSAGE);
                        // wait for the dialog to close
                        refresh();
                    } else{
                        Object[] options = {"OK"};
                        JOptionPane.showOptionDialog(null, "Could not find guest",
                                "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, options, options[0]);
                    }
                } else{
                    Employee em = SystemHandler.handler().validEmployee(searchName);
                    if(em != null) {
                        JPanel gui = new ModifyProfileGUI(em, false);
                        gui.setPreferredSize(new Dimension(500, 500));
                        JOptionPane.showMessageDialog(null, gui, "Modify Employee", JOptionPane.PLAIN_MESSAGE);
                        // wait for the dialog to close
                        refresh();
                    } else{
                        Object[] options = {"OK"};
                        JOptionPane.showOptionDialog(null, "Could not find employee",
                                "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, options, options[0]);
                    }
                }
            }
        } );

        this.add(MenuCreator.createMenuBar());

        // Add buttons to the frame (and spaces between buttons)
        this.add(personList);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(usernameLabel);
        this.add(username);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(modifyButton);
    }
}
