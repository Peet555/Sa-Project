package ku.cs;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;

import com.sun.net.httpserver.HttpServer;

public class Main {
    public static void main(String[] args) throws IOException {
        
        Connection conn = null;
        String datasourceUrl = "jdbc:mysql://localhost:3306/pre_exam";


        try {
            conn = DriverManager.getConnection(datasourceUrl, "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }


        HttpServer server = HttpServer.create(new InetSocketAddress(9000),0);
        server.createContext("/",new MyHandler(conn));

        server.setExecutor(null);
        server.start();

        System.out.println("Server starting");
    }
}