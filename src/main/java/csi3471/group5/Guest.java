package csi3471.group5;

import java.util.ArrayList;
import java.util.List;

public class Guest {
    private int userID;
    private String password;
    private int phoneNumber;
    public List<Reservation> guestsReservations;

    Guest(int id, String p, int n){
        this.userID = id;
        this.password = p;
        this.phoneNumber = n;
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
    public String getPassword() {
        return this.password;
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
