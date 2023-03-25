package csi3471.group5;

import csi3471.group5.store.RoomTypeStore;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private Integer roomNumber;
    private Integer roomFloor;
    private RoomType rootType;

    private int roomtypeindex;
    private List<Reservation> reservationList;

    public Room(){
        this.roomNumber = -1;
        this.roomFloor = -1;
        this.rootType = null;
        this.reservationList = new ArrayList<Reservation>();
    }

    public Room(Integer roomNum, Integer roomFloor, RoomType root){
        this.roomNumber = roomNum;
        this.roomFloor = roomFloor;
        this.rootType = root;
        this.reservationList = new ArrayList<Reservation>();
    }

    public void setRoomNumber(Integer n){
        this.roomNumber = n;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public int getRoomtypeindex() {
        return roomtypeindex;
    }

    public void setRoomtypeindex(int roomtypeindex) {
        this.roomtypeindex = roomtypeindex;
    }

    public RoomType getRootType() {
        return rootType;
    }

    public void setRootType(RoomType rootType) {
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
        final StringBuilder sb = new StringBuilder("Room{");
        sb.append("roomNumber=").append(roomNumber);
        sb.append(", roomFloor=").append(roomFloor);
        sb.append(", roomtypeindex=").append(roomtypeindex);
        sb.append(", reservationList=").append(reservationList);
        sb.append('}');
        return sb.toString();
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
        RoomType oldRt = this.getRootType();
        oldRt.removeRoom(this);
        this.setRootType(rt);
        rt.addRoom(this);
    }

}
