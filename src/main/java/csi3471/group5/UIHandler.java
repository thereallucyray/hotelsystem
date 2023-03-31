package csi3471.group5;

import csi3471.group5.db.DBStore;
import csi3471.group5.gui.AddGuestUI;
import csi3471.group5.gui.ModifyRoomGUI;
import csi3471.group5.gui.ReserveRoomGUI;

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

        // Add buttons to the frame (and spaces between buttons)
        homePane.add(registerButton);
        homePane.add(reserveRoomButtom);

        //Create the "cards".
        JPanel card1 = new AddGuestUI();
        JPanel card2 = new ReserveRoomGUI();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add("ADDGUEST", card1);
        cards.add("RESERVEROOM", card2);

        pane.add(homePane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, e.getActionCommand());
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        UIHandler demo = new UIHandler();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
