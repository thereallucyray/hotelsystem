package csi3471.group5;

import csi3471.group5.store.RoomTypeStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Room {
    private Integer roomNumber;
    private Integer roomFloor;
    private RoomType rootType;
    private List<Reservation> reservationList;

    public Room(Integer roomNum, Integer roomFloor, RoomType type){
        this.roomNumber = roomNum;
        this.roomFloor = roomFloor;
        this.rootType = type;
        this.reservationList = new ArrayList<Reservation>();
    }

    public void setRoomNumber(Integer n){
        this.roomNumber = n;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return rootType;
    }

    public void setRoomType(RoomType rootType) {
        this.rootType = rootType;
    }

    public Integer getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(Integer roomFloor) {
        this.roomFloor = roomFloor;
    }

    public void addReservation(Reservation r){
        this.reservationList.add(r);
    }
    public void removeReservation(Reservation r){this.reservationList.remove(r);}

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

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    /* rt -> new room type
       1. removes room from old room type's list
       2. changes room's type
       3. adds room to new roomType's list
     */
    public void modifyRoomType(RoomType rt){
        RoomType oldRt = this.getRoomType();
        oldRt.removeRoom(this);
        this.setRoomType(rt);
        rt.addRoom(this);
    }

}
