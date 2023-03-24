package csi3471.group5;

import csi3471.group5.store.RoomStore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RoomType {
    private boolean isSmoking;
    private Integer numBeds;
    private Hotel.qualityDesc quality;
    private Double price;
    private List<Room> roomList;

    public RoomType() {
        this.isSmoking = false;
        this.numBeds = 0;
        this.quality = Hotel.qualityDesc.ECONOMY;
        this.price = 0.0;
        this.roomList = new ArrayList<Room>();
    }

    public RoomType(boolean isSmoking, Integer numBeds, Hotel.qualityDesc quality, Double price) {
        this.isSmoking = isSmoking;
        this.numBeds = numBeds;
        this.quality = quality;
        this.price = price;
        this.roomList = new ArrayList<Room>();
    }

    public boolean isSmoking() {
        return isSmoking;
    }

    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    public Integer getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(Integer numBeds) {
        this.numBeds = numBeds;
    }

    public Hotel.qualityDesc getQuality() {
        return quality;
    }

    public void setQuality(Hotel.qualityDesc quality) {
        this.quality = quality;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addRoom(Room r){
        roomList.add(r);
    }
    public void loadRooms(int index) {
        RoomStore roomStore = new RoomStore();
        roomList = roomStore.query().get().stream().filter(room -> room.getRoomtypeindex() == index).collect(Collectors.toList());
        roomList.forEach(room -> {
            room.setRoomtypeindex(index);
            room.setRootType(this);
        });
    }
    //overlap is a helper function for the getAvailableRoom function
    private boolean overlap(Date StartDate1, Date StartDate2, Date EndDate1, Date EndDate2){
        //return ((StartDate1 <= EndDate2) && (StartDate2 <= EndDate1));
        //note: we may want to remove the equals check, as someone can book a room for the same
        //      day another customer checks out
        return ((StartDate1.compareTo(EndDate2) <= 0) && (StartDate2.compareTo(EndDate1) <= 0));
    }
    public Room getAvailableRoom(Date start, Date end){
        Room newRoom = null;
        for(Room room: roomList){
            for(Reservation res: room.getReservationList()){
                if(!overlap(res.startDate, start, res.endDate, end)){
                    newRoom = room;
                }
            }
        }
        return newRoom;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RoomType{");
        sb.append("isSmoking=").append(isSmoking);
        sb.append(", numBeds=").append(numBeds);
        sb.append(", quality=").append(quality);
        sb.append(", price=").append(price);
        sb.append(", roomList=").append(roomList);
        sb.append(", room count=").append(roomList.size());
        sb.append('}');
        return sb.toString();
    }
}
