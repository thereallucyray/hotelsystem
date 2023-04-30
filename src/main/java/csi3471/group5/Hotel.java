package csi3471.group5;

import java.util.ArrayList;
import java.util.Date;

public class Hotel {
    public enum qualityDesc {SUITE, ECONOMY, LUXURY}
    private String hotelName;
    private ArrayList<RoomType> roomTypes;
    private ArrayList<Guest> guestList;
    private ArrayList<Employee> employeeList;
    public Hotel(String name) {
        hotelName = name;

        guestList = new ArrayList<>();
        roomTypes = new ArrayList<>();
        employeeList = new ArrayList<>();
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

    public void addEmployee(Employee e){
        employeeList.add(e);
    }

    public ArrayList<Employee> getEmployeeList(){
        return employeeList;
    }
    public boolean reserveRoom(RoomType rt, Date start, Date end, Guest guest){
        return reserveRoom(rt,start,end,guest,false);
    }

    public boolean reserveRoom(RoomType rt, Date start, Date end, Guest guest, boolean isCorporate){
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
            newReservation.setCorporate(isCorporate);
            //associate new reservation with room
            room.addReservation(newReservation);
            guest.addReservation(newReservation);
        }

        return reserveSuccessful;
    }

    public Guest registerGuest(String u, String p, String number){
        // check if a guest with this username already exists.
        for (Guest g: guestList) {
            if (g.getUsername().equals(u)) {
                return null;
            }
        }
        Guest newGuest = new Guest(u, p, number);
        newGuest.setPassword(p);
        addGuest(newGuest);
        return newGuest;
    }
}
