package csi3471.group5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class LoginUser {
    private String username;
    private String password;
    private String salt;

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public LoginUser(String username,String hashPassword) {
        this.username = username;
        this.password = hashPassword;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setHashedPassword(String password) {
        this.password = password;
    }

    public boolean login(String username,String password) {
        return username.equals(this.username) && hashPassword(password).equals(this.password);
    }

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
