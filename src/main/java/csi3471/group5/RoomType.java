package csi3471.group5;

import csi3471.group5.store.RoomStore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the RoomType object and its functionalities
 */
public class RoomType {
    private boolean isSmoking;
    private Integer numBeds;
    private Hotel.qualityDesc quality;
    private Double price;
    private List<Room> roomList;

    /**
     * RoomType default constructor
     */
    public RoomType() {
        this.isSmoking = false;
        this.numBeds = 0;
        this.quality = Hotel.qualityDesc.ECONOMY;
        this.price = 0.0;
        this.roomList = new ArrayList<Room>();
    }

    /**
     * RoomType's constructor with all of its needed parameters for variables
     * @param isSmoking (boolean)
     * @param numBeds (Integer)
     * @param quality (Hotel.qualityDesc)
     * @param price (Double)
     */
    public RoomType(boolean isSmoking, Integer numBeds, Hotel.qualityDesc quality, Double price) {
        this.isSmoking = isSmoking;
        this.numBeds = numBeds;
        this.quality = quality;
        this.price = price;
        this.roomList = new ArrayList<Room>();
    }

    /**
     * Checks to see whether this room type is smoking
     * @return boolean
     */
    public boolean isSmoking() {
        return isSmoking;
    }

    /**
     * Sets this RoomType to be smoking or not
     * @param smoking (boolean)
     */
    public void setSmoking(boolean smoking) {
        isSmoking = smoking;
    }

    /**
     * Gets the number of beds this RoomType has
     * @return Integer
     */
    public Integer getNumBeds() {
        return numBeds;
    }

    /**
     * Sets the number of beds this RoomType has
     * @param numBeds (Integer)
     */
    public void setNumBeds(Integer numBeds) {
        this.numBeds = numBeds;
    }

    /**
     * Gets the quality of this RoomType
     * @return Hotel.qualityDesc
     */
    public Hotel.qualityDesc getQuality() {
        return quality;
    }

    /**
     * Sets the quality of this RoomType
     * @param quality (Hotel.qualityDesc)
     */
    public void setQuality(Hotel.qualityDesc quality) {
        this.quality = quality;
    }

    /**
     * Gets the price for this RoomType
     * @return Double
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of this RoomType
     * @param price (Double)
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Adds another room to the RoomType's room list
     * @param r (Room)
     */
    public void addRoom(Room r){
        roomList.add(r);
    }

    /**
     * Removes a room from the RoomType's room list
     * @param r (Room)
     */
    public void removeRoom(Room r){
        roomList.remove(r);
    }

    /**
     * A helper function for the getAvailableRoom function that checks if dates for a reservation
     * overlap
     * @param StartDate1 (Date)
     * @param StartDate2 (Date)
     * @param EndDate1 (Date)
     * @param EndDate2 (Date)
     * @return boolean
     */
    private boolean overlap(Date StartDate1, Date StartDate2, Date EndDate1, Date EndDate2){
        //return ((StartDate1 <= EndDate2) && (StartDate2 <= EndDate1));
        return ((StartDate1.compareTo(EndDate2) < 0) && (StartDate2.compareTo(EndDate1) < 0));
    }

    /**
     * gets an available room for the specific dates of this room type
     * @param start (Date)
     * @param end (Date)
     * @return Room
     */
    public Room getAvailableRoom(Date start, Date end){
        Room newRoom = null;
        for(Room room: roomList){
            boolean found = true;
            for(Reservation res: room.getReservationList()){
                if(overlap(res.startDate, start, res.endDate, end)){
                    if(res.isActive()) {
                        found = false;
                    }
                }
            }
            if(found) {
                newRoom = room;
                break;
            }
        }
        return newRoom;
    }

    /**
     * Gets the RoomType's room list
     * @return List<Room>
     */
    public List<Room> getRoomList() {
        return roomList;
    }

    /**
     * Sets the RoomType's room list
     * @param roomList (List<Room>)
     */
    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    /**
     * RoomType's toString that constructs a message
     * with its necessary information
     * @return String (the message)
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(numBeds).append(" bed ");
        sb.append(quality).append(" (");
        if(isSmoking) sb.append("smoking");
        else sb.append("non-smoking");
        sb.append(") room for $").append(price);
        return sb.toString();
    }
}
