package csi3471.group5;

import csi3471.group5.db.DBStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UIHandler extends JFrame {

    public static class guiReserveRoom {
        private ArrayList<String> textBoxInputs;
        private static JTextField startDate, endDate, guestId;
        private static JComboBox rtMenu;

        public ArrayList<String> getTextBoxInputs() {
            return textBoxInputs;
        }

        public static void createAndShowGUI() {

            JFrame frame = new JFrame("Reserve a Room");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.out.println("Closing");
                    DBStore.saveAll();
                    System.exit(0);
                }
            });
            JPanel panel = new JPanel();
            panel.setBackground(new Color(200,219,215));
            BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
            panel.setLayout(boxLayout);
            panel.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));

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
            panel.add(rtLabel);
            panel.add(rtMenu);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            panel.add(startLabel);
            panel.add(startDate);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            panel.add(endLabel);
            panel.add(endDate);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            panel.add(guestLabel);
            panel.add(guestId);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            panel.add(reserveButton);

            // Set the window to be visible as the default to be false
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
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

}
