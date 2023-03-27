package csi3471.group5;

import java.util.ArrayList;
import java.util.List;

public class Guest extends LoginUser{
    private int userID;
    private int phoneNumber;
    public List<Reservation> guestsReservations;

    public Guest(int id, String username, String password, int number){
        this.userID = id;
        setPassword(password);
        setUsername(username);
        this.phoneNumber = number;
        guestsReservations = new ArrayList<>();
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public int getUserID() {
        return this.userID;
    }
    public int getPhoneNumber() {
        return this.phoneNumber;
    }
    public void addReservation(Reservation r){
        guestsReservations.add(r);
    }
    public void removeReservation(Reservation r){
        guestsReservations.remove(r);
    }
}
