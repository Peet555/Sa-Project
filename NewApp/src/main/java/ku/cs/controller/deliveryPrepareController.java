package ku.cs.controller;

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

    // ObservableList to hold the orders
    private ObservableList<Order> orderList;

    @FXML
    public void initialize() {
        // Setting cell value factories for table columns
        Order_ID.setCellValueFactory(new PropertyValueFactory<>("Order_ID"));
        Order_Type.setCellValueFactory(new PropertyValueFactory<>("Order_Type"));
        Order_Status.setCellValueFactory(new PropertyValueFactory<>("Order_Status"));
        Order_Timestamp.setCellValueFactory(new PropertyValueFactory<>("Order_Timestamp"));
        Delivery_date.setCellValueFactory(new PropertyValueFactory<>("Delivery_date"));

        // Disable the prepare button by default
        prepareButton.setDisable(true);

        // Enable the prepare button only when a row is selected
        deliveryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            prepareButton.setDisable(newSelection == null); // Enable if a row is selected
        });

        // Load orders from the database into the delivery table
        loadOrders();

        // Button action to open confirmation window
        prepareButton.setOnAction(event -> {
            try {
                prepareDelivery();
            } catch (IOException e) {
                System.err.println("Error opening payment window: " + e.getMessage());
            }
        });
        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("employeeWarehouseProfile"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });
    }

    // Method to load orders from the database
    private void loadOrders() {
        orderList = FXCollections.observableArrayList(); // Initialize the ObservableList

        Connection connection = DatabaseConnect.getConnection();
        String sql = "SELECT Order_ID, Order_Type, Order_Status, Order_Timestamp, Delivery_date FROM `order` "; // Change 'Orders' to your actual table name

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Create Order objects from the result set
                String orderId = resultSet.getString("Order_ID");
                String orderType = resultSet.getString("Order_Type");
                int orderStatus = resultSet.getInt("Order_Status");
                Timestamp orderTimestamp = resultSet.getTimestamp("Order_Timestamp");
                String deliveryDate = resultSet.getString("Delivery_date");

                Order order = new Order(orderId, null, null, orderStatus, orderTimestamp, 0, orderType, deliveryDate); // Fill in with appropriate values
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

            // Set the order ID in the specifyDateForDeliveryWindow controller
            specifyDateForDeliveryWindow controller = loader.getController();
            controller.setOrderId(selectedOrder.getOrder_ID()); // Pass the order ID

            // Create a new stage for the delivery date window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);  // The new window will be modal
            stage.showAndWait();  // Show the new window
        } else {
            System.out.println("No order selected.");
        }
    }


    @FXML
    public void goOrder() {
        try {
            FXRouter.goTo("orderStock");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
