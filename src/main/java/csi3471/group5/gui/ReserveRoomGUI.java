package csi3471.group5.gui;

import csi3471.group5.SystemHandler;
import csi3471.group5.db.DBStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReserveRoomGUI extends JPanel{
        private ArrayList<String> textBoxInputs;
        private static JTextField startDate, endDate, guestId;
        private static JComboBox rtMenu;

        public ArrayList<String> getTextBoxInputs() {
            return textBoxInputs;
        }

        public ReserveRoomGUI() {
            this.setBackground(new Color(200,219,215));
            BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(boxLayout);
            this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));

            JButton reserveButton = new JButton("RESERVE");
            reserveButton.addActionListener(new ReserveActionListener());

            startDate = new JTextField(16);
            endDate = new JTextField(16);
            guestId = new JTextField(16);

            JLabel rtLabel = new JLabel("Room Type:");
            JLabel startLabel = new JLabel("Start Date: mm-dd-yyyy");
            JLabel endLabel = new JLabel("End Date: mm-dd-yyyy");
            JLabel guestLabel = new JLabel("guest ID number:");

            String[] rtStrings = { "Room Type 1", "Room Type 2", "Room Type 3"};

            //Create the combo box, select item at index 1.
            rtMenu = new JComboBox(rtStrings);
            rtMenu.setSelectedIndex(0);


            // Add buttons to the frame (and spaces between buttons)
            this.add(rtLabel);
            this.add(rtMenu);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            this.add(startLabel);
            this.add(startDate);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            this.add(endLabel);
            this.add(endDate);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            this.add(guestLabel);
            this.add(guestId);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            this.add(reserveButton);
        }

        private static final class ReserveActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                try {
                    Date start = formatter.parse(startDate.getText());
                    Date end = formatter.parse(endDate.getText());
                    int id = Integer.parseInt(guestId.getText());

                    //This could be a bad idea -Lucy
                    Integer roomType = rtMenu.getSelectedIndex();

                    boolean success = SystemHandler.handler().reserveRoom(roomType, start, end);
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

                }catch (java.text.ParseException p){
                    Object[] options = { "OK" };
                    JOptionPane.showOptionDialog(null, "Please Enter Date: mm-dd-yyyy",
                            "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                }
            }
        }
}
