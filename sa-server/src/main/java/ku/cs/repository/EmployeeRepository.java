package ku.cs.repository;

import ku.cs.entity.Customer;
import ku.cs.entity.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeRepository {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public EmployeeRepository(Connection connection) {
        this.connection = connection;
    }

    public void createEmployee(Employee employee) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.statement.executeUpdate("INSERT INTO employee " +
                    "(Employee_ID,Employee_Name,Employee_Address,Employee_Password,Employee_Phone_number,Employee_username,Role) " +
                    String.format("VALUES ('%s','%s','%s','%s','%s','%s','%s');",
                            employee.getEmployee_ID(),
                            employee.getEmployee_name(),
                            employee.getEmployee_address(),
                            employee.getEmployee_password(),
                            employee.getEmployee_phone_number(),
                            employee.getEmployee_username(),
                            employee.getRole()));
        } finally {
            this.statement.close();
        }
    }

    public Employee getEmployeeByUserName(String username) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    String.format(
                            "SELECT  Employee_ID,Employee_Name,Employee_Address,Employee_Password,Employee_Phone_number,Employee_username,Role FROM employee WHERE username = '%s';",
                            username));

            this.resultSet.next();
            String resultUUID = resultSet.getString("Employee_ID");
            String resultName = resultSet.getString("Employee_Name");
            String resultAddress = resultSet.getString("Employee_Address");
            String resultPassword = resultSet.getString("Employee_Password");
            String resultPhone = resultSet.getString("Employee_Phone_number");
            String resultUsername = resultSet.getString("Employee_username");
            String resultRole = resultSet.getString("Role");


            return new Employee(resultUUID,resultName,resultAddress,resultPassword,resultPhone,resultUsername,resultRole);

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

    public Employee getEmployeeByCustomerID(String username) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    String.format(
                            "SELECT  Employee_ID,Employee_Name,Employee_Address,Employee_Password,Employee_Phone_number,Employee_username,Role FROM employee WHERE Customer_ID = '%s';",
                            username));

            this.resultSet.next();
            String resultUUID = resultSet.getString("Employee_ID");
            String resultName = resultSet.getString("Employee_Name");
            String resultAddress = resultSet.getString("Employee_Address");
            String resultPassword = resultSet.getString("Employee_Password");
            String resultPhone = resultSet.getString("Employee_Phone_number");
            String resultUsername = resultSet.getString("Employee_username");
            String resultRole = resultSet.getString("Role");


            return new Employee(resultUUID,resultName,resultAddress,resultPassword,resultPhone,resultUsername,resultRole);

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }
}
