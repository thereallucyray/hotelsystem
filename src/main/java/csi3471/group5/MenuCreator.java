package csi3471.group5;
import csi3471.group5.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MenuCreator extends UIHandler{

    private static JPanel cards = null;
    Container pane;

    public static void setCardLayout(JPanel panel) {
        cards = panel;
    }
    public static void switchCard(String card) {
        if(cards == null) {
            throw new NullPointerException("Fix your stuff.");
        }
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, card);
        for(Component panel : cards.getComponents()) {
            if(panel.isVisible()) {
                CleverCards cc = (CleverCards) panel;
                cc.refresh();
            }
        }
    }

    public static JPanel createMenuBar(){
        JPanel homePane = new JPanel(); //use FlowLayout
        homePane.setLayout(new GridLayout());

        JButton loginButton = new JButton("Logout");
        loginButton.setActionCommand("LOGIN");
        JButton registerButton = new JButton("Add Guest");
        registerButton.setActionCommand("ADDGUEST");
        JButton reserveRoomButtom = new JButton("Reserve Room");
        reserveRoomButtom.setActionCommand("RESERVEROOM");
        JButton modifyRoomButton = new JButton("Modify Room");
        modifyRoomButton.setActionCommand("MODIFYROOM");
        JButton reservationListButton = new JButton("Reservations");
        reservationListButton.setActionCommand("RESERVATIONLIST");
        JButton roomViewButton = new JButton("Rooms");
        roomViewButton.setActionCommand("ROOMVIEW");
        JButton profileButton = new JButton("Profile");
        profileButton.setActionCommand("MODIFYPROFILE");
        JButton modifyUserButton = new JButton("Modify User");
        modifyUserButton.setActionCommand("MODIFYUSER");

        // Add buttons to the frame (and spaces between buttons)
        if(SystemHandler.handler().isEmployeeFacing()) {
            homePane.add(registerButton);
        }
        homePane.add(reserveRoomButtom);
        if(SystemHandler.handler().isEmployeeFacing()) {
            homePane.add(modifyRoomButton);
        }
        homePane.add(reservationListButton);
        if(SystemHandler.handler().isEmployeeFacing()) {
            homePane.add(roomViewButton);
            homePane.add(modifyUserButton);
        }
        homePane.add(profileButton);
        homePane.add(loginButton);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuCreator.switchCard(e.getActionCommand());
            }
        };

        loginButton.addActionListener(listener);
        registerButton.addActionListener(listener);
        reserveRoomButtom.addActionListener(listener);
        modifyRoomButton.addActionListener(listener);
        reservationListButton.addActionListener(listener);
        roomViewButton.addActionListener(listener);
        profileButton.addActionListener(listener);
        modifyUserButton.addActionListener(listener);

        return homePane;
    }


}
