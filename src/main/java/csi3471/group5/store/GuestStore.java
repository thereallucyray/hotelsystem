package csi3471.group5.store;

import csi3471.group5.Guest;
import csi3471.group5.db.DBSerde;
import csi3471.group5.db.DBStore;

import java.util.ArrayList;

/**
 * A Store for Guests!
 */
public class GuestStore extends DBStore<Guest,GuestStore> {

    /**
     * Gos through all employees and checks if the username and password match.
     * @param username
     * @param password
     * @return The employee if he exists
     */
    public Guest login(String username, String password) {
        Guest g = null;
        new HotelStore().resolveConnections();
        for (Guest guest: data()) {
            if (guest.login(username, password)) {
                g = guest;
                break;
            }
        }
        return g;
    }

    @Override
    public String getFilename() {
        return "Guest";
    }

    @Override
    public DBSerde<Guest> getSerde() {
        return new DBSerde<Guest>() {
            @Override
            public ArrayList<String> serialize(Guest obj) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(obj.getUsername());
                list.add(obj.getPassword());
                list.add(obj.getPhoneNumber());
                String token = obj.getBankToken();
                if(token == null) {
                    list.add("null");
                } else {
                    list.add(obj.getBankToken());
                }
                return list;
            }

            @Override
            public Guest deserialize(String[] s) {
                Guest g = new Guest(s[0], s[1], s[2]);
                if(!s[3].equals("null")) {
                    g.setBankToken(s[3]);
                }
                new HotelStore().getByID(0).addGuest(g);
                return g;
            }

            @Override
            public void resolveConnections(Guest obj) {
                new ReservationStore().resolve(obj.guestsReservations);
            }
        };
    }
}
