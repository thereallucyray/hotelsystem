package csi3471.group5.gui;

import csi3471.group5.Reservation;
import csi3471.group5.store.ReservationStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReservationListGUI extends CleverCards {
    public void init() {
        this.setBackground(new Color(200, 219, 215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        this.setVisible(true);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 10000));
        // setup a scroll pane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(550, 400));
        this.add(scrollPane);
        panel.setBackground(new Color(180,207,201));
        for(Reservation res : new ReservationStore().query().get()) {
            panel.add(reservationPanel(res));
            panel.add(Box.createRigidArea(new Dimension(0,5)));
        }
    }

    JPanel reservationPanel(Reservation res) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230,239,237));
        panel.setPreferredSize(new Dimension(500, 75));
        GridLayout gridLayout = new GridLayout();
        this.setLayout(gridLayout);
        JLabel lab = new JLabel(res.toString());
        if(res.isActive()) {
            lab.setForeground(new Color(0, 0, 0));
            String text = res.getStatus() == Reservation.Status.CHECKED_IN ? "CHECK OUT" : "CHECK IN";
            JButton button = new JButton(text);
            panel.add(button);
            if(res.getStatus() == Reservation.Status.CHECKED_IN) {
                button.addActionListener(new CheckOutActionListener(res));
            } else {
                button.addActionListener(new CheckInActionListener(res));
                JButton button2 = new JButton("CANCEL");
                panel.add(button2);
                button2.addActionListener(new CancelActionListener(res));
            }
        } else {
            lab.setForeground(new Color(100, 100, 100));
        }
        panel.add(lab);

        return panel;
    }
    // change status of reservation class
    class CheckInActionListener implements ActionListener {
        Reservation res;
        CheckInActionListener(Reservation res) {
            this.res = res;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            res.setStatus(Reservation.Status.CHECKED_IN);
            refresh();
        }
    }
    class CheckOutActionListener implements ActionListener {
        Reservation res;
        CheckOutActionListener(Reservation res) {
            this.res = res;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            res.setStatus(Reservation.Status.CHECKED_OUT);
            refresh();
        }
    }
    class CancelActionListener implements ActionListener {
        Reservation res;
        CancelActionListener(Reservation res) {
            this.res = res;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            res.setStatus(Reservation.Status.CANCELED);
            refresh();
        }
    }
}
