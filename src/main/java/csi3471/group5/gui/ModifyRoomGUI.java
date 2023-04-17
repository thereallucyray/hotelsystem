package csi3471.group5.gui;

import csi3471.group5.MenuCreator;
import csi3471.group5.RoomType;
import csi3471.group5.SystemHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModifyRoomGUI extends CleverCards{
    private static JTextField roomNumber;
    private static RoomTypeSelector rtMenu;

//    private static JCheckBox smoking = new JCheckBox("Smoking");

    @Override
    public void init() {
        this.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));

        JButton modifyButton = new JButton("MODIFY");
        modifyButton.addActionListener(new modifyRoomActionListener());

        roomNumber = new JTextField(16);

        JLabel rtLabel = new JLabel("Room Type:");
        JLabel roomNumberLabel = new JLabel("Room number:");

        rtMenu = new RoomTypeSelector();
        rtMenu.setSelectedIndex(0);

        this.add(MenuCreator.createMenuBar());

        this.add(roomNumberLabel);
        roomNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(roomNumber);
        this.add(Box.createRigidArea(new Dimension(0, 10)));


        // Add buttons to the frame (and spaces between buttons)
        this.add(rtLabel);
        rtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(rtMenu);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

//        this.add(smoking);
//        smoking.setAlignmentX(Component.CENTER_ALIGNMENT);
//        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(modifyButton);
        modifyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private static final class modifyRoomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int roomNum = Integer.parseInt(roomNumber.getText());
            RoomType roomType = rtMenu.getSelectedRoomType();
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
