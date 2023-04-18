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

        JButton loginButton = new JButton("LOGIN");

        JButton registerButton = new JButton("ADDGUEST");
        JButton reserveRoomButtom = new JButton("RESERVEROOM");
        //Sys
        //
        //
        // tem.out.println(reserveRoomButtom.getActionCommand());
        //var ui = new UIHandler();
        //ui.addComponentToPane(new Container());
        /*reserveRoomButtom.addActionListener(e -> {
            *//*var ui = new UIHandler();
            CardLayout cl = (CardLayout)(ui.cards.getLayout());
            cl.show(ui.cards, e.getActionCommand());
            CleverCards cc = (CleverCards)ui.card2;
            cc.refresh();*//*
            new UIHandler();
        });*/
        JButton modifyRoomButton = new JButton("MODIFYROOM");
        //modifyRoomButton.addActionListener(this);
        JButton reservationListButton = new JButton("RESERVATIONLIST");
        //reservationListButton.addActionListener(this);

        // Add buttons to the frame (and spaces between buttons)
        homePane.add(loginButton);

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

        loginButton.addActionListener(listener);

        registerButton.addActionListener(listener);
        reserveRoomButtom.addActionListener(listener);
        modifyRoomButton.addActionListener(listener);
        reservationListButton.addActionListener(listener);

        /*frame.add(homePane);
        frame.pack();
        frame.setVisible(true);*/
        return homePane;
    }


}
