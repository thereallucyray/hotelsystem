import csi3471.group5.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class LoginTester {
    static Hotel hotel = new Hotel("Teal");
    static SystemHandler systemHandler = SystemHandler.handler();

    Guest g1, g2, g3;

    Employee e1, e2, e3;

    @BeforeEach
    public void init(){

        g1 = new Guest("hailey", "haileypassword", "8582138188");
        g2 = new Guest("john", "johnpassword", "7482236619");
        g3 = new Guest("rachel", "rachelpassword", "9981378826");

        hotel.addGuest(g1);
        hotel.addGuest(g2);
        hotel.addGuest(g3);

        e1 = new Employee("blake", "blakepassword", false);
        e2 = new Employee("emma", "emmapassword", true);
        e3 = new Employee("jess", "jesspassword", false);

        hotel.addEmployee(e1);
        hotel.addEmployee(e2);
        hotel.addEmployee(e3);


    }

    @Test
    public void TestLoginGuestSuccess1() throws AssertionError {
        System.out.println('\n' + "TESTING Log in as Guest Success:");
        try{
            boolean loggedIn = systemHandler.login("uberguest", "password", false);
            assertEquals(true, loggedIn);
            System.out.println("Guest is logged in.");
        }catch (AssertionError e){
            System.out.println("ERROR : guest is NOT logged in.");
        }
    }

    @Test
    public void TestLoginGuestFailure2() throws AssertionError {
        System.out.println('\n' + "TESTING Log in as Guest Failure: employee logging in as a guest.");
        try{
            boolean loggedIn = systemHandler.login("ciara", "password", false);
            assertEquals(true, loggedIn);
            System.out.println("Guest is logged in.");
        }catch (AssertionError e){
            System.out.println("ERROR : guest is NOT logged in.");
        }
    }

    @Test
    public void TestLoginGuestFailure3() throws AssertionError {
        System.out.println('\n' + "TESTING Log in as Guest Failure: unknown login entered.");
        try{
            boolean loggedIn = systemHandler.login("hannah", "hpassword", false);
            assertEquals(true, loggedIn);
            System.out.println("Guest is logged in.");
        }catch (AssertionError e){
            System.out.println("ERROR : guest is NOT logged in.");
        }
    }

    @Test
    public void TestLoginEmployeeSuccess4() throws AssertionError {
        System.out.println('\n' + "TESTING Log in as Employee Success:");
        try{
            boolean loggedIn = systemHandler.login("ciara", "password", true);
            assertEquals(true, loggedIn);
            System.out.println("Employee is logged in.");
        }catch (AssertionError e){
            System.out.println("ERROR : employee is NOT logged in.");
        }
    }

    @Test
    public void TestLoginEmployeeSuccess5() throws AssertionError {
        System.out.println('\n' + "TESTING Log in as Employee Success:");
        try{
            boolean loggedIn = systemHandler.login("lucy", "password", true);
            assertEquals(true, loggedIn);
            System.out.println("Employee is logged in.");
        }catch (AssertionError e){
            System.out.println("ERROR : employee is NOT logged in.");
        }
    }

    @Test
    public void TestLoginEmployeeFailure6() throws AssertionError {
        System.out.println('\n' + "TESTING Log in as Employee Failure: unknown employee login.");
        try{
            boolean loggedIn = systemHandler.login("Matthew", "Mpassword", true);
            assertEquals(true, loggedIn);
            System.out.println("Employee is logged in.");
        }catch (AssertionError e){
            System.out.println("ERROR : employee is NOT logged in.");
        }
    }

    @Test
    public void TestLoginEmployeeFailure7() throws AssertionError {
        System.out.println('\n' + "TESTING Log in as Employee Failure: guest trying to log in as employee.");
        try{
            boolean loggedIn = systemHandler.login("uberguest", "password", true);
            assertEquals(true, loggedIn);
            System.out.println("Employee is logged in.");
        }catch (AssertionError e){
            System.out.println("ERROR : employee is NOT logged in.");
        }
    }

}
