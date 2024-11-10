package ku.cs.connect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ku.cs.models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderDeliveryConnect {

    // ฟังก์ชันสำหรับดึงข้อมูล Order ทั้งหมดจากฐานข้อมูล
    public static ObservableList<Order> loadOrders() {
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        Connection connection = DatabaseConnect.getConnection();

        String sql = "SELECT Order_ID, Employee_ID, Customer_ID, Order_Status, Order_Timestamp, Outstanding_Balance, Order_Type, Delivery_date FROM `order`";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String orderId = resultSet.getString("Order_ID");
                String employeeId = resultSet.getString("Employee_ID");
                String customerId = resultSet.getString("Customer_ID");
                int orderStatus = resultSet.getInt("Order_Status");
                Timestamp orderTimestamp = resultSet.getTimestamp("Order_Timestamp");
                int outstandingBalance = resultSet.getInt("Outstanding_Balance");
                String orderType = resultSet.getString("Order_Type");
                String deliveryDate = resultSet.getString("Delivery_date");

                Order order = new Order(orderId, employeeId, customerId, orderStatus, orderTimestamp, outstandingBalance, orderType, deliveryDate);
                orderList.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Error loading orders: " + e.getMessage());
        }
        return orderList;
    }
}
