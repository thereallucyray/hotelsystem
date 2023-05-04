import csi3471.group5.*;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.suite.api.IncludeClassNamePatterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests modify reservation
 */
@IncludeClassNamePatterns({"*"})
public class ModifyReservationTester {
    private static Reservation res;
    private static String datePattern = "MM-dd-yyyy";
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Initializes a test hotel system for testing
     */
    @BeforeEach
    void init() {
        try {
            Date start = dateFormatter.parse("04-20-2024");
            Date end = dateFormatter.parse("04-23-2024");

            Guest g = new Guest("testGuest", "password", "1000");
            RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
            Room r = new Room(1, 1, rt);

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
     * Tests a successful modify reservation
     * @throws ParseException
     */
    @Test
    public void TestModifyRoomType() throws ParseException {
        Date start = dateFormatter.parse("04-23-2024");
        Date end = dateFormatter.parse("04-26-2024");

        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 2, Hotel.qualityDesc.LUXURY, 100.25);
        Room r = new Room(1, 1, rt);

        RoomType newRt = new RoomType(false,3, Hotel.qualityDesc.ECONOMY,75.25);
        Room newR = new Room(2, 2, newRt);
        newRt.addRoom(newR);

        res = new Reservation(start, end, r, g);
        res.modifyRoomType(newRt);
        //System.out.println(res.getBookedRoom().getRoomType());
        Assertions.assertEquals(75.25,
                res.getBookedRoom().getRoomType().getPrice());
        Assertions.assertEquals(Hotel.qualityDesc.ECONOMY,
                res.getBookedRoom().getRoomType().getQuality());
        Assertions.assertEquals(false,
                res.getBookedRoom().getRoomType().isSmoking());
    }

    /**
     * Tests when you modify end date
     * @throws ParseException
     */
    @Test
    public void TestModifyEndDate() throws ParseException {
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        Date newEnd = dateFormatter.parse("04-24-2024");
        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);
        rt.addRoom(r);
        res = new Reservation(start, end, r, g);
        res.modifyDateRange(start, newEnd);

        Assertions.assertEquals("04-24-2024", dateFormatter.format(res.getEndDate()));
    }

    /**
     * Tests a successfully modified start date
     * @throws ParseException
     */
    @Test
    public void TestModifyStartDate() throws ParseException {
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        Date newStart = dateFormatter.parse("04-19-2024");
        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);
        rt.addRoom(r);
        res = new Reservation(start, end, r, g);
        res.modifyDateRange(newStart, end);

        Assertions.assertEquals("04-19-2024", dateFormatter.format(res.getStartDate()));
    }

    /**
     * Tests a successful modification of start and end dates
     * @throws ParseException
     */
    @Test
    public void TestModifyDateRange() throws ParseException {
        Date start = dateFormatter.parse("04-20-2024");
        Date end = dateFormatter.parse("04-23-2024");

        Date newStart = dateFormatter.parse("04-19-2024");
        Date newEnd = dateFormatter.parse("04-24-2024");
        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.ECONOMY, 100.0);
        Room r = new Room(1, 1, rt);
        rt.addRoom(r);
        res = new Reservation(start, end, r, g);
        res.modifyDateRange(newStart, newEnd);

        Assertions.assertEquals("04-19-2024", dateFormatter.format(res.getStartDate()));
        Assertions.assertEquals("04-24-2024", dateFormatter.format(res.getEndDate()));
    }

    /**
     * Tests a case where start date > end date
     * @throws ParseException
     */
    @Test
    public void TestModifyRoomTypeFail() throws ParseException{
        Date start = dateFormatter.parse("04-23-2024");
        Date end = dateFormatter.parse("04-26-2024");

        Guest g = new Guest("testGuest", "password", "1000");
        RoomType rt = new RoomType(true, 2, Hotel.qualityDesc.LUXURY, 100.25);
        Room r = new Room(1, 1, rt);

        RoomType newRt = new RoomType(false,3, Hotel.qualityDesc.ECONOMY,75.25);
        Room newR = new Room(2, 2, newRt);

        res = new Reservation(start, end, r, g);
        res.modifyRoomType(newRt);
        Assertions.assertEquals(r, res.getBookedRoom());
    }

    /**
     * tears down after tests
     */
    @AfterEach
    public void tearDown() {}

}
