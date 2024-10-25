package ku.cs.service;

import ku.cs.controller.RegisterCustomerController;
import ku.cs.entity.Customer;
import ku.cs.repository.CustomerRepository;
import ku.cs.request.RegisterCustomerRequest;
import org.json.JSONObject;

import javax.naming.InvalidNameException;
import java.sql.SQLException;
import java.util.UUID;

public class CustomerService {

    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public boolean isUsernameAvailable(String username) throws SQLException {
        return repository.getCustomerByUserName(username) == null;
    }

    private void validateNull(String s, String name) {
        if (s == null || s.isEmpty()) throw new NullPointerException(name + " is null");
    }


    public void createCustomer(JSONObject json) throws InvalidNameException, SQLException {
        Customer customer = new Customer(
                UUID.randomUUID().toString().substring(0,33),
                json.getString("username"),
                json.getString("name"),
                json.getString("password"),
                "",
                "",
                ""
        );
        repository.createCustomer(customer);



    }
}
