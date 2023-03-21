package csi3471.group5;
public class Clerk {
    private int userId;
    private String password;

    public Clerk(){
        userId = 0;
        password = "";
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
