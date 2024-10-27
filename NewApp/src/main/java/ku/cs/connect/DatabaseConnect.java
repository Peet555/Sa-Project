package ku.cs.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {

    // กำหนดข้อมูลการเชื่อมต่อฐานข้อมูล
    private static final String URL = "jdbc:mysql://localhost:3306/sa2";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    // เมธอดสำหรับเริ่มการเชื่อมต่อ
    public static void initializeConnection() {

            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the database.");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database: " + e.getMessage());
            }

    }

    // เมธอดสำหรับดึงการเชื่อมต่อ
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) { // ตรวจสอบว่าการเชื่อมต่อปิดอยู่หรือไม่
                initializeConnection();
            }
        } catch (SQLException e) {
            System.out.println("Error checking connection status: " + e.getMessage());
        }
        return connection;
    }

    // เมธอดสำหรับปิดการเชื่อมต่อ
    public static void closeConnection() {

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                System.out.println("Failed to close the connection: " + e.getMessage());
            }
        }

}
