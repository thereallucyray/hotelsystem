package csi3471.group5.store;
import csi3471.group5.Room;
import csi3471.group5.RoomType;
import csi3471.group5.db.DBSerde;
import csi3471.group5.db.DBStore;

import java.util.ArrayList;

/**
 * A Store for Rooms!
 */
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
                list.add(Integer.toString(new RoomTypeStore().getID(obj.getRoomType())));
                return list;
            }
            @Override
            public Room deserialize(String[] s) {
                RoomType rtype = new RoomTypeStore().getByID(Integer.parseInt(s[2]));
                Room r = new Room(Integer.parseInt(s[0]), Integer.parseInt(s[1]), rtype);
                rtype.addRoom(r);
                return r;
            }
            @Override
            public void resolveConnections(Room obj) {
                System.out.println("Resolving connections for RoomType");
                new RoomTypeStore().resolve(obj.getRoomType());
                System.out.println("Resolving connections for Reservation");
                new ReservationStore().resolve(obj.getReservationList());
            }
        };
    }
}
