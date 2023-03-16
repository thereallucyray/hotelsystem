package csi3471.group5;

public class Room {
    private Integer roomNumber;

    public Room(){
        this.roomNumber = -1;
    }

    public void setRoomNumber(Integer n){
        this.roomNumber = n;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }
}
