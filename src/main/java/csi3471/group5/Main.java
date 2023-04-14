package csi3471.group5;

import csi3471.group5.gui.AddGuestUI;

import javax.swing.*;

public class
Main {
    public static void main(String[] args){
        //Create and launch the GUI
        SystemHandler.handler().init();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UIHandler.createAndShowGUI();
            }
        });
        MenuCreator.createMenuBar();
    }
}
