package ku.cs.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    private static Connection connection = null;

    // กำหนดข้อมูลการเชื่อมต่อฐานข้อมูล
    private static final String URL = "jdbc:mysql://localhost:3306/sa project";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // เมธอดสำหรับเริ่มการเชื่อมต่อ
    public static void initializeConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to the database.");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database: " + e.getMessage());
            }
        }
    }

    // เมธอดสำหรับดึงการเชื่อมต่อ
    public static Connection getConnection() {
        return connection;
    }

    // เมธอดสำหรับปิดการเชื่อมต่อ
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close the connection: " + e.getMessage());
            }
        }
    }
}
