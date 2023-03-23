package csi3471.group5.store;
import csi3471.group5.Room;
import csi3471.group5.db.DBSerde;
import csi3471.group5.db.DBStore;

import java.util.ArrayList;
import java.util.List;

public class RoomStore extends DBStore<Room> {
    @Override
    public String getFilename() {
        return "Room";
    }
    @Override
    public DBSerde<Room> getSerde() {
        return new DBSerde<Room>() {
            @Override
            public ArrayList<String> serialize(Room obj) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(Integer.toString(obj.getRoomNumber()));
                list.add(Integer.toString(obj.getRoomFloor()));
                list.add(Integer.toString(obj.getRoomtypeindex()));
                return list;
            }
            @Override
            public Room deserialize(String[] s) {
                Room r = new Room();
                r.setRoomNumber(Integer.parseInt(s[0]));
                r.setRoomFloor(Integer.parseInt(s[1]));
                r.setRoomtypeindex(Integer.parseInt(s[2]));
                return r;
            }
        };
    }
}
