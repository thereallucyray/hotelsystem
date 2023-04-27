package csi3471.group5;
import csi3471.group5.db.DBStore;
import csi3471.group5.gui.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates and controls the menu bar. In charge of switching between cards.
 */
public class MenuCreator extends UIHandler{

    private static JPanel cards = null;
    Container pane;

    /**
     * Constructor for MenuCreator
     * @param cards
     * @param pane
     */
    MenuCreator(JPanel cards,Container pane ){
        MenuCreator.cards = cards;
        this.pane = pane;
    }

    /**
     * @param panel The card layout to be used.
     */
    public static void setCardLayout(JPanel panel) {
        cards = panel;
    }

    /**
     * Switches the card layout to the specified card.
     * @param card The name of the card to switch to.
     */
    public static void switchCard(String card) {
        if(cards == null) {
            throw new NullPointerException("Fix your stuff.");
        }
        System.out.println(card);
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, card);
    }

    /**
     * @return A Menu Bar
     */
    public static JPanel createMenuBar(){
        JPanel homePane = new JPanel(); //use FlowLayout

        JButton registerButton = new JButton("ADDGUEST");
        JButton reserveRoomButtom = new JButton("RESERVEROOM");
        JButton modifyRoomButton = new JButton("MODIFYROOM");
        JButton reservationListButton = new JButton("RESERVATIONLIST");

        // Add buttons to the frame (and spaces between buttons)
        homePane.add(registerButton);
        homePane.add(reserveRoomButtom);
        homePane.add(modifyRoomButton);
        homePane.add(reservationListButton);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                MenuCreator.switchCard(e.getActionCommand());
                for(Component panel : cards.getComponents()) {
                    if(panel.isVisible()) {
                        CleverCards cc = (CleverCards) panel;
                        cc.refresh();
                    }
                }
            }
        };

        registerButton.addActionListener(listener);
        reserveRoomButtom.addActionListener(listener);
        modifyRoomButton.addActionListener(listener);
        reservationListButton.addActionListener(listener);

        return homePane;
    }


}
