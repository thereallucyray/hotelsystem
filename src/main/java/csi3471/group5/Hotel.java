package csi3471.group5;

import java.util.ArrayList;
import java.util.Date;

public class Hotel {
    public enum qualityDesc {SUITE, ECONOMY, LUXURY}
    private String hotelName;
    private ArrayList<RoomType> roomTypes;
    private ArrayList<Guest> guestList;
    public Hotel(String name) {
        hotelName = name;

        guestList = new ArrayList<>();
        roomTypes = new ArrayList<>();
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void addRoomType(RoomType rt){
        roomTypes.add(rt);
    }

    public ArrayList<RoomType> getRoomTypes(){
        return roomTypes;
    }

    public void addGuest(Guest g){
        guestList.add(g);
    }

    public ArrayList<Guest> getGuestList(){
        return guestList;
    }

    public boolean reserveRoom(RoomType rt, Date start, Date end, Guest guest){
        //determine if there is an available room of type rt
        //you can use the getAvailableRoom() function

        //if null is returned from getAvailableRoom(), no room was available, and false
        //should be returned from this method

        //if a room is returned from getAvailableRoom(), create a reservation, and associate it
        //with the guest and the room
        //true should be returned from this method.

        boolean reserveSuccessful = false;
        Room room = rt.getAvailableRoom(start, end);

        //if there's a room to reserve
        if(room != null){
            reserveSuccessful = true;

            //create new reservation
            Reservation newReservation = new Reservation(start, end, room,guest);
            //associate new reservation with room
            room.addReservation(newReservation);
        }

        return reserveSuccessful;
    }

    public boolean registerGuest(int id, String u, String p, String number){
        // check if a guest with this username already exists.
        for (Guest g: guestList) {
            if (g.getUsername().equals(u)) {
                return false;
            }
        }
        Guest newGuest = new Guest(id, u, p, number);

        guestList.add(newGuest);
        return true;
    }
}
