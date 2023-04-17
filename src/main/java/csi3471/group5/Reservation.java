package csi3471.group5;
import csi3471.group5.bank.Bank;
import csi3471.group5.bank.Receipt;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.round;

public class Reservation {
       Date startDate;
       Date endDate;
       boolean isCorporate;

       public enum Status{CREATED, CHECKED_IN, CHECKED_OUT, CANCELED}

       Status status;

       Room bookedRoom;
       Guest guest;
    public Reservation(Date start, Date end, Room room, Guest guest){
        isCorporate = false;
        status = Status.CREATED;
        startDate = start;
        endDate = end;
        bookedRoom = room;
        this.guest = guest;
    }
    public void setStartDate(Date d){
           startDate = d;
    }
    public void setEndDate(Date d){
           endDate = d;
    }

    public void setCorporate(boolean b){
           isCorporate = b;
    }

    public boolean isPaid() {return !isActive();}

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isCorporate() {
        return isCorporate;
    }

    public boolean isActive() {
        return (status == Status.CREATED || status == Status.CHECKED_IN);
    }

    public Room getBookedRoom() {
        return bookedRoom;
    }

    public void setBookedRoom(Room bookedRoom) {
        this.bookedRoom = bookedRoom;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(bookedRoom.getRoomType().getQuality().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        sb.append(" for ").append(guest.getUsername());
        sb.append(" from ").append(sdf.format(startDate));
        sb.append(" to ").append(sdf.format(endDate));
        return sb.toString();
    }

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

    public void checkIn(){
        this.status = Status.CHECKED_IN;
    }

    public Receipt checkOut(){
        status = Status.CHECKED_OUT;
        Bank bank = new Bank();
        Integer diffInDays = round((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
        Double total = bookedRoom.getRoomType().getPrice() * diffInDays;
        Receipt rec = bank.getReceipt(guest.getBankToken(), total, guest.getUsername(),
                bookedRoom.getRoomType().getQuality().toString());
        return rec;
    }

    public Receipt cancelRes(){
        Bank bank = new Bank();
        status = Status.CANCELED;
        //determine if cancellation is late
        Date cancelTime = new Date();
        Integer diffInDays = round((startDate.getTime() - cancelTime.getTime()) / (1000 * 60 * 60 * 24));
        boolean isLate = (diffInDays < 2);

        Receipt rec;
        if(isLate){
            Double total = bookedRoom.getRoomType().getPrice() * 0.8;
            rec = bank.getReceipt(guest.getBankToken(), total, guest.getUsername(),
                    "Late Cancellation of: " + bookedRoom.getRoomType().getQuality().toString());
        } else {
            rec = bank.getReceipt(guest.getBankToken(), 0.0, guest.getUsername(),"Reservation Canceled");
        }

        return rec;
    }
}