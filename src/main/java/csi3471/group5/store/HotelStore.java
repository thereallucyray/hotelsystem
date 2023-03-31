package csi3471.group5.store;

import csi3471.group5.Hotel;
import csi3471.group5.db.DBSerde;
import csi3471.group5.db.DBStore;

import java.util.ArrayList;

public class HotelStore extends DBStore<Hotel, HotelStore> {
    @Override
    public String getFilename() {
        return "Hotel";
    }

    @Override
    public DBSerde<Hotel> getSerde() {
        return new DBSerde<Hotel>() {
            @Override
            public ArrayList<String> serialize(Hotel obj) {
                ArrayList<String> list = new ArrayList<>();
                list.add(obj.getHotelName());
                return list;
            }

            @Override
            public Hotel deserialize(String[] s) {
                Hotel h = new Hotel(s[0]);
                return h;
            }

            @Override
            public void resolveConnections(Hotel obj) {
                new RoomTypeStore().resolve(obj.getRoomTypes());
                new GuestStore().resolve(obj.getGuestList());
                new EmployeeStore().resolve(obj.getEmployeeList());
            }
        };
    }
}
