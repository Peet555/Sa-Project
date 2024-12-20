package ku.cs.repository;

import ku.cs.entity.Customer;

import java.sql.*;
import java.sql.SQLException;

public class CustomerRepository {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public CustomerRepository(Connection connection) {
        this.connection = connection;
    }

    public void createCustomer(Customer customer) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.statement.executeUpdate("INSERT INTO customer " +
                    "(Customer_ID, username, Name,Email,customer_password,Customer_Address,Customer_Phone_number) " +
                    String.format("VALUES ('%s','%s','%s','%s','%s','%s','%s');",
                            customer.getCustomerId(),
                            customer.getUsername(),
                            customer.getName(),
                            customer.getEmail(),
                            customer.getCustomer_password(),
                            customer.getCustomer_address(),
                            customer.getCustomer_phone_number()));
        } finally {
            this.statement.close();
        }
    }


    public Customer getCustomerByUserName(String username) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    String.format(
                            "SELECT  Customer_ID,username,Name ,Email, customer_password, Customer_Address,Customer_Phone_number FROM customer WHERE username = '%s';",
                            username));

            this.resultSet.next();
            String resultUUID = resultSet.getString("Customer_ID").replaceAll("\\s+", "");
            String resultUsername = resultSet.getString("username").replaceAll("\\s+", "");
            String resultName = resultSet.getString("NAME").replaceAll("\\s+", "");
            String resultEmail = resultSet.getString("Email").replaceAll("\\s+", "");
            String resultPassword = resultSet.getString("customer_password").replaceAll("\\s+", "");
            String resultPhoneNumber = resultSet.getString("Customer_Phone_number").replaceAll("\\s+", "");
            String resultAddress = resultSet.getString("Customer_Address").replaceAll("\\s+", "");



            return new Customer(resultUUID, resultUsername, resultName, resultPassword, resultEmail, resultAddress, resultPhoneNumber);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.statement.close();
        }
    }

    public Customer getCustomerByCustomerID(String username) throws SQLException {
        try {
            this.statement = connection.createStatement();

            this.resultSet = this.statement.executeQuery(
                    String.format(
                            "SELECT  Customer_ID,username,Name ,Email, customer_password, Customer_Address,Customer_Phone_number FROM customer WHERE Customer_ID = '%s';",
                            username));

            this.resultSet.next();
            String resultUUID = resultSet.getString("Customer_ID").replaceAll("\\s+", "");
            String resultUsername = resultSet.getString("username").replaceAll("\\s+", "");
            String resultName = resultSet.getString("NAME").replaceAll("\\s+", "");
            String resultEmail = resultSet.getString("Email").replaceAll("\\s+", "");
            String resultPassword = resultSet.getString("customer_password").replaceAll("\\s+", "");
            String resultPhoneNumber = resultSet.getString("PHONE_NUMBER").replaceAll("\\s+", "");
            String resultAddress = resultSet.getString("Customer_Address").replaceAll("\\s+", "");


            return new Customer(resultUUID, resultUsername, resultName, resultPassword, resultEmail, resultAddress, resultPhoneNumber);

        } catch (SQLException e) {
            return null;
        } finally {
            this.statement.close();
        }
    }

}
