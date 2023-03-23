package csi3471.group5;

import csi3471.group5.db.Database;
import csi3471.group5.store.RoomStore;
import csi3471.group5.store.RoomTypeStore;

import java.util.ArrayList;

public class Hotel {
    public enum qualityDesc {SUITE, ECONOMY, LUXURY};
    private String hotelName;
    private ArrayList<RoomType> roomTypes;
    Hotel(String name, int floors, int roomsPerFloor) {
        RoomStore roomStore = new RoomStore();
        RoomTypeStore roomTypeStore = new RoomTypeStore();

        ArrayList<Room> roomList = roomStore.getList();
        roomTypes = roomTypeStore.getList();

        roomTypes.forEach(System.out::println);

        hotelName = name;
    }
}
