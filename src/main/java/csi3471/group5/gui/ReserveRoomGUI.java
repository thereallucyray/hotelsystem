package csi3471.group5.gui;

import csi3471.group5.SystemHandler;
import csi3471.group5.db.DBStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

// import a DatePicker from https://www.codejava.net/java-se/swing/jdatepicker-tutorial-and-examples
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


public class ReserveRoomGUI extends JPanel{
        private ArrayList<String> textBoxInputs;
        private static JTextField guestId;
        private static JDatePickerImpl startDate, endDate;
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

            guestId = new JTextField(16);

            JLabel rtLabel = new JLabel("Room Type:");
            JLabel guestLabel = new JLabel("guest ID number:");

            String[] rtStrings = { "Room Type 1", "Room Type 2", "Room Type 3"};

            //Create the combo box, select item at index 1.
            rtMenu = new JComboBox(rtStrings);
            rtMenu.setSelectedIndex(0);

            // Add buttons to the frame (and spaces between buttons)
            this.add(rtLabel);
            this.add(rtMenu);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            addDatePickers(this);

            this.add(guestLabel);
            this.add(guestId);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            this.add(reserveButton);

        }
        void addDatePickers(JPanel panel) {
            // create a date picker
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");

            JLabel startLabel = new JLabel("Start Date: mm-dd-yyyy");
            JLabel endLabel = new JLabel("End Date: mm-dd-yyyy");
            JFormattedTextField.AbstractFormatter formatter = new JFormattedTextField.AbstractFormatter() {
                private String datePattern = "MM-dd-yyyy";
                private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
                @Override
                public Object stringToValue(String text) throws ParseException {
                    return dateFormatter.parseObject(text);
                }
                @Override
                public String valueToString(Object value) throws ParseException {
                    if (value != null) {
                        Calendar cal = (Calendar) value;
                        return dateFormatter.format(cal.getTime());
                    }
                    return "";
                }
            };
            UtilDateModel model = new UtilDateModel();
            JDatePanelImpl startDatePanel = new JDatePanelImpl(model, p);
            startDate = new JDatePickerImpl(startDatePanel, formatter);

            this.add(startLabel);
            panel.add(startDate);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            UtilDateModel model2 = new UtilDateModel();
            JDatePanelImpl endDatePanel = new JDatePanelImpl(model2, p);
            endDate = new JDatePickerImpl(endDatePanel, formatter);

            this.add(endLabel);
            panel.add(endDate);
        }

        private static final class ReserveActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                try {
                    Date start = formatter.parse(startDate.getJFormattedTextField().getText());
                    Date end = formatter.parse(endDate.getJFormattedTextField().getText());
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
