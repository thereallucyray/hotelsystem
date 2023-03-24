package csi3471.group5;

import csi3471.group5.db.Database;
import csi3471.group5.store.RoomStore;
import csi3471.group5.store.RoomTypeStore;

import java.util.ArrayList;
import java.util.Date;

public class Hotel {
    public enum qualityDesc {SUITE, ECONOMY, LUXURY};
    private String hotelName;
    private ArrayList<RoomType> roomTypes;
    private ArrayList<Guest> guestList;
    Hotel(String name) {
        RoomTypeStore roomTypeStore = new RoomTypeStore();

        roomTypes = roomTypeStore.query().get();
        for(int i = 0; i < roomTypes.size(); i++) {
            roomTypes.get(i).loadRooms(i);
        }
        roomTypes.forEach(System.out::println);

        hotelName = name;
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
        return reserveSuccessful;
    }
}
