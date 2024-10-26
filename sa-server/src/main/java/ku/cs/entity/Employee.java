package ku.cs.entity;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class Employee {
    String employee_ID;
    String employee_name;
    String employee_address;
    String employee_password;
    String employee_phone_number;
    String employee_username;
    String role;

    public Employee() {}

    public Employee(String Employee_ID, String Employee_Name, String Employee_Address, String Employee_Password,
                    String Employee_Phone_number, String Employee_username, String Role) {
        this.employee_ID = Employee_ID;
        this.employee_name = Employee_Name;
        this.employee_address = Employee_Address;
        this.employee_password = Employee_Password;
        this.employee_phone_number = Employee_Phone_number;
        this.employee_username = Employee_username;
        this.role = Role;
    }

    public String getEmployee_ID() {
        return employee_ID;
    }

    public void setEmployee_ID(String employee_ID) {
        this.employee_ID = employee_ID;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_address() {
        return employee_address;
    }

    public void setEmployee_address(String employee_address) {
        this.employee_address = employee_address;
    }

    public String getEmployee_password() {
        return employee_password;
    }

    public void setEmployee_password(String employee_password) {
        this.employee_password = employee_password;
    }

    public String getEmployee_phone_number() {
        return employee_phone_number;
    }

    public void setEmployee_phone_number(String employee_phone_number) {
        this.employee_phone_number = employee_phone_number;
    }

    public String getEmployee_username() {
        return employee_username;
    }

    public void setEmployee_username(String employee_username) {
        this.employee_username = employee_username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
