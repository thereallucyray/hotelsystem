package csi3471.group5;
import java.util.Date;
public class Reservation {
       Date startDate;
       Date endDate;
       boolean isCorporate;

       public enum Status{CREATED, CHECKED_IN, CHECKED_OUT, CANCELED};

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

    public boolean isPaid() {return !isActive();};

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
        final StringBuilder sb = new StringBuilder("Reservation{");
        sb.append("startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", isCorporate=").append(isCorporate);
        sb.append(", guest=").append(guest);
        sb.append('}');
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
}