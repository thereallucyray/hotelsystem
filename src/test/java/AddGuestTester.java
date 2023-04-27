import csi3471.group5.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddGuestTester {
    static  Hotel hotel = new Hotel("Teal");
    static SystemHandler system = SystemHandler.handler();

    String username1;
    String password1;
    String phoneNum1;

    String username2;
    String password2;
    String phoneNum2;

    @Before
    public void init(){
        //need to add guests to the hotel -> or have a test when there's none in it yet
        System.out.println("init");
        username1 = "newGuestName";
        password1 = "fancyPassword";
        phoneNum1 = "1234567890";

        username2 = "newGuestName";
        password2 = "funPassword";
        phoneNum2 = "0987654321";
    }

    @After
    public void delete(){
        System.out.println("delete");
        hotel.getGuestList().clear();
    }
    @Test
    public void TestAddGuestSuccess(){
        //registerGuest function adds guest when empty
        System.out.println("test8");

        //a new guest should be added to hotel's guest list
        assertEquals(0, hotel.getGuestList().size());

        hotel.registerGuest(username1, password1, phoneNum1);

        assertEquals(1, hotel.getGuestList().size());

    }
    @Test
    public void TestAddGuestFail(){
        //registerGuest function doesn't add when guest already exists
        System.out.println("test9");

        //a new guest should be added to hotel's guest list
        assertEquals(0, hotel.getGuestList().size());

        hotel.registerGuest(username1, password1, phoneNum1);

        assertEquals(1, hotel.getGuestList().size());

        hotel.registerGuest(username1, password1, phoneNum1);

        assertEquals(1, hotel.getGuestList().size());

    }
    @Test
    public void TestAddGuestFail2(){
        //registerGuest function doesn't add when username already exists
        System.out.println("test10");

        //a new guest should be added to hotel's guest list
        assertEquals(0, hotel.getGuestList().size());

        hotel.registerGuest(username1, password1, phoneNum1);

        assertEquals(1, hotel.getGuestList().size());

        hotel.registerGuest(username2, password2, phoneNum2);

        assertEquals(1, hotel.getGuestList().size());

    }

    @Test
    public void TestAddGuestSuccess2(){
        System.out.println("test11");

        //testing systemHandler, a new guest registers correctly
        boolean registerSuccess = system.registerGuest(username1, password1, phoneNum1);

        assertTrue(registerSuccess);

    }

    @Test
    public void TestAddGuestFail3(){
        System.out.println("test12");

        //testing systemHandler, a duplicate guest doesn't get registered
        system.registerGuest(username1, password1, phoneNum1);
        boolean registerSuccess = system.registerGuest(username2, password2, phoneNum2);

        assertFalse(registerSuccess);
    }
}
