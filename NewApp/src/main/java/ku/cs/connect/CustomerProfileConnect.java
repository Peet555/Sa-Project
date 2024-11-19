package ku.cs.connect;

import ku.cs.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerProfileConnect {
    public Customer profile() {
        Connection connection = DatabaseConnect.getConnection();
        String customerId = LoginConnect.getCurrentUser().getID(); // ใช้ ID จาก currentUser ที่ล็อกอิน
        String query = "SELECT Name, Email, Customer_Address, Customer_Phone_number FROM customer WHERE Customer_ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerId); // ใช้ Customer_ID ของผู้ใช้ที่ล็อกอินอยู่

            ResultSet resultSet = statement.executeQuery();
            Customer customer = new Customer();
            if (resultSet.next()) {
                customer.setName(resultSet.getString("Name"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setAddress(resultSet.getString("Customer_Address"));
                customer.setPhoneNumber(resultSet.getString("Customer_Phone_number"));
            }
            return customer;
        } catch (SQLException e) {
            System.err.println("Failed to load customer data: " + e.getMessage());
        } finally {
            DatabaseConnect.closeConnection();
        }
        return null;
    }
}
