package ku.cs.connect;

import ku.cs.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrderConnect {

    public static List<Order> fetchCustomerOrders(String customerID) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT Order_ID, Employee_ID, Customer_ID, Order_Status, Order_Timestamp, Outstanding_Balance, Order_Type, Delivery_date FROM `order` WHERE Customer_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, customerID); // ใช้ customerID ที่ส่งมาจาก controller
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String orderID = resultSet.getString("Order_ID");
                    String employeeID = resultSet.getString("Employee_ID");
                    String customerIDFromDB = resultSet.getString("Customer_ID");
                    int orderStatus = resultSet.getInt("Order_Status");
                    Timestamp orderTimestamp = resultSet.getTimestamp("Order_Timestamp");
                    int outstandingBalance = resultSet.getInt("Outstanding_Balance");
                    String orderType = resultSet.getString("Order_Type");
                    String deliveryDate = resultSet.getString("Delivery_date");

                    Order order = new Order(orderID, employeeID, customerIDFromDB, orderStatus, orderTimestamp, outstandingBalance, orderType, deliveryDate);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch orders: " + e.getMessage());
        }

        return orders;
    }


}
