import csi3471.group5.*;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.suite.api.IncludeClassNamePatterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.*;

@IncludeClassNamePatterns({"*"})
public class CheckInOutTester {
    private static Reservation res;
    private static String datePattern = "MM-dd-yyyy";
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @BeforeEach
    void init() {
        try {
            Date start = dateFormatter.parse("04-20-2024");
            Date end = dateFormatter.parse("04-23-2024");

            Guest g = new Guest("testGuest", "password", "1000");
            RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
            Room r = new Room(1, 1, rt);

            res = new Reservation(start, end, r, g);
            System.out.println("I made it here");
            System.out.println(res.getStartDate().toString() + " " + res.getEndDate().toString());
            System.out.println(res.getGuest().getUsername() + " " + res.getGuest().getPassword());
            System.out.println(res.getBookedRoom().toString());
        } catch(ParseException pe){
            assertTrue(false);
            System.out.println(pe.getMessage());
        }
    }

    @Test
    public void TestCheckIn() throws ParseException {
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);

        res = new Reservation(start, end, r, g);

        res.checkIn();
        assertEquals(res.getStatus(), Reservation.Status.CHECKED_IN);
    }

    @Test
    public void TestCheckOut() throws ParseException {
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);

        res = new Reservation(start, end, r, g);

        res.checkOut();
        assertEquals(res.getStatus(), Reservation.Status.CHECKED_OUT);
        Integer diffInDays = round((res.getEndDate().getTime() - res.getStartDate().getTime()) / (1000 * 60 * 60 * 24));
        assertEquals(diffInDays, 3);
    }

    @Test
    public void TestCancelNotLate() throws ParseException{
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);

        res = new Reservation(start, end, r, g);

        res.checkOut();
        assertEquals(res.getStatus(), Reservation.Status.CHECKED_OUT);
        Date cancelTime = new Date();
        System.out.println(cancelTime.toString());
        Integer diffInDays = round((res.getStartDate().getTime() - cancelTime.getTime()) / (1000 * 60 * 60 * 24));
        System.out.println(diffInDays);
        assertTrue(diffInDays > 2);
    }

    @Test
    public void TestCancelLate() throws ParseException{
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);

        res = new Reservation(start, end, r, g);

        res.checkOut();
        assertEquals(res.getStatus(), Reservation.Status.CHECKED_OUT);
        Date cancelTime = dateFormatter.parse("04-20-2024");
        System.out.println(cancelTime.toString());
        Integer diffInDays = round((res.getStartDate().getTime() - cancelTime.getTime()) / (1000 * 60 * 60 * 24));
        System.out.println(diffInDays);
        assertTrue(diffInDays < 2);
    }

    @Test
    public void TestCancel2days() throws ParseException{
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);

        res = new Reservation(start, end, r, g);

        res.checkOut();
        assertEquals(res.getStatus(), Reservation.Status.CHECKED_OUT);
        Date cancelTime = dateFormatter.parse("04-18-2024");
        System.out.println(cancelTime.toString());
        Integer diffInDays = round((res.getStartDate().getTime() - cancelTime.getTime()) / (1000 * 60 * 60 * 24));
        System.out.println(diffInDays);
        assertFalse(diffInDays < 2);
    }

    @AfterEach
    public void tearDown() {}

}
