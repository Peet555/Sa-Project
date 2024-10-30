package ku.cs.connect;

import ku.cs.controller.salerCheckOrderPageController.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ku.cs.models.OrderUtils;

public class sellerCheckOrderConnect {
    public ObservableList<Order> getOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        String query = "SELECT Order_ID, Order_Type, Order_Status, Order_Timestamp FROM `order`";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String orderID = resultSet.getString("Order_ID");
                String orderType = resultSet.getString("Order_Type");
                int status = resultSet.getInt("Order_Status");
                String orderTimestamp = resultSet.getString("Order_Timestamp");

                // Convert Order_Status based on Order_Type
                String orderStatus = OrderUtils.getOrderStatus(orderType, status);

                orders.add(new Order(orderID, orderType, orderStatus, orderTimestamp));
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch orders: " + e.getMessage());
        }

        return orders;
    }
}
