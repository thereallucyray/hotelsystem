package csi3471.group5;

public class SystemHandler {
    static Hotel hotel;

    public static void main(String[] args){
        hotel = new Hotel("Teal");
        //Guest guest = new Guest(1, "password", 50312345678);


        //NOTE: I am still trying to figure out how to return information obtained
        //in the UI to the System Handler object -Lucy
        UIHandler ui = new UIHandler();
        UIHandler.guiReserveRoom.createAndShowGUI();

        boolean reserved;
        //reserved = hotel.reserveRoom(roomType, startDate, endDate, guest.getUserID());


    }

}
