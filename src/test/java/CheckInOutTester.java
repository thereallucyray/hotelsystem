import csi3471.group5.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.suite.api.IncludeClassNamePatterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests checking in/ checking out functionality
 */
@IncludeClassNamePatterns({"*"})
public class CheckInOutTester {
    private static Reservation res;
    private static String datePattern = "MM-dd-yyyy";
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    private static Guest g;
    private static RoomType rt;
    private static Room r;

    /**
     * Creates guests and reservations for testing
     */
    @BeforeEach
    void init() {
        try {
            Date start = dateFormatter.parse("04-20-2024");
            Date end = dateFormatter.parse("04-23-2024");

            g = new Guest("testGuest", "password", "1000");
            rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
            r = new Room(1, 1, rt);

            res = new Reservation(start, end, r, g);

            System.out.println(res.getStartDate().toString() + " " + res.getEndDate().toString());
            System.out.println(res.getGuest().getUsername() + " " + res.getGuest().getPassword());
            System.out.println(res.getBookedRoom().toString());
        } catch(ParseException pe){
            assertTrue(false);
            System.out.println(pe.getMessage());
        }
    }

    /**
     * Tests successful check in for guest
     * @throws ParseException
     */
    @Test
    public void TestCheckIn() throws ParseException {
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        res.checkIn();
        assertEquals(res.getStatus(), Reservation.Status.CHECKED_IN);
    }


    /**
     * Tests successful check out for guest
     * @throws ParseException
     */
    @Test
    public void TestCheckOut() throws ParseException {
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        res.checkOut();
        assertEquals(res.getStatus(), Reservation.Status.CHECKED_OUT);
        Integer diffInDays = round((res.getEndDate().getTime() - res.getStartDate().getTime()) / (1000 * 60 * 60 * 24));
        assertEquals(diffInDays, 3);
    }

    /**
     * Tests successful reservation cancellation for guest
     * @throws ParseException
     */
    @Test
    public void TestCancelNotLate() throws ParseException{
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        res.cancelRes();
        assertEquals(res.getStatus(), Reservation.Status.CANCELED);
        Date cancelTime = new Date();
        System.out.println(cancelTime.toString());
        Integer diffInDays = round((res.getStartDate().getTime() - cancelTime.getTime()) / (1000 * 60 * 60 * 24));
        System.out.println(diffInDays);
        assertTrue(diffInDays > 2);
    }

    /**
     * Tests successful late cancellation for guest
     * @throws ParseException
     */
    @Test
    public void TestCancelLate() throws ParseException{
        Date start = dateFormatter.parse("04-20-2023");
        Date end = dateFormatter.parse("04-23-2023");

        Reservation lateRes = new Reservation(start, end, r, g);

        lateRes.cancelRes();
        assertEquals(lateRes.getStatus(), Reservation.Status.CANCELED_LATE);
        Date cancelTime = dateFormatter.parse("04-20-2023");
        System.out.println(cancelTime.toString());
        Integer diffInDays = round((lateRes.getStartDate().getTime() - cancelTime.getTime()) / (1000 * 60 * 60 * 24));
        System.out.println(diffInDays);
        assertTrue(diffInDays < 2);
    }

    @Test
    /**
     * Tests successful cancellation within 2 days of reservation date
     * @throws ParseException
     */
    public void TestCancel2days() throws ParseException{
        Date start = dateFormatter.parse("04-20-2023");
        Date end = dateFormatter.parse("04-23-2023");

        Reservation notLateRes = new Reservation(start, end, r, g);

        notLateRes.cancelRes();
        assertEquals(notLateRes.getStatus(), Reservation.Status.CANCELED_LATE);
        Date cancelTime = dateFormatter.parse("04-18-2023");
        System.out.println(cancelTime.toString());
        Integer diffInDays = round((notLateRes.getStartDate().getTime() - cancelTime.getTime()) / (1000 * 60 * 60 * 24));
        System.out.println(diffInDays);
        assertTrue(diffInDays == 2);
    }

    /**
     * Tests at same time
     * @throws ParseException
     */
    @Test
    public void SameTime() throws ParseException {
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");
        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);
        rt.addRoom(r);

        res = new Reservation(start, end, r, g);
        r.addReservation(res);
        assertEquals(1,rt.getRoomList().size());
        assertNull(rt.getAvailableRoom(start, end));
    }

    /**
     * Tests checking-in rooms back-to-back
     * @throws ParseException
     */
    @Test
    public void Subsequent() throws ParseException {
        Date start1 = dateFormatter.parse("04-20-2024");
        Date end1 = dateFormatter.parse("04-23-2024");
        Date start2 = dateFormatter.parse("04-23-2024");
        Date end2 = dateFormatter.parse("04-26-2024");
        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);
        rt.addRoom(r);
        res = new Reservation(start1, end1, r, g);
        r.addReservation(res);
        assertEquals(1,rt.getRoomList().size());
        assertNotNull(rt.getAvailableRoom(start2, end2));
    }
    /**
     * Tests successful check in for guest for overlapping reservations
     * @throws ParseException
     */
    @Test
    public void Overlapping() throws ParseException {
        Date start1 = dateFormatter.parse("04-20-2024");
        Date end1 = dateFormatter.parse("04-23-2024");
        Date start2 = dateFormatter.parse("04-22-2024");
        Date end2 = dateFormatter.parse("04-26-2024");
        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);
        rt.addRoom(r);
        res = new Reservation(start1, end1, r, g);
        r.addReservation(res);
        assertEquals(1,rt.getRoomList().size());
        assertNull(rt.getAvailableRoom(start2, end2));
    }

    /**
     * Tear down after each test
     */
    @AfterEach
    public void tearDown() {}

}
