package csi3471.group5;

/**
 * This class represents the functionalities of an Employee in the hotel
 */
public class Employee extends LoginUser {
    private boolean isAdmin;

    /**
     * Constructor for the Employee class
     * @param username
     * @param password
     * @param isAdmin
     */
    public Employee(String username, String password, boolean isAdmin){
        super(username, password);
        this.isAdmin = isAdmin;
    }

    /**
     * Checks to see whether this Employee is an admin (getter)
     * @return boolean
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Sets the admin variable based on whether this Employee is an admin
     * @param admin
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
