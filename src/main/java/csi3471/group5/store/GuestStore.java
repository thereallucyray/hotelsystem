package csi3471.group5.store;

import csi3471.group5.Guest;
import csi3471.group5.db.DBSerde;
import csi3471.group5.db.DBStore;

import java.util.ArrayList;

public class GuestStore extends DBStore<Guest,GuestStore> {

    public Guest login(String username, String password) {
        Guest g = null;
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
                list.add(Integer.toString(obj.getUserID()));
                list.add(obj.getUsername());
                list.add(obj.getPassword());
                list.add(Integer.toString(obj.getPhoneNumber()));
                return list;
            }

            @Override
            public Guest deserialize(String[] s) {
                return new Guest(Integer.parseInt(s[0]), s[1], s[2], Integer.parseInt(s[3]));
            }

            @Override
            public void resolveConnections(Guest obj) {
                new ReservationStore().resolve(obj.guestsReservations);
            }
        };
    }
}
