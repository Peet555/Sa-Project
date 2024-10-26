package ku.cs;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import ku.cs.controller.LoginController;
import ku.cs.controller.RegisterCustomerController;
import ku.cs.controller.RegisterEmployeeController;
import ku.cs.repository.CustomerRepository;
import ku.cs.repository.EmployeeRepository;
import ku.cs.service.CustomerService;
import ku.cs.service.EmployeeSevice;
import org.json.JSONObject;
import com.sun.net.httpserver.HttpServer;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Connection conn = null;
        String user;
        String password;
        String dataSourceUrl = "jdbc:mysql://localhost/sa project";

        try (InputStream inputStream = Main.class.getResourceAsStream("./database-access.json")) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A");
            String content = scanner.hasNext() ? scanner.next() : "{}";

            JSONObject json = new JSONObject(content);
            user = json.getString("DB_USERNAME");
            password = json.getString("DB_PASSWORD");

            scanner.close();
        }

        // Connect to Database
        System.out.println("Connecting to " + dataSourceUrl);

        try {
            conn = DriverManager.getConnection(dataSourceUrl, user, password);
            System.out.println("Database connected successfully");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }


        int port = 25670;

        // create repository
        CustomerRepository customerRepository = new CustomerRepository(conn);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/login", new LoginController());
        server.createContext("/register_customer", new RegisterCustomerController(new CustomerService(new CustomerRepository(conn))));
        server.createContext("/register_employee",new RegisterEmployeeController(new EmployeeSevice(new EmployeeRepository(conn))));

        server.setExecutor(null);
        server.start();

        System.out.println("Server is running on port " + port);
    }
}