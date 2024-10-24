package ku.cs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnect {

    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "root";  // Replace with your XAMPP username
    private static final String PASSWORD = "";  // Replace with your XAMPP password

    // Establish connection to the database
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database!");
            e.printStackTrace();
        }
        return conn;
    }
    // Add more methods for other CRUD operations as needed
}
