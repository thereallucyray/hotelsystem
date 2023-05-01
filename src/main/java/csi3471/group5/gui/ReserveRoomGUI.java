package csi3471.group5.gui;

import csi3471.group5.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

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


public class ReserveRoomGUI extends CleverCards {
    private Reservation reservation;
    private ArrayList<String> textBoxInputs;
    private RoomTypeSelector rtMenu;
    private JTextField guestId;
    private JDatePickerImpl startDate, endDate;
    private JCheckBox isCorporate, cardOnFile;

    public ArrayList<String> getTextBoxInputs() {
        return textBoxInputs;
    }

    public ReserveRoomGUI() {
        super();
    }
    public ReserveRoomGUI(Reservation reservation) {
        this.reservation = reservation;
        refresh();
    }

    @Override
    public void init() {
        JPanel mainContent = new JPanel();
        this.setLayout(new BorderLayout());
        mainContent.setBackground(new Color(200, 219, 215));
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.PAGE_AXIS));
        mainContent.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));

        JButton reserveButton = new JButton(reservation == null ? "RESERVE" : "MODIFY");
        reserveButton.addActionListener(new ReserveActionListener(reservation));

        guestId = new JTextField(16);
        guestId.setMaximumSize(new Dimension(Integer.MAX_VALUE, guestId.getPreferredSize().height));
        if(reservation != null) {
            guestId.setText(reservation.getGuest().getUsername());
        }

        JLabel rtLabel = new JLabel("Room Type:");
        JLabel guestLabel = new JLabel("Guest Username:");

        rtMenu = new RoomTypeSelector();
        rtMenu.setSelectedIndex(0);
        if(reservation != null) {
            rtMenu.setSelectedItem(reservation.getBookedRoom().getRoomType());
        }
        if(reservation == null) {
            this.add(MenuCreator.createMenuBar(),BorderLayout.NORTH);
        }

        // Add buttons to the frame (and spaces between buttons)
        mainContent.add(rtLabel);
        rtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //restrict size on page axis
        rtMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, rtMenu.getPreferredSize().height));
        mainContent.add(rtMenu);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        addDatePickers(mainContent);

        if(reservation != null) {
            guestId.setEditable(false);
        }
        if(SystemHandler.handler().isEmployeeFacing()) {
            mainContent.add(guestLabel);
            guestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainContent.add(guestId);
        }
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        isCorporate = new JCheckBox("Corporate");
        if(reservation != null) {
            isCorporate.setSelected(reservation.isCorporate());
        }
        isCorporate.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(isCorporate);
        mainContent.add(Box.createRigidArea(new Dimension(0, 10)));

        cardOnFile = new JCheckBox("Use card on file?");
        cardOnFile.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(cardOnFile);

        mainContent.add(reserveButton);
        reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(mainContent, BorderLayout.CENTER);
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
        if(reservation != null) {
            model.setValue(reservation.getStartDate());
        }
        JDatePanelImpl startDatePanel = new JDatePanelImpl(model, p);
        startDate = new JDatePickerImpl(startDatePanel, formatter);

        panel.add(startLabel);
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(startDate);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        UtilDateModel model2 = new UtilDateModel();
        if(reservation != null) {
            model2.setValue(reservation.getEndDate());
        }
        JDatePanelImpl endDatePanel = new JDatePanelImpl(model2, p);
        endDate = new JDatePickerImpl(endDatePanel, formatter);

        panel.add(endLabel);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(endDate);
    }

    private final class ReserveActionListener implements ActionListener {
        private Reservation reservation;
        public ReserveActionListener(Reservation reservation) {
            this.reservation = reservation;
        }
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            try {
                Date start = formatter.parse(startDate.getJFormattedTextField().getText());
                Date end = formatter.parse(endDate.getJFormattedTextField().getText());

                if(start.after(end)) {
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Start date must be before end date.",
                            "Failed Request", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[0]);
                    return;
                }

                RoomType roomType = rtMenu.getSelectedRoomType();
                Guest validGuest = null;

                if(SystemHandler.handler().isEmployeeFacing()) {
                    validGuest = SystemHandler.handler().validGuest(guestId.getText());
                } else {
                    validGuest = SystemHandler.handler().getGuest();
                }

                boolean corporate = isCorporate.isSelected();
                boolean success = false;
                boolean payment = true;

                if (validGuest != null) {
                    if(reservation != null) {
                        Room room = roomType.getAvailableRoom(start, end);
                        if(room != null) {
                            reservation.setBookedRoom(room);
                            reservation.setStartDate(start);
                            reservation.setEndDate(end);
                            reservation.setCorporate(corporate);
                            success = true;
                        }
                    } else {
                        success = SystemHandler.handler().reserveRoom(roomType, start, end, validGuest, corporate);
                    }

                    if (success) {
                        if(cardOnFile.isSelected() && !isCorporate.isSelected() && validGuest.getBankToken() == null){
                            Object[] options = {"OK"};
                            JOptionPane.showOptionDialog(null, "No card on file",
                                    "Failed Request", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, options, options[0]);
                            payment = false;
                        } else if (!cardOnFile.isSelected() && !isCorporate.isSelected()) {
                            PaymentDialog pd = new PaymentDialog(validGuest);
                            //JOptionPane.showMessageDialog(null, pd, "Payment", JOptionPane.PLAIN_MESSAGE);
//                            JDialog dialog = new JDialog();
//                            dialog.add(pd);
//                            dialog.setSize(400,500);
                            pd.setModal(true);
                            pd.setVisible(true);

                        }

                        if(payment) {
                            System.out.println("successful reservation");
                            Object[] options = {"YES", "NO"};
                            //ask guest if they want to use their card on file
                            System.out.println(corporate);
                            System.out.println(validGuest.getBankToken() != null);

                            Object[] options2 = {"OK"};
                            JOptionPane.showOptionDialog(null, "Thank you for your Reservation",
                                    "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, options2, options2[0]);
                        }
                    } else {
                        Object[] options = {"OK"};
                        JOptionPane.showOptionDialog(null, "No rooms of this type are available",
                                "Failed Request", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options, options[0]);
                    }
                } else {
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null, "Guest username is invalid.",
                            "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                }


            } catch (java.text.ParseException p) {
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null, "Please Enter Date: mm-dd-yyyy",
                        "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
            }
        }
    }
}
