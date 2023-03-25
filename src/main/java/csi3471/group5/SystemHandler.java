package csi3471.group5;

import csi3471.group5.store.RoomTypeStore;

import java.util.Date;

public class SystemHandler {
    private Hotel hotel;

    private static SystemHandler instance;

    public static SystemHandler handler(){
        if(instance == null){
            instance = new SystemHandler();
        }
        return instance;
    }

    public void init() {}

    private SystemHandler(){
        hotel = new Hotel("Teal");
    }

    public boolean reserveRoom(Integer roomType, Date start, Date end, int id){
        RoomType rt = new RoomTypeStore().query().getIndex(roomType);
        boolean success = hotel.reserveRoom(rt, start, end, id);
        //boolean success = true;
        return success;
    }
}
