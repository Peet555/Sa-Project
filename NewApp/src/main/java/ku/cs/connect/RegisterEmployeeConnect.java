package ku.cs.connect;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class RegisterEmployeeConnect {

    public void insertEmployee(String username, String name, String password,String role) {
        String sql = "INSERT INTO employee (Employee_ID,Employee_Name,Employee_Address,Employee_Password,Employee_Phone_number,Employee_username,Role) VALUES (?,?,?,?,?, ?, ?)";

        try{
            Connection conn = DatabaseConnect.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, UUID.randomUUID().toString().substring(0, 33));
                pstmt.setString(2, name);
                pstmt.setString(3, "");
                pstmt.setString(4, password);
                pstmt.setString(5, "");
                pstmt.setString(6, username);
                pstmt.setString(7, role);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting customer: " + e.getMessage());

        }
    }
}
