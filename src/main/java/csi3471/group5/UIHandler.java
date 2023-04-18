package csi3471.group5;

import csi3471.group5.db.DBStore;
import csi3471.group5.gui.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIHandler implements ActionListener{

    //JPanel cards; //a panel that uses CardLayout

    //Create the "cards".
    JPanel card1 = new AddGuestUI();
    JPanel card2 = new ReserveRoomGUI();
    JPanel card3 = new ModifyRoomGUI();
    JPanel card4 = new ReservationListGUI();
    JPanel card5 = new LoginGUI();

    JPanel cards = new JPanel(new CardLayout());

    MenuCreator menuCreator;


    //Create the panel that contains the "cards".
    //cards = new JPanel(new CardLayout());
       /* cards.add("ADDGUEST", card1);
        cards.add("RESERVEROOM", card2);
        cards.add("MODIFYROOM", card3);
        cards.add("RESERVATIONLIST", card4);
        cards.add("LOGIN", card5)*/

    public UIHandler(){
        cards.add("LOGIN", card5);
        cards.add("ADDGUEST", card1);
        cards.add("RESERVEROOM", card2);
        cards.add("MODIFYROOM", card3);
        cards.add("RESERVATIONLIST", card4);
    }
    public UIHandler(String button){

    }
    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        //JPanel homePane = new JPanel(); //use FlowLayout

        //making new branch:)
        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener(this);

        JButton registerButton = new JButton("ADDGUEST");
        registerButton.addActionListener(this);
        JButton reserveRoomButton = new JButton("RESERVEROOM");
        reserveRoomButton.addActionListener(this);
        JButton modifyRoomButton = new JButton("MODIFYROOM");
        modifyRoomButton.addActionListener(this);
        JButton reservationListButton = new JButton("RESERVATIONLIST");
        reservationListButton.addActionListener(this);

        // Add buttons to the frame (and spaces between buttons)
        /*
        homePane.add(loginButton);
        homePane.add(registerButton);
        homePane.add(reserveRoomButton);
        homePane.add(modifyRoomButton);
        homePane.add(reservationListButton);*/

        //Create the "cards".
        JPanel card5 = new LoginGUI();

        JPanel card1 = new AddGuestUI();
        JPanel card2 = new ReserveRoomGUI();
        JPanel card3 = new ModifyRoomGUI();
        JPanel card4 = new ReservationListGUI();
        JPanel card6 = new ViewRoomsGUI();

        //JPanel card5 = new LoginGUI();

        //Create the panel that contains the "cards".
        //cards = new JPanel(new CardLayout());
        cards.add("LOGIN", card5);

        cards.add("ADDGUEST", card1);
        cards.add("RESERVEROOM", card2);
        cards.add("MODIFYROOM", card3);
        cards.add("RESERVATIONLIST", card4);
        cards.add("ROOMVIEW", card6);

        menuCreator = new MenuCreator(cards, pane);

        //pane.add(homePane, BorderLayout.PAGE_START);
        //pane.add(MenuCreator.createMenuBar(), BorderLayout.PAGE_START);
        MenuCreator.setCardLayout(cards);
        pane.add(cards, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Create the "cards".
        /*JPanel card1 = new AddGuestUI();
        JPanel card2 = new ReserveRoomGUI();
        JPanel card3 = new ModifyRoomGUI();
        JPanel card4 = new ReservationListGUI();

        cards.add("ADDGUEST", card1);
        cards.add("RESERVEROOM", card2);
        cards.add("MODIFYROOM", card3);
        cards.add("RESERVATIONLIST", card4);*/

//        CardLayout cl = (CardLayout)(cards.getLayout());
//        cl.show(cards, e.getActionCommand());
        System.out.println(e.getActionCommand());
        MenuCreator.switchCard(e.getActionCommand());
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
