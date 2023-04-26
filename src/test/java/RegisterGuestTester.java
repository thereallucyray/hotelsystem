import csi3471.group5.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class RegisterGuestTester {
    static Hotel hotel = new Hotel("Teal");

    Guest g1, g2, g3;

    @BeforeEach
    public void init(){

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
    public void TestRegisterGuestSuccessful1() throws ParseException {
        System.out.println('\n' + "TESTING Register New Guest Successful:");
        try{
            Guest newGuest;
            String user = "Kiley";
            String pass = "kileypassword";
            String number = "8582138888";
            Guest expectedGuest = new Guest(user,pass,number);
            newGuest = hotel.registerGuest(user, pass, number);
            assertEquals(expectedGuest.getUsername(),newGuest.getUsername());
            System.out.println("Guest " + user + " has been registered successfully.");
        }catch (AssertionError e){
            System.out.println("ERROR");
        }
    }

    @Test
    public void TestRegisterGuestSuccessful2() throws ParseException {
        System.out.println('\n' + "TESTING Register New Guest Successful:");
        try{
            Guest newGuest;
            String user = "Hailey";
            String pass = "kileypassword";
            String number = "8582138822";
            Guest expectedGuest = new Guest(user,pass,number);
            newGuest = hotel.registerGuest(user, pass, number);
            assertEquals(expectedGuest.getPassword(),newGuest.getPassword());
            System.out.println("Guest " + user + " has been registered successfully.");
        }catch (AssertionError e){
            System.out.println("ERROR");
        }
    }


    @Test
    public void TestRegisterGuestSuccessful3() throws ParseException {
        System.out.println('\n' + "TESTING Register New Guest Successful:");
        try{
            Guest newGuest;
            String user = "James";
            String pass = "jamespassword";
            String number = "6197732145";
            Guest expectedGuest = new Guest(user,pass,number);
            newGuest = hotel.registerGuest(user, pass, number);
            assertEquals(expectedGuest.getPhoneNumber(),newGuest.getPhoneNumber());
            System.out.println("Guest " + user + " has been registered successfully.");
        }catch (AssertionError e){
            System.out.println("ERROR");
        }
    }


    @Test
    public void TestRegisterGuestFailure4() throws ParseException {
        System.out.println('\n' + "TESTING Register New Guest " +
                "Failure: guest already registered.");
        try{
            Guest newGuest;
            String user = "john";
            String pass = "johnpassword";
            String number = "7482236619";
            Guest expectedGuest = new Guest(user,pass,number);
            newGuest = hotel.registerGuest(user, pass, number);
            assertEquals(expectedGuest.getPhoneNumber(),newGuest.getPhoneNumber());
            System.out.println("Guest " + user + " has been registered successfully.");
        }catch (NullPointerException e){
            System.out.println("ERROR : guest is already registered.");
        }
    }

    @Test
    public void TestRegisterGuestFailure5() throws ParseException {
        System.out.println('\n' + "TESTING Register New Guest " +
                "Failure: guest already registered.");
        try{
            Guest newGuest;
            String user = "rachel";
            String pass = "rachelpassword";
            String number = "9981378826";
            Guest expectedGuest = new Guest(user,pass,number);
            newGuest = hotel.registerGuest(user, pass, number);
            assertEquals(expectedGuest.getPhoneNumber(),newGuest.getPhoneNumber());
            System.out.println("Guest " + user + " has been registered successfully.");
        }catch (NullPointerException e){
            System.out.println("ERROR : guest is already registered.");
        }
    }
}
