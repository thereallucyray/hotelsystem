package csi3471.group5;

import csi3471.group5.db.DBStore;
import csi3471.group5.gui.AddGuestUI;
import csi3471.group5.gui.ModifyRoomGUI;
import csi3471.group5.gui.ReserveRoomGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIHandler implements ActionListener{
    JPanel cards; //a panel that uses CardLayout

    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel homePane = new JPanel(); //use FlowLayout

        JButton registerButton = new JButton("Add Guest");
        registerButton.addActionListener(this);
        JButton reserveRoomButtom = new JButton("Reserve Room");
        reserveRoomButtom.addActionListener(this);
        JButton modifyRoomButton = new JButton("Modify Room");
        modifyRoomButton.addActionListener(this);

        // Add buttons to the frame (and spaces between buttons)
        homePane.add(registerButton);
        homePane.add(reserveRoomButtom);
        homePane.add(modifyRoomButton);

        //Create the "cards".
        JPanel card1 = new AddGuestUI();
        JPanel card2 = new ReserveRoomGUI();
        JPanel card3 = new ModifyRoomGUI();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add("Add Guest", card1);
        cards.add("Reserve Room", card2);
        cards.add("Modify Room", card3);


        //pane.add(homePane, BorderLayout.PAGE_START);
        pane.add(createMenuBar(), BorderLayout.PAGE_START);

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

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        menuBar.add(Box.createHorizontalGlue());

        //Build the first menu.
        menu = new JMenu("MENU");
        //menu.setMnemonic(KeyEvent.VK_A);


        //a group of JMenuItems
        menuItem = new JMenuItem("Modify Room");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();

        JMenuItem menuItem2 = new JMenuItem("Reserve Room");
        menuItem2.addActionListener(this);
        menu.add(menuItem2);
        menu.addSeparator();

        JMenuItem menuItem3 = new JMenuItem("Add Guest");
        menuItem3.addActionListener(this);
        menu.add(menuItem3);
        menu.addSeparator();

        menuBar.add(menu);


        return menuBar;
    }

}
