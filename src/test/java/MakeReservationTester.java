import csi3471.group5.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class MakeReservationTester {
    static Hotel hotel = new Hotel("Teal");
    static String datePattern = "MM-dd-yyyy";
    static SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    RoomType rt1, rt2, rt3, rt4;

    Guest g1, g2, g3;

    Room r1, r2, r3;

    Reservation res1;

    @BeforeEach
    public void init(){
        rt1 = new RoomType(false, 2, Hotel.qualityDesc.LUXURY, 500.00);
        rt2 = new RoomType(true, 3, Hotel.qualityDesc.ECONOMY, 250.00);
        rt3 = new RoomType(false, 1, Hotel.qualityDesc.SUITE, 550.00);
        rt4 = new RoomType(true, 1, Hotel.qualityDesc.SUITE, 500.00);

        hotel.addRoomType(rt1);
        hotel.addRoomType(rt2);
        hotel.addRoomType(rt3);

        r1 = new Room(112, 1, rt1);
        r2 = new Room(222, 2, rt2);
        r3 = new Room(333, 3, rt3);

        rt1.addRoom(r1);
        rt2.addRoom(r2);
        rt3.addRoom(r3);

        g1 = new Guest("hailey", "haileypassword", "8582138188");
        g2 = new Guest("john", "johnpassword", "7482236619");
        g3 = new Guest("rachel", "rachelpassword", "9981378826");

        hotel.addGuest(g1);
        hotel.addGuest(g2);
        hotel.addGuest(g3);

        Employee e1 = new Employee("blake", "blakepassword", false);
        Employee e2 = new Employee("emma", "emmapassword", true);
        Employee e3 = new Employee("jess", "jesspassword", false);

        hotel.addEmployee(e1);
        hotel.addEmployee(e2);
        hotel.addEmployee(e3);


    }

    @Test
    public void TestMakeReserveSuccess13() throws ParseException {
        System.out.println('\n' + "TESTING Make Reservation Success #1");

        Date start = dateFormatter.parse("04-20-2027");
        Date end = dateFormatter.parse("04-23-2027");

        boolean reservedRoom = hotel.reserveRoom(rt1, start, end, g1);
        assertTrue(reservedRoom);
        //res1 = new Reservation(start,end,r1,g1);
        //res1.setStatus(Reservation.Status.CHECKED_IN);
        System.out.println("Room Reserved.");

    }

    @Test
    public void TestMakeReserveSuccess14() throws ParseException {
        System.out.println('\n' + "TESTING Make Reservation Success #2");

        Date start = dateFormatter.parse("04-29-2027");
        Date end = dateFormatter.parse("04-29-2027");

        boolean reservedRoom = hotel.reserveRoom(rt3, start, end, g3);
        assertTrue(reservedRoom);
        System.out.println("Room Reserved.");

    }

    @Test
    public void TestReserveFailureRT15() throws ParseException {
        System.out.println('\n' + "TESTING Make Reservation Failure: No Room with room type.");
        try{

            Date start = dateFormatter.parse("04-24-2027");
            Date end = dateFormatter.parse("04-25-2027");

            boolean reservedRoom = hotel.reserveRoom(rt4, start, end, g1);
            assertFalse(reservedRoom);
            System.out.println("Room with roomtype is unavailable.");
        }catch (AssertionError e){
            System.out.println("ERROR");
        }

    }

    @Test
    public void TestReserveFailureRR16() throws ParseException {
        System.out.println('\n' + "TESTING Make Reservation Failure: Room already reserved.");
        try{
            Date start = dateFormatter.parse("04-21-2027");
            Date end = dateFormatter.parse("04-22-2027");

            boolean reservedRoom = hotel.reserveRoom(rt1, start, end, g2);
            reservedRoom = hotel.reserveRoom(rt1, start, end, g2);
            assertFalse(reservedRoom);
            System.out.println("ERROR : room is already reserved.");
        }catch (AssertionError e){
            System.out.println("ERROR");
        }
    }
}
