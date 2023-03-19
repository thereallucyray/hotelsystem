package csi3471.group5;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    public enum qualityDesc {SUITE, ECONOMY, LUXURY};
    private String hotelName;
    private List<Room> roomList;
    Hotel(String name, int floors, int roomsPerFloor) {
        hotelName = name;
        roomList = new ArrayList<Room>();
        for (int i = 0; i < floors; i++) {
            for (int j = 0; j < roomsPerFloor; j++) {
                Room r = new Room();
                r.setRoomNumber(i*100+j);
                r.setRoomFloor(i);
                roomList.add(r);
            }
        }
    }
}
