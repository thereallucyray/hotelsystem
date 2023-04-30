package csi3471.group5.gui;

import csi3471.group5.MenuCreator;
import csi3471.group5.Reservation;
import csi3471.group5.SystemHandler;
import csi3471.group5.store.ReservationStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The front-end interaction for displaying a user's reservations.
 * Is a CleverCards so can switch to other cards.
 */
public class ReservationListGUI extends CleverCards {
    JScrollPane scrollPane;

    @Override
    public void refresh() {
        int rect = scrollPane.getVerticalScrollBar().getValue();
        super.refresh();
        scrollPane.getVerticalScrollBar().setValue(rect);
        scrollPane.getVerticalScrollBar().setValue(rect);
    }

    /**
     * Establishes a list of user's reservations to the interface.
     * If no reservations have been made yet then a message will indicate so.
     */
    public void init() {
        this.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
//        panel.setPreferredSize(new Dimension(500, 10000));
//        JLabel noReservations = new JLabel("you have no reservations");

        // setup a scroll pane
        scrollPane = new JScrollPane(panel);
        this.add(MenuCreator.createMenuBar(),BorderLayout.NORTH);
        List<Reservation> reservations;
        if(!SystemHandler.handler().isEmployeeFacing()) {
            reservations = SystemHandler.handler().getGuest().guestsReservations.stream().filter(r -> r.getGuest() == SystemHandler.handler().getGuest()).toList();
        } else {
            reservations = new ReservationStore().query().get();
        }
        if(reservations.isEmpty()){
            JLabel noReservations = new JLabel("You have no reservations");
            panel.add(noReservations);
        }
        panel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        panel.setPreferredSize(new Dimension(500, 62 * reservations.size()));
        panel.setBackground(new Color(180,207,201));
        for(Reservation res : reservations) {
            panel.add(reservationPanel(res));
            panel.add(Box.createRigidArea(new Dimension(0,5)));
        }
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates a panel for a reseration including a check in, check out, cancel, modify, or view receipt option.
     * @param res The reservation whose panel is getting created.
     * @return JPanel
     */
    JPanel reservationPanel(Reservation res) {
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(500, 25));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(230,239,237));
        panel.setPreferredSize(new Dimension(500, 55));
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel lab = new JLabel(res.toString());
        // create a dialog button with the reserveroomgui inside
        JButton dialogButton = new JButton("MODIFY");
        dialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel gui = new ReserveRoomGUI(res);
                gui.setPreferredSize(new Dimension(500, 500));
                JOptionPane.showMessageDialog(null, gui, "Modify Reservation", JOptionPane.PLAIN_MESSAGE);
                // wait for the dialog to close
                refresh();
            }
        });
        if(res.isActive()) {
            lab.setForeground(new Color(0, 0, 0));
            String text = res.getStatus() == Reservation.Status.CHECKED_IN ? "CHECK OUT" : "CHECK IN";
            JButton button = new JButton(text);
            if(SystemHandler.handler().isEmployeeFacing()) {
                buttonPanel.add(button);
            }
            if(res.getStatus() == Reservation.Status.CHECKED_IN) {
                button.addActionListener(new CheckOutActionListener(res));
            } else {
                button.addActionListener(new CheckInActionListener(res));
                JButton button2 = new JButton("CANCEL");
                button2.addActionListener(new CancelActionListener(res));
                if(res.canModify()) {
                    buttonPanel.add(button2);
                    buttonPanel.add(dialogButton);
                }
            }
        } else {
            if(res.hasReceipt()) {
                JButton button = new JButton("VIEW RECEIPT");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JPanel gui = new JPanel();
                        gui.setLayout(new BoxLayout(gui, BoxLayout.Y_AXIS));
                        // make it respect newlines
                        JTextArea textArea = new JTextArea(res.getReceipt().toString());
                        textArea.setLineWrap(true);
                        gui.add(textArea);
//                        gui.setPreferredSize(new Dimension(500, 500));
                        JOptionPane.showMessageDialog(null, gui, "Receipt", JOptionPane.PLAIN_MESSAGE);
                    }
                });
                buttonPanel.add(button);
            }
            lab.setForeground(new Color(100, 100, 100));
        }
        panel.add(buttonPanel);
        panel.add(lab);


        return panel;
    }

    /**
     * Change status of reservation class to checked in.
     */
    class CheckInActionListener implements ActionListener {
        Reservation res;

        /**
         * CheckInActionListener constructor.
         * @param res Reservation whose status is updating.
         */
        CheckInActionListener(Reservation res) {
            this.res = res;
        }

        /**
         * Checking in res.
         * @param e the event to be processed.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            res.checkIn();
            refresh();
        }
    }
    /**
     * Change status of reservation class to checked out.
     */
    class CheckOutActionListener implements ActionListener {
        Reservation res;
        /**
         * CheckOutActionListener constructor.
         * @param res Reservation whose status is updating.
         */
        CheckOutActionListener(Reservation res) {
            this.res = res;
        }
        /**
         * Checking out res.
         * @param e the event to be processed.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            res.checkOut();
            refresh();
        }
    }
    /**
     * Change status of reservation class to canceled.
     */
    class CancelActionListener implements ActionListener {
        Reservation res;
        /**
         * CancelActionListener constructor.
         * @param res Reservation whose status is updating.
         */
        CancelActionListener(Reservation res) {
            this.res = res;
        }
        /**
         * Canceling res.
         * @param e the event to be processed.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            res.cancelRes();
            refresh();
        }
    }
}
