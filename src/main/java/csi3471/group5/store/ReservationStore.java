package csi3471.group5.store;

import csi3471.group5.Guest;
import csi3471.group5.Reservation;
import csi3471.group5.Room;
import csi3471.group5.db.DBSerde;
import csi3471.group5.db.DBStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReservationStore extends DBStore<Reservation,ReservationStore> {
    @Override
    public String getFilename() {
        return "Reservation";
    }

    @Override
    public DBSerde<Reservation> getSerde() {
        return new DBSerde<Reservation>() {
            @Override
            public ArrayList<String> serialize(Reservation obj) {
                ArrayList<String> list = new ArrayList<String>();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                list.add(sdf.format(obj.getStartDate()));
                list.add(sdf.format(obj.getEndDate()));
                list.add(Boolean.toString(obj.isCorporate()));
                list.add(Boolean.toString(obj.isActive()));
                list.add(obj.getCurrPaymentStatus().toString());
                list.add(Integer.toString(new RoomStore().getID(obj.getBookedRoom())));
                list.add(Integer.toString(new GuestStore().getID(obj.getGuest())));
                return list;
            }
            @Override
            public Reservation deserialize(String[] s) {
                Reservation r = new Reservation();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                try {
                    r.setStartDate(sdf.parse(s[0]));
                    r.setEndDate(sdf.parse(s[1]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                r.setCorporate(Boolean.parseBoolean(s[2]));
                r.setActive(Boolean.parseBoolean(s[3]));
                r.setCurrPaymentStatus(Reservation.paymentStatus.valueOf(s[4]));
                RoomStore rs = new RoomStore();
                Room room = rs.getByID(Integer.parseInt(s[5]));
                r.setBookedRoom(room);
                room.addReservation(r);
                Guest g = new GuestStore().getByID(Integer.parseInt(s[6]));
                r.setGuest(g);
                g.addReservation(r);
                return r;
            }
            @Override
            public void resolveConnections(Reservation obj) {
                new RoomStore().resolve(obj.getBookedRoom());
                new GuestStore().resolve(obj.getGuest());
            }
        };
    }
}
