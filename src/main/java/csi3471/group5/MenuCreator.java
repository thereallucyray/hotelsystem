package csi3471.group5;
import csi3471.group5.db.DBStore;
import csi3471.group5.gui.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MenuCreator extends UIHandler{

    private static JPanel cards = null;
    Container pane;

    MenuCreator(){

    }
    MenuCreator(JPanel c,Container p ){
        cards = c;
        pane = p;
    }

    public static void setCardLayout(JPanel panel) {
        cards = panel;
    }
    public static void switchCard(String card) {
        if(cards == null) {
            throw new NullPointerException("Fix your stuff.");
        }
        System.out.println(card);
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, card);
    }

    public static JPanel createMenuBar(){
    //public void addComponentToPane(Container pane) {

        //JFrame frame = new JFrame("Hotel System");
        JPanel homePane = new JPanel(); //use FlowLayout

        JButton registerButton = new JButton("ADDGUEST");
        JButton reserveRoomButtom = new JButton("RESERVEROOM");
        JButton modifyRoomButton = new JButton("MODIFYROOM");
        JButton reservationListButton = new JButton("RESERVATIONLIST");
        JButton roomViewButton = new JButton("ROOMVIEW");

        // Add buttons to the frame (and spaces between buttons)
        homePane.add(registerButton);
        homePane.add(reserveRoomButtom);
        homePane.add(modifyRoomButton);
        homePane.add(reservationListButton);
        homePane.add(roomViewButton);

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
        roomViewButton.addActionListener(listener);

        /*frame.add(homePane);
        frame.pack();
        frame.setVisible(true);*/
        return homePane;
    }


}
