package csi3471.group5;

import csi3471.group5.db.DBStore;
import csi3471.group5.gui.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIHandler implements ActionListener{
    JPanel cards; //a panel that uses CardLayout

    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel homePane = new JPanel(); //use FlowLayout

        JButton registerButton = new JButton("ADDGUEST");
        registerButton.addActionListener(this);
        JButton reserveRoomButtom = new JButton("RESERVEROOM");
        reserveRoomButtom.addActionListener(this);
        JButton modifyRoomButton = new JButton("MODIFYROOM");
        modifyRoomButton.addActionListener(this);
        JButton reservationListButton = new JButton("RESERVATIONLIST");
        reservationListButton.addActionListener(this);

        // Add buttons to the frame (and spaces between buttons)
        homePane.add(registerButton);
        homePane.add(reserveRoomButtom);
        homePane.add(modifyRoomButton);
        homePane.add(reservationListButton);

        //Create the "cards".
        JPanel card1 = new AddGuestUI();
        JPanel card2 = new ReserveRoomGUI();
        JPanel card3 = new ModifyRoomGUI();
        JPanel card4 = new ReservationListGUI();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add("ADDGUEST", card1);
        cards.add("RESERVEROOM", card2);
        cards.add("MODIFYROOM", card3);
        cards.add("RESERVATIONLIST", card4);

        pane.add(homePane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, e.getActionCommand());
        for(Component panel : cards.getComponents()) {
            if(panel.isVisible()) {
                CleverCards cc = (CleverCards) panel;
                cc.refresh();
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Hotel System");
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Closing");
                DBStore.saveAll();
                System.exit(0);
            }
        });
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        UIHandler demo = new UIHandler();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
