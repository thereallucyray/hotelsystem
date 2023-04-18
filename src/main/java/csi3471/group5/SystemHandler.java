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

    private SystemHandler() throws RuntimeException {
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

    public boolean reserveRoom(RoomType roomType, Date start, Date end){
        return hotel.reserveRoom(roomType, start, end, guest);
    }
    public boolean modifyRoom(int roomNumber, RoomType roomType){
        for (Room r : new RoomStore().query().get()) {
            if (Objects.equals(r.getRoomNumber(), roomNumber)) {
                r.modifyRoomType(roomType);
                return true;
            }
        }
        return false;
    }
    public boolean validGuest(String username){
        for(int i = 0; i < hotel.getGuestList().size(); i++){
            if(hotel.getGuestList().get(i).getUsername().equals(username)){
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
    public boolean login(String username, String password, Boolean isEmployee){
        employeeFacing = isEmployee;
        if(isEmployee){
            Employee e = new EmployeeStore().login(username,password);
            employee = e;
            return e != null;
        }
        else{
            Guest g = new GuestStore().login(username,password);
            guest = g;
            return g != null;
        }
    }
}
