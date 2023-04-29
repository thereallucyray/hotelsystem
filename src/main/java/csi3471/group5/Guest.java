package csi3471.group5;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the guests in the hotel
 */
public class Guest extends LoginUser{
    private String phoneNumber;
    public List<Reservation> guestsReservations;
    String bankToken; //this is how the guest card info is stored
                      //BRENDON: this can be null
                      //LUCY: this should be updated when a reservation is made

    /**
     * Constructor for the Guest class
     * @param username
     * @param password
     * @param number
     */
    public Guest(String username, String password, String number){
        super(username, password);
        this.phoneNumber = number;
        guestsReservations = new ArrayList<>();
    }

    /**
     * Sets the Guest's phone number variable
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets this Guest's phone number
     * @return String phoneNumber of this Guest
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Adds a specific reservation to this Guest's reservation list
     * @param r (a reservation)
     */
    public void addReservation(Reservation r){
        guestsReservations.add(r);
    }

    /**
     * Removes a specific reservation to this Guest's reservation list
     * @param r (a reservation)
     */
    public void removeReservation(Reservation r){
        guestsReservations.remove(r);
    }

    /**
     * Gets the Guest's stored card information
     * @return String variable : the Guest's banktoken (their stored card information)
     */

    public String getBankToken() {
        return bankToken;
    }

    /**
     * Sets the Guest's bank token variable
     * @param bankToken (String variable of their stored card information)
     */
    public void setBankToken(String bankToken) {
        this.bankToken = bankToken;
    }
}
