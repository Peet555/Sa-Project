package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ku.cs.connect.DatabaseConnect;
import ku.cs.connect.LoginConnect;
import ku.cs.models.Order;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class customerOrderHistoryController {

    @FXML
    private VBox vBox;  // เชื่อมกับ VBox จาก FXML

    @FXML
    public Button profileButton ;

    @FXML
    public Button cartButton ;

    @FXML
    public Button orderHistoryButton ;

    @FXML
    public Button homeButton;


    @FXML
    public void initialize() {
        List<Order> orders = fetchCustomerOrders();
        for (Order order : orders) {
            try {
                addOrderHistoryItem(order);
            } catch (IOException e) {
                System.err.println("Failed to add order history item: " + e.getMessage());
            }

        }
        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("homePage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to Homepage");
            }
        });
        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("profile"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });

        cartButton.setOnAction(event -> {
            try {
                FXRouter.goTo("customerOrderList");
            } catch (IOException e) {
                System.err.println("Cannot go to cart");
            }
        });

        orderHistoryButton.setOnAction(event -> {
            try {
                FXRouter.goTo("customerOrderHistory");
            } catch (IOException e) {
                System.err.println("Cannot go to order history");
            }
        });

    }

    private List<Order> fetchCustomerOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT Order_ID, Employee_ID, Customer_ID, Order_Status, Order_Timestamp, Outstanding_Balance, Order_Type, Delivery_date FROM `order` WHERE Customer_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, LoginConnect.getCurrentUser().getID()); // ดึง Customer_ID ของผู้ใช้ที่ล็อกอินอยู่
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String orderID = resultSet.getString("Order_ID");
                    String employeeID = resultSet.getString("Employee_ID");
                    String customerID = resultSet.getString("Customer_ID");
                    int orderStatus = resultSet.getInt("Order_Status");
                    Timestamp orderTimestamp = resultSet.getTimestamp("Order_Timestamp");
                    int outstandingBalance = resultSet.getInt("Outstanding_Balance");
                    String orderType = resultSet.getString("Order_Type");
                    String deliveryDate = resultSet.getString("Delivery_date");

                    Order order = new Order(orderID, employeeID, customerID, orderStatus, orderTimestamp, outstandingBalance, orderType, deliveryDate);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch orders: " + e.getMessage());
        }

        return orders;
    }


    // Method สำหรับเพิ่ม customerOrderHistoryItem ลงใน VBox
    private void addOrderHistoryItem(Order order) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/customerOrderHistoryItem.fxml"));
        Node node = loader.load();

        customerOrderHistoryItemController itemController = loader.getController();
        itemController.setOrderData(order);

        vBox.getChildren().add(node);
    }


}
