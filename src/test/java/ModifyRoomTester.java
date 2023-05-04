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
 * Tests Modify Room functionality
 */
@IncludeClassNamePatterns({"*"})
public class ModifyRoomTester {

    private static Reservation res;
    private static String datePattern = "MM-dd-yyyy";
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @BeforeEach
    void init() {}

    /**
     * Successfully changes room number
     */
    @Test
    public void TestModifyRoomNumber(){
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.LUXURY, 100.0);
        Room r = new Room(1,1,rt) ;

        r.setRoomNumber(2);
        assertEquals(2, r.getRoomNumber());
    }

    /**
     * Successfully changes room type
     */
    @Test
    public void TestModifyRoomTypeRoom(){
        RoomType rt = new RoomType(true, 1, Hotel.qualityDesc.LUXURY, 100.0);
        Room r = new Room(1,1,rt) ;

        RoomType newRt = new RoomType(false, 2, Hotel.qualityDesc.ECONOMY, 140.0);
        r.setRoomType(newRt);
        assertEquals(newRt, r.getRoomType());
    }


}
