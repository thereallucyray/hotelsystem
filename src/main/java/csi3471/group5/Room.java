package csi3471.group5;

public class Room {
    private Integer roomNumber;
    private Integer roomFloor;
    private RoomType rootType;

    public Room(){
        this.roomNumber = -1;
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
}
