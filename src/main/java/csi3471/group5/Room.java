package csi3471.group5;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private Integer roomNumber;
    private Integer roomFloor;
    private RoomType rootType;
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

    public List<Reservation> getReservationList() {
        return reservationList;
    }
}
