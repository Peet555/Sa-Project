package ku.cs;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHandler implements HttpHandler {

    private Connection connection;

    public MyHandler(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String res = "NO DATA";
        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT Pname FROM PART");
            res = rs.getArray(0).toString();
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String response = res;    
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
        
}
