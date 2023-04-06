package csi3471.group5.gui;

import csi3471.group5.Room;
import csi3471.group5.RoomType;
import csi3471.group5.SystemHandler;
import csi3471.group5.db.DBStore;
import csi3471.group5.store.RoomStore;
import csi3471.group5.store.RoomTypeStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReserveRoomGUI extends CleverCards {
        private ArrayList<String> textBoxInputs;
        private static JTextField startDate, endDate, guestId;
        private static RoomTypeSelector rtMenu;

        public ArrayList<String> getTextBoxInputs() {
            return textBoxInputs;
        }

    @Override
    public void init() {
        this.setBackground(new Color(200,219,215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));

        JButton reserveButton = new JButton("RESERVE");
        reserveButton.addActionListener(new ReserveActionListener());

        startDate = new JTextField(16);
        endDate = new JTextField(16);
        guestId = new JTextField(16);

        JLabel rtLabel = new JLabel("Room Type:", JLabel.CENTER);
        JLabel startLabel = new JLabel("Start Date: mm-dd-yyyy", JLabel.CENTER);
        JLabel endLabel = new JLabel("End Date: mm-dd-yyyy", JLabel.CENTER);
        JLabel guestLabel = new JLabel("guest ID number:", JLabel.CENTER);

//            String[] rtStrings = { "Room Type 1", "Room Type 2", "Room Type 3"};

        //Create the combo box, select item at index 1.
//            rtMenu = new JComboBox<RoomType>(new RoomTypeStore().query().get().toArray(new RoomType[0]));
        rtMenu = new RoomTypeSelector();
        rtMenu.setSelectedIndex(0);


        // Add buttons to the frame (and spaces between buttons)
        this.add(rtLabel);
        rtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(rtMenu);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(startLabel);
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(startDate);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(endLabel);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(endDate);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(guestLabel);
        guestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(guestId);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        this.add(reserveButton);
        reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    public ReserveRoomGUI() {
            init();
    }
        private static final class ReserveActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                try {
                    Date start = formatter.parse(startDate.getText());
                    Date end = formatter.parse(endDate.getText());

                    //This could be a bad idea -Lucy
                    RoomType roomType = rtMenu.getSelectedRoomType();

                    boolean success = SystemHandler.handler().reserveRoom(roomType, start, end);
                    boolean validGuest = SystemHandler.handler().validGuest(guestId.getText());
                    if(validGuest){
                        if(success){
                            Object[] options = { "OK" };
                            JOptionPane.showOptionDialog(null, "Thank you for your Reservation",
                                    "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, options, options[0]);
                        }
                        else{
                            Object[] options = { "OK" };
                            JOptionPane.showOptionDialog(null, "No rooms of this type are available",
                                    "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, options, options[0]);
                        }
                    }
                    else{
                        Object[] options = { "OK" };
                        JOptionPane.showOptionDialog(null, "Guest username is invalid.",
                                "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, options, options[0]);
                    }


                }catch (java.text.ParseException p){
                    Object[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, "Please Enter Date: mm-dd-yyyy",
                            "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                }
            }
        }
}
