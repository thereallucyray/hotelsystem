package csi3471.group5;

import csi3471.group5.gui.AddGuestUI;

public class Main {
    public static void main(String[] args){
        //Create and launch the GUI
        UIHandler.createAndShowGUI();
        SystemHandler.handler().init();
    }
}
