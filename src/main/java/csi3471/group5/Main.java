package csi3471.group5;


import javax.swing.*;

/**
 * Main class
 */
public class Main {
    /**
     * main method
     * @param args string arguments
     */
    public static void main(String[] args){
        //Create and launch the GUI
        SystemHandler.handler().init();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UIHandler.createAndShowGUI();
            }
        });
    }
}
