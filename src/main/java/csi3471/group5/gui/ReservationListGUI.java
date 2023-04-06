package csi3471.group5.gui;

import csi3471.group5.Reservation;
import csi3471.group5.store.ReservationStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReservationListGUI extends CleverCards {

    public void init() {
        this.setBackground(new Color(200, 219, 215));
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setBorder(new EmptyBorder(new Insets(150, 100, 150, 100)));
        this.setVisible(true);
        JPanel panel = new JPanel();
        BoxLayout boxLayout2 = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout2);
        panel.setBackground(new Color(180,207,201));
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        System.out.println("Getting reservations");
        for(Reservation res : new ReservationStore().query().get()) {
            panel.add(reservationPanel(res));
            panel.add(Box.createRigidArea(new Dimension(0,5)));
        }
        this.add(panel);
    }

    public ReservationListGUI() {
        init();
    }
    JPanel reservationPanel(Reservation res) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230,239,237));
        GridLayout gridLayout = new GridLayout();
        this.setLayout(gridLayout);
        JLabel lab = new JLabel(res.toString());
        panel.add(lab);
        JButton button = new JButton("Select this Reservation");
        panel.add(button);
        return panel;
    }
}
