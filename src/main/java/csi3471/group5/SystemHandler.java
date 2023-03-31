package csi3471.group5;

import csi3471.group5.store.*;

import java.util.Date;
import java.util.Objects;

public class SystemHandler {
    private Hotel hotel;
    private boolean employeeFacing;
    private Employee employee;
    private Guest guest;

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

        guest = new GuestStore().login("uberguest", "password");
        if(guest == null) {
            throw new RuntimeException("Guest not found in database");
        }
        employee = new EmployeeStore().login("admin1","superduperpassword");
        if(employee == null) {
            throw new RuntimeException("Employee not found in database");
        }
        employeeFacing = true;
    }

    public boolean reserveRoom(Integer roomType, Date start, Date end){
        RoomType rt = new RoomTypeStore().getByID(roomType);
        return hotel.reserveRoom(rt, start, end, guest);
    }
    public boolean modifyRoom(int roomNumber, int roomTypeId) {
        for (Room r : new RoomStore().query().get()) {
            if (Objects.equals(r.getRoomNumber(), roomNumber)) {
                RoomType rt = new RoomTypeStore().getByID(roomTypeId);
                r.modifyRoomType(rt);
                return true;
            }
        }
        return false;
    }
    public boolean registerGuest(String username, String password, String phoneNumber){
        Guest g = hotel.registerGuest(username,password,phoneNumber);
        if(g == null) {
            return false;
        }
        guest = g;
        return true;
    }
}
