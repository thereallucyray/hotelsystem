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
        new RoomStore().init();
        new ReservationStore().init();
        new GuestStore().init();

        roomTypes = new RoomTypeStore().query().get();
        roomTypes.forEach(System.out::println);

        RoomType rt = new RoomTypeStore().query().getOne();
        Room newroom = new Room(1000,1,rt);
        Reservation newres = new Reservation(new Date(), new Date(), newroom);
        newres.setGuest(new Guest(0, "John", "Doe", 469900920));
        newroom.addReservation(newres);
        rt.addRoom(newroom);

        DBStore.saveAll();
        hotelName = name;

        //initializing guestList
        guestList = new ArrayList<>();
    }

    public boolean reserveRoom(RoomType rt, Date start, Date end, int guestId){
        //determine if there is an available room of type rt
        //you can use the getAvailableRoom() function

        //if null is returned from getAvailableRoom(), no room was available, and false
        //should be returned from this method

        //if a room is returned from getAvailableRoom(), create a reservation, and associate it
        //with the guest and the room
        //true should be returned from this method.

        boolean reserveSuccessful = false;

        for(int i = 0; i < roomTypes.size(); i++){

            if(roomTypes.get(i) == rt){

                //if there's a room to reserve
                if( (roomTypes.get(i).getAvailableRoom(start, end)) != null){
                    reserveSuccessful = true;

                    //create new reservation
                    Reservation newReservation = new Reservation(start, end,
                            (roomTypes.get(i).getAvailableRoom(start, end))); //add isActive?

                    //associate new reservation with room
                    (roomTypes.get(i).getAvailableRoom(start, end)).addReservation(newReservation);

                    //associate reservation with guest
                    for(int j = 0; j < guestList.size(); j++){
                        if(guestList.get(j).getUserID() == guestId){
                            guestList.get(j).addReservation(newReservation);
                        }
                    }
                }
            }
        }

        return reserveSuccessful;
    }

    public void registerGuest(int id, String u, String p, int number){
        Guest newGuest = new Guest(id, u, p, number);

        guestList.add(newGuest);
    }
}
