package csi3471.group5;

public class Employee extends LoginUser {
    private boolean isAdmin;
    public Employee(String username, String password, boolean isAdmin){
        super(username, password);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
