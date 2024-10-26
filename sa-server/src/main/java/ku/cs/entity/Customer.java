package ku.cs.entity;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Customer {
    String customerId;
    String username;
    String name;
    String email;
    String customer_password;
    String customer_address;
    String customer_phone_number;

    public Customer() {}

    public Customer(String customerId, String username, String name, String customer_password, String email, String customer_address,
                String customer_phone_number) {
        this.customerId = customerId;
        this.username = username;
        this.name = name;
        this.customer_password = customer_password;
        this.email = email;
        this.customer_address = customer_address;
        this.customer_phone_number = customer_phone_number;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_phone_number() {
        return customer_phone_number;
    }

    public void setCustomer_phone_number(String customer_phone_number) {
        this.customer_phone_number = customer_phone_number;
    }

    @Override
    public String toString() {
        return "User [" + this.customerId + ", " + this.username + ", " + this.name + "]";
    }

    public boolean validatePassword(String password) {

        if (this.customer_password.equals(password)) return true;

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.customer_password);
        return result.verified;
    }
}
