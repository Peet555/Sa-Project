package ku.cs.connect;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class RegisterCustomerConnect {

    public void insertCustomer(String username, String name, String password) {
        String sql = "INSERT INTO customer (Customer_ID, Username, Name,Email,Customer_password,Customer_Address,Customer_Phone_number) VALUES (?,?,?,?,?, ?, ?)";
        try{
            Connection conn = DatabaseConnect.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, UUID.randomUUID().toString().substring(0, 33));
                pstmt.setString(2, username);
                pstmt.setString(3, name);
                pstmt.setString(4, "");
                pstmt.setString(5, password);
                pstmt.setString(6, "");
                pstmt.setString(7, "");
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting customer: " + e.getMessage());

        }
    }
}