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

/**
 * Implements the front-end for modifying a room
 */
public class ModifyRoomGUI extends CleverCards{
    private static JTextField roomNumber;
    private static RoomTypeSelector rtMenu;

//    private static JCheckBox smoking = new JCheckBox("Smoking");

    /**
     * Initializes swing components for modifying a room
     * The options include searching for a room number,
     * and changing its room type
     *
     */
    @Override
    public void init() {
        JPanel mainContent = new JPanel();
        this.setLayout(new BorderLayout());
        mainContent.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(mainContent, BoxLayout.Y_AXIS);
        mainContent.setLayout(boxLayout);
        mainContent.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));

        JButton modifyButton = new JButton("MODIFY");
        modifyButton.addActionListener(new modifyRoomActionListener());

        roomNumber = new JTextField(16);

        JLabel rtLabel = new JLabel("Room Type:");
        JLabel roomNumberLabel = new JLabel("Room number:");

        rtMenu = new RoomTypeSelector();
        rtMenu.setSelectedIndex(0);

        this.add(MenuCreator.createMenuBar(),BorderLayout.NORTH);

        mainContent.add(roomNumberLabel);
        roomNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(roomNumber);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));


        // Add buttons to the frame (and spaces between buttons)
        mainContent.add(rtLabel);
        rtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(rtMenu);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        mainContent.add(modifyButton);
        modifyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(mainContent,BorderLayout.CENTER);
    }

    /**
     * Action listener when clicking "Modify Room" option
     */
    private static final class modifyRoomActionListener implements ActionListener {
        /**
         * The room is successfully modified if the room number is valid.
         * The room-type will be updated.
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {
            int roomNum = Integer.parseInt(roomNumber.getText());
            RoomType roomType = rtMenu.getSelectedRoomType();

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
