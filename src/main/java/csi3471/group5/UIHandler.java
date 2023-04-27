package csi3471.group5;

import csi3471.group5.db.DBStore;
import csi3471.group5.gui.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIHandler {

    //Create the "cards".

    JPanel cards;
    MenuCreator menuCreator;

    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        //JPanel homePane = new JPanel(); //use FlowLayout
        cards = new JPanel(new CardLayout());

        //Create the "cards".

        JPanel card1 = new AddGuestUI();
        JPanel card2 = new ReserveRoomGUI();
        JPanel card3 = new ModifyRoomGUI();
        JPanel card4 = new ReservationListGUI();
        JPanel card5 = new LoginGUI();
        JPanel card6 = new ViewRoomsGUI();
        JPanel card7 = new ModifyProfileGUI();
        JPanel card8 = new ModifyOtherProfileGUI();

        //Create the panel that contains the "cards".
        //cards = new JPanel(new CardLayout());
        cards.add("LOGIN", card5);
        cards.add("ADDGUEST", card1);
        cards.add("RESERVEROOM", card2);
        cards.add("MODIFYROOM", card3);
        cards.add("RESERVATIONLIST", card4);
        cards.add("ROOMVIEW", card6);
        cards.add("MODIFYPROFILE", card7);
        cards.add("MODIFYUSER",card8);


        MenuCreator.setCardLayout(cards);
        pane.add(cards, BorderLayout.CENTER);
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
