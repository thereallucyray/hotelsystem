package csi3471.group5;
import java.util.Date;
public class Reservation {
       Date startDate = new Date();
       Date endDate = new Date();
       boolean isCorporate, isActive;
       public enum paymentStatus{NOT_PAID, PAYMENT_PROCESSING, PAYMENT_PROCESSED};

       paymentStatus currPaymentStatus;
       Room bookedRoom;
       Guest guest;
    public Reservation(Date start, Date end, Room room){
        isCorporate = false;
        isActive = true;
        currPaymentStatus = paymentStatus.NOT_PAID;
        startDate = start;
        endDate = end;
        bookedRoom = room;
    }
    public Reservation() {
        isCorporate = false;
        isActive = true;
        currPaymentStatus = paymentStatus.NOT_PAID;
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
    public void setActive(boolean b){
           isActive = b;
    }

    public void setCurrPaymentStatus(paymentStatus p){
        currPaymentStatus = p;
    }

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
        return isActive;
    }

    public Room getBookedRoom() {
        return bookedRoom;
    }

    public void setBookedRoom(Room bookedRoom) {
        this.bookedRoom = bookedRoom;
    }

    public paymentStatus getCurrPaymentStatus() {
        return currPaymentStatus;
    }

    public Guest getGuest() {
        System.out.println("Getting guest");
        System.out.println(this.hashCode());
        System.out.println(guest);
        return guest;
    }

    public void setGuest(Guest guest) {
        System.out.println("Setting guest");
        System.out.println(this.hashCode());
        System.out.println(guest);
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
        sb.append(", isActive=").append(isActive);
        sb.append(", currPaymentStatus=").append(currPaymentStatus);
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