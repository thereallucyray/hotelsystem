package csi3471.group5;

import csi3471.group5.store.*;

import java.util.Date;
import java.util.Objects;

/**
 * This class represents the SystemHandler
 * which is used for variaus backend functionalities
 */
public class SystemHandler {
    private Hotel hotel;
    private boolean employeeFacing;
    private Employee employee;
    private Guest guest;

    private static SystemHandler instance;

    /**
     * Creates an instance of the SystemHandler if it is null
     * @return SystemHandler
     */
    public static SystemHandler handler(){
        if(instance == null){
            instance = new SystemHandler();
        }
        return instance;
    }

    /**
     * initializes the system
     */
    public void init() {}

    /**
     * SystemHandler constructor
     * @throws RuntimeException
     */
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

    /**
     * Reserve room functionality with the specific details to make a reservation
     * @param roomType (RoomType)
     * @param start (Date)
     * @param end (Date)
     * @param guest (Guest)
     * @param isCorporate (boolean)
     * @return boolean (if successful)
     */
    public boolean reserveRoom(RoomType roomType, Date start, Date end, Guest guest, boolean isCorporate){
        return hotel.reserveRoom(roomType, start, end, guest, isCorporate);
    }

    /**
     * Modifies a specific room with a different room type if needed
     * @param roomNumber (int)
     * @param roomType (RoomType)
     * @return boolean
     */
    public boolean modifyRoom(int roomNumber, RoomType roomType){
        for (Room r : new RoomStore().query().get()) {
            if (Objects.equals(r.getRoomNumber(), roomNumber)) {
                r.modifyRoomType(roomType);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if a guest is a registered and valid guest
     * @param username (String)
     * @return Guest
     */
    public Guest validGuest(String username){
        for(int i = 0; i < hotel.getGuestList().size(); i++){
            Guest g = hotel.getGuestList().get(i);
            if(g.getUsername().equals(username)){
                return g;
            }
        }
        return null;
    }

    /**
     * Checks to see if an employee is a registered and valid employee
     * @param username (String)
     * @return Employee
     */
    public Employee validEmployee(String username){
        for(int i = 0; i < hotel.getEmployeeList().size(); i++){
            Employee e = hotel.getEmployeeList().get(i);
            if(e.getUsername().equals(username)){
                return e;
            }
        }
        return null;
    }

    /**
     * Registers a new guest into the hotel's system
     * @param username (String)
     * @param password (String)
     * @param phoneNumber (String)
     * @return boolean (if successful)
     */
    public boolean registerGuest(String username, String password, String phoneNumber){
        Guest g = hotel.registerGuest(username,password,phoneNumber);
        if(g == null) {
            return false;
        }
        guest = g;
        return true;
    }

    /**
     * Checks to see if its an employee facing
     * @return boolean
     */
    public boolean isEmployeeFacing() {
        return employeeFacing;
    }

    /**
     * Sees whether its an employee or guest logged in
     * @return LoginUser
     */
    public LoginUser getLoggedInUser() {
        if(employeeFacing) {
            return employee;
        } else {
            return guest;
        }
    }

    /**
     * Gets the guest if its not employee facing
     * @return Guest
     */
    public Guest getGuest() {
        if (employeeFacing) {
            throw new RuntimeException("Cannot get guest when employee facing");
        }
        return guest;
    }

    /**
     * Gets the employee if it is employee facing
     * @return Employee
     */
    public Employee getEmployee() {
        if (!employeeFacing) {
            throw new RuntimeException("Cannot get employee when guest facing");
        }
        return employee;
    }

    /**
     * The log in functionality using username, password, and isEmployee
     * @param username (String)
     * @param password (String)
     * @param isEmployee (Boolean)
     * @return boolean
     */
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

