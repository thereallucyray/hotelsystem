package csi3471.group5.gui;

import csi3471.group5.Reservation;
import csi3471.group5.store.ReservationStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        this.add(scrollPane);
        ArrayList<Reservation> reservations = new ReservationStore().query().get();
        panel.setPreferredSize(new Dimension(500, 100 * reservations.size()));
        panel.setBackground(new Color(180,207,201));
        for(Reservation res : reservations) {
            panel.add(reservationPanel(res));
            panel.add(Box.createRigidArea(new Dimension(0,5)));
        }
    }

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
                JPanel gui = new ReserveRoomGUI();
                gui.setPreferredSize(new Dimension(500, 500));
                JOptionPane.showMessageDialog(null, gui, "Modify Reservation (Just adds another for now, we'll fix later)", JOptionPane.PLAIN_MESSAGE);
                // wait for the dialog to close
                refresh();
            }
        });
        if(res.isActive()) {
            lab.setForeground(new Color(0, 0, 0));
            String text = res.getStatus() == Reservation.Status.CHECKED_IN ? "CHECK OUT" : "CHECK IN";
            JButton button = new JButton(text);
            buttonPanel.add(button);
            if(res.getStatus() == Reservation.Status.CHECKED_IN) {
                button.addActionListener(new CheckOutActionListener(res));
            } else {
                button.addActionListener(new CheckInActionListener(res));
                JButton button2 = new JButton("CANCEL");
                buttonPanel.add(button2);
                button2.addActionListener(new CancelActionListener(res));
                buttonPanel.add(dialogButton);
            }
        } else {
            lab.setForeground(new Color(100, 100, 100));
        }
        panel.add(buttonPanel);
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
