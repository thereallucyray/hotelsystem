package csi3471.group5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * This class contains the functionalities and information needed to log in a user
 */
public class LoginUser {
    private String username;
    private String password;

    /**
     * Sets the username variable
     * @param username (String)
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the user's username
     * @return user's username (String)
     */
    public String getUsername() {
        return username;
    }

    /**
     * LoginUser class's constructor
     * @param username (String)
     * @param hashPassword (String)
     */
    public LoginUser(String username,String hashPassword) {
        this.username = username;
        this.password = hashPassword;
    }

    /**
     * Set's the variable password to the parameter's hash password
     * @param password (String)
     */
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    /**
     * Gets the user's password
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set's the hashed password
     * @param password (String)
     */
    public void setHashedPassword(String password) {
        this.password = password;
    }

    /**
     * Function that will determine if login was successful based on if the
     * parameter password's hashpassword is equal to the this password
     * @param username
     * @param password
     * @return boolean
     */
    public boolean login(String username,String password) {
        return username.equals(this.username) && hashPassword(password).equals(this.password);
    }

    /**
     * Formats passwords into its corresponding SHA-256 format
     * @param password (String)
     * @return String password after being hashed
     */
    private String hashPassword(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("I guess there's no such thing as SHA-256");
            throw new RuntimeException(e);
        }
        byte[] hashedPassword = md.digest(password.getBytes());
        return bytesToHex(hashedPassword);
    }

    private final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    /**
     * Converts an array of bytes into its corresponding hex format
     * @param bytes (byte[])
     * @return String password in the hex format
     */
    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
