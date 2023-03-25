package csi3471.group5;

import java.util.ArrayList;
import java.util.List;

public class Guest {
    private int userID;
    private String password;
    private int phoneNumber;
    private List<Reservation> guestsReservations;


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
    public void setPassword(String password) {
        this.password = password;
    }
    public void setGuestsReservations(List<Reservation> guestsReservations) {
        this.guestsReservations = guestsReservations;
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
    public List<Reservation> getGuestsReservations() {
        return guestsReservations;
    }
}
