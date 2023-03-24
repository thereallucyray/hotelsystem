package csi3471.group5;

import csi3471.group5.db.Database;
import csi3471.group5.store.RoomStore;
import csi3471.group5.store.RoomTypeStore;

import java.util.ArrayList;

public class Hotel {
    public enum qualityDesc {SUITE, ECONOMY, LUXURY};
    private String hotelName;
    private ArrayList<RoomType> roomTypes;
    Hotel(String name) {
        RoomTypeStore roomTypeStore = new RoomTypeStore();

        roomTypes = roomTypeStore.query().get();
        for(int i = 0; i < roomTypes.size(); i++) {
            roomTypes.get(i).loadRooms(i);
        }
        roomTypes.forEach(System.out::println);

        hotelName = name;
    }
}
