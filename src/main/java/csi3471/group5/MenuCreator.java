package csi3471.group5;
import csi3471.group5.db.DBStore;
import csi3471.group5.gui.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MenuCreator implements ActionListener{

    public static JPanel createMenuBar(){
    //public void addComponentToPane(Container pane) {

        JFrame frame = new JFrame("Hotel System");
        JPanel homePane = new JPanel(); //use FlowLayout

        JButton registerButton = new JButton("ADDGUEST");
        JButton reserveRoomButtom = new JButton("RESERVEROOM");
        //reserveRoomButtom.addActionListener(this);
        JButton modifyRoomButton = new JButton("MODIFYROOM");
        //modifyRoomButton.addActionListener(this);
        JButton reservationListButton = new JButton("RESERVATIONLIST");
        //reservationListButton.addActionListener(this);

        // Add buttons to the frame (and spaces between buttons)
        homePane.add(registerButton);
        homePane.add(reserveRoomButtom);
        homePane.add(modifyRoomButton);
        homePane.add(reservationListButton);
        frame.add(homePane);
        frame.pack();
        frame.setVisible(true);
        return homePane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
