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
    public void editProfileCustomer(String name, String phone, String address, String email, String id) {
        String updateQuery = "UPDATE customer SET Name = ?, Customer_Phone_Number = ?, Customer_Address = ?, Email = ? WHERE Customer_ID = ?";
        try {
            Connection conn = DatabaseConnect.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
                ps.setString(1, name);
                ps.setString(2, phone);
                ps.setString(3, address);
                ps.setString(4, email); // Setting email
                ps.setString(5, id);    // Setting id

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("User info updated successfully.");
                } else {
                    System.out.println("No user found with the given ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }
}
