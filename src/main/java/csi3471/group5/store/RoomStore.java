package csi3471.group5.store;
import csi3471.group5.Room;
import csi3471.group5.RoomType;
import csi3471.group5.db.DBSerde;
import csi3471.group5.db.DBStore;

import java.util.ArrayList;

public class RoomStore extends DBStore<Room,RoomStore> {
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
                list.add(Integer.toString(new RoomTypeStore().resolve(obj.getRoomType())));
                return list;
            }
            @Override
            public Room deserialize(String[] s) {
                Room r = new Room();
                r.setRoomNumber(Integer.parseInt(s[0]));
                r.setRoomFloor(Integer.parseInt(s[1]));
                RoomType rtype = new RoomTypeStore().getByID(Integer.parseInt(s[2]));
                r.setRoomType(rtype);
                rtype.addRoom(r);
                return r;
            }
        };
    }
}
