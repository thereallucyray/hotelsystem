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

        hotel = new HotelStore().query().getOne();
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

    public boolean reserveRoom(RoomType roomType, Date start, Date end, Guest guest){
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
    public Guest validGuest(String username){
        for(int i = 0; i < hotel.getGuestList().size(); i++){
            Guest g = hotel.getGuestList().get(i);
            if(g.getUsername().equals(username)){
                return g;
            }
        }
        return null;
    }
    public Employee validEmployee(String username){
        for(int i = 0; i < hotel.getEmployeeList().size(); i++){
            Employee e = hotel.getEmployeeList().get(i);
            if(e.getUsername().equals(username)){
                return e;
            }
        }
        return null;
    }
    public boolean registerGuest(String username, String password, String phoneNumber){
        Guest g = hotel.registerGuest(username,password,phoneNumber);
        if(g == null) {
            return false;
        }
        guest = g;
        return true;
    }

    public boolean isEmployeeFacing() {
        return employeeFacing;
    }

    public LoginUser getLoggedInUser() {
        if(employeeFacing) {
            return employee;
        } else {
            return guest;
        }
    }

    public Guest getGuest() {
        if (employeeFacing) {
            throw new RuntimeException("Cannot get guest when employee facing");
        }
        return guest;
    }

    public Employee getEmployee() {
        if (!employeeFacing) {
            throw new RuntimeException("Cannot get employee when guest facing");
        }
        return employee;
    }
}