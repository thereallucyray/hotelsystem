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
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * The ReserveRoom GUI class creates a Swing GUI that allows a guest to reserve a room, or
 * an Admin or Employee to reserve a room on behalf of a guest
 */
public class ReserveRoomGUI extends CleverCards {
    private Reservation reservation;
    private RoomTypeSelector rtMenu;
    private JTextField guestId;
    private JDatePickerImpl startDate, endDate;
    private JCheckBox isCorporate, cardOnFile;


    /**
     * Default constructor
     */
    public ReserveRoomGUI() {
        super();
    }

    /**
     * Constructor given a reservation, for use in modify room
     * @param reservation pre existing reservation to be changed
     */
    public ReserveRoomGUI(Reservation reservation) {
        this.reservation = reservation;
        refresh();
    }

    /**
     * This init method initializes the Swing GUI to reserve a room, dynamically
     * changing depending on guest/employee view
     */
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


    /**
     * this method creates date pickers for use in selecting the start
     * and end dates
     * @param panel panel component the calendars should be added to
     */
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

    /**
     * Action listener class to parse inputs and check for correctness, and create
     * a new reservation
     */
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
                    JOptionPane.showMessageDialog(null, "Start date must be before end date.");
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
                            JOptionPane.showMessageDialog(null, "No card on file");
                            payment = false;
                        } else if (!cardOnFile.isSelected() && !isCorporate.isSelected()) {
                            PaymentDialog pd = new PaymentDialog(validGuest);
                            pd.setModal(true);
                            pd.setVisible(true);
                        }

                        if(payment) {

                            JOptionPane.showMessageDialog(null, "Thank you for your Reservation");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No rooms of this type are available");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Guest username is invalid.");
                }
            } catch (java.text.ParseException p) {
                JOptionPane.showMessageDialog(null, "Please Enter Date: mm-dd-yyyy");
            }
        }
    }
}
