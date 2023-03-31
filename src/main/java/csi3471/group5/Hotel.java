package csi3471.group5;

import csi3471.group5.db.DBStore;
import csi3471.group5.store.GuestStore;
import csi3471.group5.store.ReservationStore;
import csi3471.group5.store.RoomStore;
import csi3471.group5.store.RoomTypeStore;

import java.util.ArrayList;
import java.util.Date;

public class Hotel {
    public enum qualityDesc {SUITE, ECONOMY, LUXURY}
    private String hotelName;
    private ArrayList<RoomType> roomTypes;
    private ArrayList<Guest> guestList;
    public Hotel(String name) {
        hotelName = name;

        //initializing guestList
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

    public boolean reserveRoom(RoomType rt, Date start, Date end, int guestId){
        //determine if there is an available room of type rt
        //you can use the getAvailableRoom() function

        //if null is returned from getAvailableRoom(), no room was available, and false
        //should be returned from this method

        //if a room is returned from getAvailableRoom(), create a reservation, and associate it
        //with the guest and the room
        //true should be returned from this method.

        boolean reserveSuccessful = false;

        //if there's a room to reserve
        if( (rt.getAvailableRoom(start, end)) != null){
            reserveSuccessful = true;

            //create new reservation
            Reservation newReservation = new Reservation(start, end,
                    (rt.getAvailableRoom(start, end))); //add isActive?

            //associate new reservation with room
            (rt.getAvailableRoom(start, end)).addReservation(newReservation);

            //associate reservation with guest
            for(int j = 0; j < guestList.size(); j++){
                if(guestList.get(j).getUserID() == guestId){
                    guestList.get(j).addReservation(newReservation);
                }
            }
        }

        return reserveSuccessful;
    }

    public void registerGuest(int id, String u, String p, int number){
        Guest newGuest = new Guest(id, u, p, number);

        guestList.add(newGuest);
    }
}
