package ku.cs.controller;

import com.sun.net.httpserver.HttpExchange;
import ku.cs.service.CustomerService;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RegisterCustomerController extends Controller {

    CustomerService service;

    public RegisterCustomerController(CustomerService repository) {
        this.service = repository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            handleRequest(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }

        exchange.close();
    }

    private void handleRequest(HttpExchange exchange) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            String jsonString = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            System.out.println(jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);
            service.createCustomer(jsonObject);
            String response = "Sign Up Successfully";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            responseError(exchange, e);
        }
    }
}
