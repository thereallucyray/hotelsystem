package csi3471.group5;

public class Main {
    public static void main(String[] args){
        //Create and launch the GUI
        UIHandler ui = new UIHandler();
        UIHandler.guiReserveRoom.createAndShowGUI();
        SystemHandler.handler().init();
    }
}
