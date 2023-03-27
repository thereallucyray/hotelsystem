package csi3471.group5;

import csi3471.group5.db.DBStore;
import csi3471.group5.db.Database;
import csi3471.group5.store.GuestStore;
import csi3471.group5.store.ReservationStore;
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
        new RoomStore().init();
        new ReservationStore().init();
        new GuestStore().init();

        roomTypes = new RoomTypeStore().query().get();
        roomTypes.forEach(System.out::println);

        RoomType rt = new RoomTypeStore().query().getOne();
        Room newroom = new Room(1000,1,rt);
        Reservation newres = new Reservation(new Date(), new Date(), newroom);
        newres.setGuest(new Guest(0, "John", "Doe", 469900920));
        newroom.addReservation(newres);
        rt.addRoom(newroom);

        DBStore.saveAll();
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
