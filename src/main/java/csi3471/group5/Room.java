package csi3471.group5;

import csi3471.group5.store.RoomTypeStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents the Room object and its functionalities
 */
public class Room {
    private Integer roomNumber;
    private Integer roomFloor;
    private RoomType rootType;
    private List<Reservation> reservationList;

    /**
     * Constructor for the Room class
     * @param roomNum (Integer)
     * @param roomFloor (Integer)
     * @param type (RoomType)
     */
    public Room(Integer roomNum, Integer roomFloor, RoomType type){
        this.roomNumber = roomNum;
        this.roomFloor = roomFloor;
        this.rootType = type;
        this.reservationList = new ArrayList<Reservation>();
    }

    /**
     * Sets the room number for the room
     * @param n (Integer)
     */
    public void setRoomNumber(Integer n){
        this.roomNumber = n;
    }

    /**
     * Gets the room's room number
     * @return Integer
     */
    public Integer getRoomNumber() {
        return roomNumber;
    }

    /**
     * Gets the room type of the room
     * @return RoomType
     */
    public RoomType getRoomType() {
        return rootType;
    }

    /**
     * Sets the room type for the room object
     * @param rootType (RoomType)
     */
    public void setRoomType(RoomType rootType) {
        this.rootType = rootType;
    }

    /**
     * Gets the room's floor number
     * @return Integer
     */
    public Integer getRoomFloor() {
        return roomFloor;
    }

    /**
     * Sets the room's floor number
     * @param roomFloor (Integer)
     */
    public void setRoomFloor(Integer roomFloor) {
        this.roomFloor = roomFloor;
    }

    /**
     * If a reservation is made to this room,
     * that reservation will be added to the room's object
     * @param r (Reservation)
     */
    public void addReservation(Reservation r){
        this.reservationList.add(r);
    }

    /**
     * Removes an associated reservation to this room if called
     * @param r (Reservation)
     */
    public void removeReservation(Reservation r){this.reservationList.remove(r);}

    /**
     * Room class's toString that will return the needed information for a room
     * @return String (the info message)
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Room ");
        sb.append(roomNumber);
        Reservation r = getActiveOrFutureReservation();
        if (r != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            if(r.status == Reservation.Status.CHECKED_IN){
                sb.append(" is currently occupied by ");
                sb.append(r.getGuest().getUsername());
                sb.append(" until ");
                sb.append(sdf.format(r.getEndDate()));
            } else {
                sb.append(" is reserved by ");
                sb.append(r.getGuest().getUsername());
                sb.append(" from ");
                sb.append(sdf.format(r.getStartDate()));
                sb.append(" to ");
                sb.append(sdf.format(r.getEndDate()));
            }
        }
        return sb.toString();
    }

    /**
     * Gets the rooms next or current reservation
     * @return Reservation
     */
    private Reservation getActiveOrFutureReservation() {
        long fewestDays = Integer.MAX_VALUE;
        Reservation res = null;
        for (Reservation r : reservationList) {
            long daysUntil = (r.getStartDate().getTime() - new Date().getTime());
            if (daysUntil > 0 && daysUntil < fewestDays) {
                fewestDays = daysUntil;
                res = r;
            }
            if (r.status == Reservation.Status.CHECKED_IN) {
                return r;
            }
        }
        return res;
    }

    /**
     * Gets the list of Reservations that are made to this Room
     * @return List<Reservation>
     */
    public List<Reservation> getReservationList() {
        return reservationList;
    }

    /* rt -> new room type
       1. removes room from old room type's list
       2. changes room's type
       3. adds room to new roomType's list
     */

    /**
     * Method that is able to modify this room's roomtype if needed
     * @param rt (RoomType)
     */
    public void modifyRoomType(RoomType rt){
        RoomType oldRt = this.getRoomType();
        oldRt.removeRoom(this);
        this.setRoomType(rt);
        rt.addRoom(this);
    }

}
