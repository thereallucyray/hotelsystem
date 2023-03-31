package csi3471.group5;

public class LoginUser {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginUser(String username,String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String username,String password) {
        return username.equals(this.username) && password.equals(this.password);
    }
}
