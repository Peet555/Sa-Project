package ku.cs.connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditCustomerConnect {
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
