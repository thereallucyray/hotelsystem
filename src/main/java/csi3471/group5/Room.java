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
        RoomTypeStore roomTypeStore = new RoomTypeStore();
        this.roomtypeindex = roomtypeindex;
        this.rootType = roomTypeStore.getList().get(roomtypeindex);
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
        sb.append(", rootType=").append(rootType);
        sb.append(", roomtypeindex=").append(roomtypeindex);
        sb.append(", reservationList=").append(reservationList);
        sb.append('}');
        return sb.toString();
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }
}
