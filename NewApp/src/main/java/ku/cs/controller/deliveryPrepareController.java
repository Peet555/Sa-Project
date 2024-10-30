package ku.cs.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.models.Order;
import ku.cs.services.FXRouter;
import ku.cs.connect.DatabaseConnect;

import java.io.IOException;
import java.sql.*;

public class deliveryPrepareController {
    @FXML
    public ImageView logo;

    @FXML
    private TableView<Order> deliveryTable;
    @FXML
    private TableColumn<Order, String> Order_ID;
    @FXML
    private TableColumn<Order, String> Order_Type;
    @FXML
    private TableColumn<Order, String> Order_Status;
    @FXML
    private TableColumn<Order, String> Order_Timestamp;
    @FXML
    private TableColumn<Order, String> Delivery_date;

    @FXML
    private Button prepareButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button productIntoStockButton;

    private ObservableList<Order> orderList;

    @FXML
    public void initialize() {
        // Setting cell value factories for table columns
        Order_ID.setCellValueFactory(new PropertyValueFactory<>("Order_ID"));
        Order_Type.setCellValueFactory(new PropertyValueFactory<>("Order_Type"));
        Order_Status.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getOrderStatus()));
        Order_Timestamp.setCellValueFactory(new PropertyValueFactory<>("Order_Timestamp"));
        Delivery_date.setCellValueFactory(new PropertyValueFactory<>("Delivery_date"));

        // Disable the prepare and productIntoStock buttons by default
        prepareButton.setDisable(true);
        productIntoStockButton.setDisable(true);

        // Enable the buttons only when a row is selected
        deliveryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            prepareButton.setDisable(newSelection == null); // Enable if a row is selected
            productIntoStockButton.setDisable(newSelection == null || !newSelection.getOrder_Type().equals("สั่งจอง")); // Enable if the selected order type is "สั่งจอง"
        });

        // Load orders from the database into the delivery table
        loadOrders();

        // Button action to open confirmation window for prepare button
        prepareButton.setOnAction(event -> {
            try {
                prepareDelivery();
            } catch (IOException e) {
                System.err.println("Error opening payment window: " + e.getMessage());
            }
        });

        // Button action for profile
        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("employeeWarehouseProfile");
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });

    }

    private void loadOrders() {
        orderList = FXCollections.observableArrayList(); // Initialize the ObservableList

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
                orderList.add(order); // Add the order to the list
            }
        } catch (SQLException e) {
            System.err.println("Error loading orders: " + e.getMessage());
        }

        deliveryTable.setItems(orderList); // Set the items of the table
    }

    // Method for opening delivery preparation window
    private void prepareDelivery() throws IOException {
        Order selectedOrder = deliveryTable.getSelectionModel().getSelectedItem(); // Get the selected order
        if (selectedOrder != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/specifyDateForDeliveryWindow.fxml"));
            Parent root = loader.load();

            specifyDateForDeliveryWindow controller = loader.getController();
            controller.setOrderDetails(selectedOrder.getOrder_ID(), selectedOrder.getOrder_Type());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } else {
            System.out.println("No order selected.");
        }
    }

    // New method for handling product into stock
    @FXML
    public void handleProductIntoStock() {
        Order selectedOrder = deliveryTable.getSelectionModel().getSelectedItem(); // Get the selected order
        if (selectedOrder != null) {
            try {
                // Update the Order_Status to 4 for the selected order
                updateOrderStatus(selectedOrder.getOrder_ID());
                // Reload the orders after update
                loadOrders();
            } catch (SQLException e) {
                System.err.println("Error updating order status: " + e.getMessage());
            }
        } else {
            System.out.println("No order selected.");
        }
    }


    // Method to update order status
    private void updateOrderStatus(String orderId) throws SQLException {
        Connection connection = DatabaseConnect.getConnection();

        // Update Order_Status in the orders table
        String updateOrderSql = "UPDATE `order` SET Order_Status = ? WHERE Order_ID = ?";
        try (PreparedStatement updateOrderStmt = connection.prepareStatement(updateOrderSql)) {
            updateOrderStmt.setInt(1, 4); // Set Order_Status to 4
            updateOrderStmt.setString(2, orderId);
            updateOrderStmt.executeUpdate();
        }

        // Update Status_Pay in the invoice table
        String updateInvoiceSql = "UPDATE invoice SET Status_Pay = ? WHERE Order_ID = ?";
        try (PreparedStatement updateInvoiceStmt = connection.prepareStatement(updateInvoiceSql)) {
            updateInvoiceStmt.setInt(1, 4); // Set Status_Pay to 4
            updateInvoiceStmt.setString(2, orderId);
            updateInvoiceStmt.executeUpdate();
        }
    }




    @FXML
    public void goStock() {
        try {
            FXRouter.goTo("stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
