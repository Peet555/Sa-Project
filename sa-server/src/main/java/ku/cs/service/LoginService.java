package ku.cs.service;

import ku.cs.entity.AccessToken;
import ku.cs.entity.Customer;
import ku.cs.entity.Employee;
import ku.cs.repository.CustomerRepository;
import ku.cs.repository.EmployeeRepository;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class LoginService {
    private EmployeeRepository employeeRepository;
    private CustomerRepository customerRepository;
    public LoginService(CustomerRepository cusRepository,EmployeeRepository employeeRepository) {
        this.customerRepository = cusRepository;
        this.employeeRepository = employeeRepository;
    }


    private boolean isUsernameValid(String username) throws SQLException {
        return customerRepository.getCustomerByUserName(username) == null || employeeRepository.getEmployeeByUserName(username) == null;
    }

    private boolean validateCustomerPassword(Customer customer, String password) throws SQLException {
        return customer.validatePassword(password);
    }

    private boolean validateEmployeePassword(Employee employee, String password) throws SQLException {
        return employee.validatePassword(password);
    }

    public JSONObject login(JSONObject jsonObject) throws LoginException, SQLException {

        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");
        System.out.println(password);
        Customer customer = customerRepository.getCustomerByUserName(username);
        System.out.println(customer.getCustomerId());
        System.out.println(customer.getCustomer_password());
        System.out.println(customer.getUsername());
        System.out.println(customer.getName());
        if (!isUsernameValid(username)) {
            throw new LoginException("Username not found");
        }
        if (customerRepository.getCustomerByUserName(username) != null) {
            if (!validateCustomerPassword(customerRepository.getCustomerByUserName(username), password)) {
                throw new LoginException("Invalid password");
            }
            AccessToken accessToken = AuthenticationService.get().registerToken(customerRepository.getCustomerByUserName(username));
            JSONObject response = new JSONObject();
            response.put("access_token", accessToken.getToken());
            response.put("life_time", accessToken.getLifetime().toSeconds());
            return response;
        } else if (employeeRepository.getEmployeeByUserName(username) != null) {
            if (!validateEmployeePassword(employeeRepository.getEmployeeByUserName(username), password)) {
                throw new LoginException("Invalid password");
            }
            AccessToken accessToken = AuthenticationService.get().registerEmployeeToken(employeeRepository.getEmployeeByUserName(username));
            JSONObject response = new JSONObject();
            response.put("access_token", accessToken.getToken());
            response.put("life_time", accessToken.getLifetime().toSeconds());
            return response;
        }
        return null;
    }
}
