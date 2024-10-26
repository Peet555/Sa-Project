package ku.cs.service;

import ku.cs.entity.Customer;
import ku.cs.entity.Employee;
import ku.cs.repository.CustomerRepository;
import ku.cs.repository.EmployeeRepository;
import ku.cs.request.RegisterCustomerRequest;
import org.json.JSONObject;

import javax.naming.InvalidNameException;
import java.sql.SQLException;
import java.util.UUID;

public class EmployeeSevice {
    private EmployeeRepository repository;

    public EmployeeSevice(EmployeeRepository repository) {
        this.repository = repository;
    }

    public boolean isUsernameAvailable(String username) throws SQLException {
        return repository.getEmployeeByCustomerID(username) == null;
    }

    private void validateNull(String s, String name) {
        if (s == null || s.isEmpty()) throw new NullPointerException(name + " is null");
    }


    public void createEmployee(JSONObject json) throws InvalidNameException, SQLException {
        Employee employee = new Employee(
                UUID.randomUUID().toString().substring(0,33),
                json.getString("name"),
                "",
                json.getString("password"),
                "",
                json.getString("username"),
                json.getString("role")

        );
        repository.createEmployee(employee);



    }
}
