package csi3471.group5;

import java.util.ArrayList;
import java.util.List;

public class Guest extends LoginUser{
    private String phoneNumber;
    public List<Reservation> guestsReservations;
    String bankToken; //this is how the guest card info is stored
                      //BRENDON: this can be null
                      //LUCY: this should be updated when a reservation is made

    public Guest(String username, String password, String number){
        super(username, password);
        this.phoneNumber = number;
        guestsReservations = new ArrayList<>();
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void addReservation(Reservation r){
        guestsReservations.add(r);
    }
    public void removeReservation(Reservation r){
        guestsReservations.remove(r);
    }

    public String getBankToken() {
        return bankToken;
    }

    public void setBankToken(String bankToken) {
        this.bankToken = bankToken;
    }
}
