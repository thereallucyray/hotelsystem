package csi3471.group5.store;

import csi3471.group5.Employee;
import csi3471.group5.db.DBSerde;
import csi3471.group5.db.DBStore;

import java.util.ArrayList;

/**
 * A Store for Employees!
 */
public class EmployeeStore extends DBStore<Employee,EmployeeStore> {
    /**
     * Gos through all employees and checks if the username and password match.
     * @param username
     * @param password
     * @return The employee if he exists
     */
    public Employee login(String username, String password) {
        Employee e = null;
        new HotelStore().resolveConnections();
        for (Employee emp: data()) {
            if (emp.login(username, password)) {
                e = emp;
                break;
            }
        }
        return e;
    }

    @Override
    public String getFilename() {
        return "Employee";
    }

    @Override
    public DBSerde<Employee> getSerde() {
        return new DBSerde<Employee>() {
            @Override
            public ArrayList<String> serialize(Employee obj) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(obj.getUsername());
                list.add(obj.getPassword());
                list.add(Boolean.toString(obj.isAdmin()));
                return list;
            }

            @Override
            public Employee deserialize(String[] s) {
                Employee emp = new Employee(s[0], s[1], Boolean.parseBoolean(s[2]));
                new HotelStore().getByID(0).addEmployee(emp);
                return emp;
            }
        };
    }
}
