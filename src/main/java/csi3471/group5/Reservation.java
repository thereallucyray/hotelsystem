package csi3471.group5;
import csi3471.group5.bank.Bank;
import csi3471.group5.bank.Receipt;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.round;

/**
 * This class represents the Reservation object and all of it's functionalities
 */
public class Reservation {
       Date startDate;
       Date endDate;
       boolean isCorporate;

       public enum Status{CREATED, CHECKED_IN, CHECKED_OUT, CANCELED, CANCELED_LATE}

       Status status;

       Room bookedRoom;
       Guest guest;

    /**
     * Reservation class constructor
      * @param start (Date)
     * @param end (Date)
     * @param room (Room)
     * @param guest (Guest)
     */
    public Reservation(Date start, Date end, Room room, Guest guest){
        isCorporate = false;
        status = Status.CREATED;
        startDate = start;
        endDate = end;
        bookedRoom = room;
        this.guest = guest;
    }

    /**
     * sets the Reservation's start date
     * @param d (Date)
     */
    public void setStartDate(Date d){
           startDate = d;
    }

    /**
     * Sets the Rervation's end date
     * @param d
     */
    public void setEndDate(Date d){
           endDate = d;
    }

    /**
     * Sets whether this reservation is corporate
     * @param b (boolean)
     */
    public void setCorporate(boolean b){
           isCorporate = b;
    }

    /**
     * sees whether this reservation is paid for
     * @return boolean
     */
    public boolean isPaid() {return !isActive();}

    /**
     * Gets the start date of the reservation
     * @return Date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of the reservation
     * @return Date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Checks to see if the reservation is for a corporation
     * @return boolean based on if it is corporate
     */
    public boolean isCorporate() {
        return isCorporate;
    }

    /**
     * Checks to see whether the reservation is action
     * (someone is currently staying in the assigned room)
     * @return boolean
     */
    public boolean isActive() {
        boolean late = new Date().after(endDate);
        if(late && status == Status.CREATED){
            status = Status.CHECKED_OUT;
        }
        return (status == Status.CREATED || status == Status.CHECKED_IN) && !late;
    }

    /**
     * Gets the specific room the reservation is booked for
     * @return Room
     */
    public Room getBookedRoom() {
        return bookedRoom;
    }

    /**
     * Sets the booked room that the reservation is for
     * @param bookedRoom (Room)
     */
    public void setBookedRoom(Room bookedRoom) {
        this.bookedRoom = bookedRoom;
    }

    /**
     * Gets the current status of the reservation
     * @return Status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the current status of the reservation
     * @param status (Status)
     */
    public void setStatus(Status status) {
        this.status = status;
    }


    /**
     * Gets the guest that is associated with the reservation
     * @return Guest
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * Sets the guest associated with the reservation
     * @param guest (Guest)
     */
    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    /**
     * Modifies the room type associated with the reservation
     * @param rt (RoomType)
     */
    public void modifyRoomType(RoomType rt){
        Room newRoom = null;
        //search through all the rooms of the given room type to find one with matching
        //availability
        newRoom = rt.getAvailableRoom(startDate, endDate);
        try{
            //if newRoom was null, no available room could be found
            if(newRoom == null){
                throw new NullPointerException();
            }

            //update the reservation lists of the new and old rooms, as well as the room
            //listed on the reservation
            bookedRoom.removeReservation(this);
            this.bookedRoom = newRoom;
            newRoom.addReservation(this);

        }catch(NullPointerException n){
            System.out.println("No new room found");
        }
    }

    /**
     * To String method that will provide the reservation information
     * @return String (the message)
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(bookedRoom.getRoomType().getQuality().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        sb.append(" for ").append(guest.getUsername());
        sb.append(" from ").append(sdf.format(startDate));
        sb.append(" to ").append(sdf.format(endDate));
        sb.append(" ").append(status.toString());
        return sb.toString();
    }

    /**
     * Modifies the dates for the reservation
     * @param start (Date)
     * @param end (Date)
     */
    public void modifyDateRange(Date start, Date end){
        Room newRoom = null;
        newRoom = this.bookedRoom.getRoomType().getAvailableRoom(start, end);

        try{
            //if newRoom was null, no available room could be found
            if(newRoom == null){
                throw new NullPointerException();
            }

            //update the reservation lists of the new and old rooms, as well as the room
            //listed on the reservation
            bookedRoom.removeReservation(this);
            this.startDate = start;
            this.endDate = end;
            this.bookedRoom = newRoom;
            newRoom.addReservation(this);


        }catch(NullPointerException n){
            System.out.println("No new room found");
        }
    }

    /**
     * Changes the status of the reservation to Checked In
     */
    public void checkIn(){
        this.status = Status.CHECKED_IN;
    }

    /**
     * Changes the status of the reservation to Checked Out
     */
    public void checkOut(){
        status = Status.CHECKED_OUT;
    }

    /**
     * Changes the status of the reservation to cancelled and determines
     * if the cancellation was late by the hotel's policy or not
     */
    public void cancelRes(){
        Bank bank = new Bank();
        //determine if cancellation is late
        Date cancelTime = new Date();
        int diffInDays = round((float) (startDate.getTime() - cancelTime.getTime()) / (1000 * 60 * 60 * 24));
        boolean isLate = (diffInDays < 2);
        Receipt rec;
        if(isLate){
            status = Status.CANCELED_LATE;
        } else {
            status = Status.CANCELED;
        }
    }

    /**
     * Retrieve's the guest's receipt from their stay at the hotel
     * @return Receipt
     */
    public Receipt getReceipt() {
        if(isActive()) {
            return null;
        }
        Bank bank = new Bank();
        Receipt rec;
        String bankHolder = isCorporate ? "Corporate" : guest.getUsername();
        if(status == Status.CHECKED_OUT) {
            Integer diffInDays = round((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
            Double total = bookedRoom.getRoomType().getPrice() * diffInDays;
            rec = bank.getReceipt(guest.getBankToken(), total, bankHolder,
                    bookedRoom.getRoomType().getQuality().toString());
        } else if(status == Status.CANCELED) {
            rec = bank.getReceipt(guest.getBankToken(), 0.0, bankHolder,"Reservation Canceled");
        } else {
            Double total = bookedRoom.getRoomType().getPrice() * 0.8;
            rec = bank.getReceipt(guest.getBankToken(), total, bankHolder,
                    "Late Cancellation of: " + bookedRoom.getRoomType().getQuality().toString());
        }
        return rec;
    }

    /**
     * Determines if the guest has a receipt fromm the reservation
     * based on the status of the reservation
     * @return boolean
     */
    public boolean hasReceipt() {
        return (status == Status.CHECKED_OUT || status == Status.CANCELED_LATE);
    }

    /**
     * Determines whether or not the reservation can be modified
     * based on how many days it is before the start date
     * @return boolean
     */
    public boolean canModify() {
        return (new Date().before(startDate));
    }
}