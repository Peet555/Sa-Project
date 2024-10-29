package ku.cs.connect;

import ku.cs.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginConnect {

    private static User currentUser;

    public boolean selectCustomer(String username, String password){
        String sql = "SELECT Customer_ID,username,customer_password FROM customer WHERE username = ?";

        try {
            // ใช้การเชื่อมต่อจาก DatabaseConnect
            Connection conn = DatabaseConnect.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);

                // เรียกผลลัพธ์ของผู้ใช้จากฐานข้อมูล
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String hashedPassword = rs.getString("customer_password");
                        String id = rs.getString("Customer_ID");
                        String dbUsername = rs.getString("username");
                        // ตรวจสอบรหัสผ่านกับ hashed password
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            currentUser = new User(id, dbUsername);
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
        String sql = "SELECT Employee_ID,Employee_username,Employee_Password FROM employee WHERE Employee_username = ?";

        try {
            // ใช้การเชื่อมต่อจาก DatabaseConnect
            Connection conn = DatabaseConnect.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);

                // เรียกผลลัพธ์ของผู้ใช้จากฐานข้อมูล
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String hashedPassword = rs.getString("Employee_Password");
                        String id = rs.getString("Employee_ID");
                        String dbUsername = rs.getString("Employee_username");
                        // ตรวจสอบรหัสผ่านกับ hashed password
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            currentUser = new User(id, dbUsername);
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

    public String selectCustomerID(String username){
        String sql = "SELECT Customer_ID FROM customer WHERE username = ?";

        try {
            // ใช้การเชื่อมต่อจาก DatabaseConnect
            Connection conn = DatabaseConnect.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);

                // เรียกข้อมูล role จากฐานข้อมูล
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // คืนค่า role ของผู้ใช้
                        return rs.getString("ID");
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

    public String selectEmployeeID(String username){
        String sql = "SELECT Employee_ID FROM employee WHERE Employee_username = ?";

        try {
            // ใช้การเชื่อมต่อจาก DatabaseConnect
            Connection conn = DatabaseConnect.getConnection();

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);

                // เรียกข้อมูล role จากฐานข้อมูล
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // คืนค่า role ของผู้ใช้
                        return rs.getString("ID");
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
    public static User getCurrentUser() {
        return currentUser;
    }
}
