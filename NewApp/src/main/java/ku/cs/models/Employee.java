package ku.cs.models;

public class Employee {
    String ID;
    String name;
    String address;
    String password;
    String phoneNumber;
    String username;
    String role;

    public Employee(){}

    public Employee(String ID, String name, String address, String password, String phoneNumber, String username, String role) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.role = role;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "User [" + this.ID + ", " + this.username + ", " + this.name + ", " + this.role + "]";
    }
}
