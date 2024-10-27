package ku.cs.connect;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginConnect {
    public boolean selectCustomer(String username, String password){
        String sql = "SELECT customer_password FROM customers WHERE username = ?";

        try {
            // ใช้การเชื่อมต่อจาก DatabaseConnect
            Connection conn = DatabaseConnect.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);

                // เรียกผลลัพธ์ของผู้ใช้จากฐานข้อมูล
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String hashedPassword = rs.getString("customer_password");

                        // ตรวจสอบรหัสผ่านกับ hashed password
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            System.out.println("Login successful!");
                            return true; // การตรวจสอบรหัสผ่านผ่าน
                        } else {
                            System.out.println("Invalid password.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }
        return false; // การตรวจสอบรหัสผ่านไม่ผ่าน
    }
    public boolean selectEmployee(String username, String password){
        String sql = "SELECT Employee_Password FROM employee WHERE Employee_username = ?";

        try {
            // ใช้การเชื่อมต่อจาก DatabaseConnect
            Connection conn = DatabaseConnect.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);

                // เรียกผลลัพธ์ของผู้ใช้จากฐานข้อมูล
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String hashedPassword = rs.getString("Employee_Password");

                        // ตรวจสอบรหัสผ่านกับ hashed password
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            System.out.println("Login successful!");
                            return true; // การตรวจสอบรหัสผ่านผ่าน
                        } else {
                            System.out.println("Invalid password.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
        }
        return false; // การตรวจสอบรหัสผ่านไม่ผ่าน
    }

    public String selectRole(String username){
        String sql = "SELECT Role FROM employee WHERE Employee_username = ?";

        try {
            // ใช้การเชื่อมต่อจาก DatabaseConnect
            Connection conn = DatabaseConnect.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);

                // เรียกข้อมูล role จากฐานข้อมูล
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // คืนค่า role ของผู้ใช้
                        return rs.getString("Role");
                    } else {
                        System.out.println("User not found.");
                        return null; // คืนค่า null หากไม่พบผู้ใช้
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving role: " + e.getMessage());
            return null; // คืนค่า null หากเกิดข้อผิดพลาด
        }
    }

}
