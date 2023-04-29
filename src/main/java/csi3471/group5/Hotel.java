package csi3471.group5;

import java.util.ArrayList;
import java.util.Date;

/**
 * Hotel class that contains all the general functionalities of the hotel
 */
public class Hotel {
    public enum qualityDesc {SUITE, ECONOMY, LUXURY}
    private String hotelName;
    private ArrayList<RoomType> roomTypes;
    private ArrayList<Guest> guestList;
    private ArrayList<Employee> employeeList;

    /**
     * Hotel class's constructor
     * @param name (hotel's name)
     */
    public Hotel(String name) {
        hotelName = name;

        guestList = new ArrayList<>();
        roomTypes = new ArrayList<>();
        employeeList = new ArrayList<>();
    }

    /**
     * Getter for the Hotel's name
     * @return String hotelName
     */
    public String getHotelName() {
        return hotelName;
    }


    /**
     * Sets the Hotel's name
     * @param hotelName
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * Adds a new room type to the hotel's list of room types
     * @param rt (RoomType)
     */
    public void addRoomType(RoomType rt){
        roomTypes.add(rt);
    }


    /**
     * Gets the whole list of hotel's room types
     * @return an array list of room types (ArrayList<RoomType>)
     */
    public ArrayList<RoomType> getRoomTypes(){
        return roomTypes;
    }

    /**
     * Adds a guest to the hotel's guest list
     * @param g (Guest)
     */
    public void addGuest(Guest g){
        guestList.add(g);
    }

    /**
     * Gets the list of the Hotel's registered guests
     * @return an array lists of guests
     */
    public ArrayList<Guest> getGuestList(){
        return guestList;
    }


    /**
     * Adds an employee to the hotel's employee list
     * @param e (Employee)
     */
    public void addEmployee(Employee e){
        employeeList.add(e);
    }

    /**
     * Gets the list of the Hotel's registered employee
     * @return an array lists of employees
     */
    public ArrayList<Employee> getEmployeeList(){
        return employeeList;
    }

    /**
     * Hotel's reserve room function that will return a true or false
     * based on whether the reservation was successful
     * @param rt (RoomType)
     * @param start (Date)
     * @param end (Date)
     * @param guest (Guest)
     * @return boolean based on whether reservation was sucessful
     */
    public boolean reserveRoom(RoomType rt, Date start, Date end, Guest guest){
        return reserveRoom(rt,start,end,guest,false);
    }

    /**
     * Hotel's reserve room function that has an additional feature of determining
     * whether the reservation is a corporate one
     * @param rt (RoomType)
     * @param start (Date)
     * @param end (Date)
     * @param guest (Guest)
     * @param isCorporate (boolean)
     * @return boolean based on whether reservation was sucessful
     */
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

    /**
     * Registers a new guest to the hotel
     * @param u (String) for username
     * @param p (String) for password
     * @param number (String) for guest's phone number
     * @return the new Guest if the guest was not already registered
     */
    public Guest registerGuest(String u, String p, String number){
        // check if a guest with this username already exists.
        for (Guest g: guestList) {
            if (g.getUsername().equals(u)) {
                return null;
            }
        }
        Guest newGuest = new Guest(u, p, number);
        guestList.add(newGuest);
        return newGuest;
    }
}
