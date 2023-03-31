package csi3471.group5;

import csi3471.group5.store.*;

import java.util.Date;

public class SystemHandler {
    private Hotel hotel;
    private Guest loggedInGuest;

    private static SystemHandler instance;

    public static SystemHandler handler(){
        if(instance == null){
            instance = new SystemHandler();
        }
        return instance;
    }

    public void init() {}

    private SystemHandler(){
        new GuestStore().init();
        new ReservationStore().init();
        new RoomTypeStore().init();
        new RoomStore().init();
        new EmployeeStore().init();

        hotel = new HotelStore().query().getIndex(0);
        if(hotel == null) {
            throw new RuntimeException("Hotel not found in database");
        }

        loggedInGuest = new Guest(10, "FakeGuest","Guest", "");

    }

    public boolean reserveRoom(Integer roomType, Date start, Date end, int id){
        RoomType rt = new RoomTypeStore().getByID(roomType);
        return hotel.reserveRoom(rt, start, end, loggedInGuest);
    }
}
