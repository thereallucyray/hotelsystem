package csi3471.group5.gui;

import csi3471.group5.SystemHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModifyRoomGUI extends JPanel{
    private ArrayList<String> textBoxInputs;
    private static JTextField startDate, endDate, roomNumber;
    private static JComboBox rtMenu;

    private static JCheckBox smoking = new JCheckBox("Smoking");

    public ArrayList<String> getTextBoxInputs() {
        return textBoxInputs;
    }

    public ModifyRoomGUI() {
        this.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));

        JButton modifyButton = new JButton("MODIFY");
        modifyButton.addActionListener(new modifyRoomActionListener());

        startDate = new JTextField(16);
        endDate = new JTextField(16);
        roomNumber = new JTextField(16);

        JLabel rtLabel = new JLabel("Room Type:");
        JLabel roomNumberLabel = new JLabel("Room number:");

        String[] rtStrings = { "ECONOMY", "SUITE", "LUXURY"};

        //Create the combo box, select item at index 1.

        rtMenu = new JComboBox(rtStrings);
        rtMenu.setSelectedIndex(0);

        this.add(roomNumberLabel);
        this.add(roomNumber);
        this.add(Box.createRigidArea(new Dimension(0, 10)));



        // Add buttons to the frame (and spaces between buttons)
        this.add(rtLabel);
        this.add(rtMenu);
        this.add(Box.createRigidArea(new Dimension(0, 10)));



        this.add(smoking);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(modifyButton);
    }

    private static final class modifyRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Integer roomNum = Integer.parseInt(roomNumber.getText());
            Integer roomType = rtMenu.getSelectedIndex();
            //Integer rType = Integer.parseInt(roomT)

            boolean success = SystemHandler.handler().modifyRoom(roomNum, roomType);
            if(success){
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, "Room successfully Modified",
                        "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
            }
            else{
                Object[] options = { "OK" };
                JOptionPane.showOptionDialog(null, "This room does not exist",
                        "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
            }

        }
    }
}