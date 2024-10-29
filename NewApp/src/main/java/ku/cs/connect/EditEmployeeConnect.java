package ku.cs.connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditEmployeeConnect {
    public void editProfileEmployee(String name,String phone,String address,String id) {
        String updateQuery = "UPDATE employee SET Employee_Name = ? ,Employee_Phone_Number = ?,Employee_Address = ? WHERE Employee_ID = ?";
        try {
            Connection conn = DatabaseConnect.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
                ps.setString(1, name);
                ps.setString(2, phone);
                ps.setString(3, address);
                ps.setString(4, id);

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("User info updated successfully.");
                } else {
                    System.out.println("No user found with the given ID.");
                }

            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error update customer: " + e.getMessage());

        }
    }
}
