package csi3471.group5;

import csi3471.group5.db.DBStore;
import csi3471.group5.db.Database;
import csi3471.group5.store.GuestStore;
import csi3471.group5.store.ReservationStore;
import csi3471.group5.store.RoomStore;
import csi3471.group5.store.RoomTypeStore;

import java.util.ArrayList;
import java.util.Date;

public class Hotel {
    public enum qualityDesc {SUITE, ECONOMY, LUXURY}
    private String hotelName;
    private ArrayList<RoomType> roomTypes;
    private ArrayList<Guest> guestList;
    Hotel(String name) {
