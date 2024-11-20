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
import ku.cs.connect.OrderStatusUpdateConnect;
import ku.cs.models.Order;
import ku.cs.services.FXRouter;
import ku.cs.connect.DatabaseConnect;
import ku.cs.connect.OrderDeliveryConnect;


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
        orderList = OrderDeliveryConnect.loadOrders();
        deliveryTable.setItems(orderList);

        // Button action to open confirmation window for prepare button
        prepareButton.setOnAction(event -> {
            try {
                prepareDelivery();
                refreshTable();
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

    private void refreshTable() {
        orderList = OrderDeliveryConnect.loadOrders(); // โหลดข้อมูลใหม่จากฐานข้อมูล
        deliveryTable.setItems(orderList); // อัปเดตข้อมูลในตาราง
    }


    // Method for opening delivery preparation window
    private void prepareDelivery() throws IOException {
        Order selectedOrder = deliveryTable.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/specifyDateForDeliveryWindow.fxml"));
            Parent root = loader.load();

            specifyDateForDeliveryWindow controller = loader.getController();
            controller.setOrderDetails(selectedOrder.getOrder_ID(), selectedOrder.getOrder_Type());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait(); // รอจนกว่าผู้ใช้จะปิดหน้าต่าง

            refreshTable(); // โหลดข้อมูลใหม่หลังจากที่หน้าต่างปิดลง
        } else {
            System.out.println("No order selected.");
        }
    }

    @FXML
    public void handleProductIntoStock() {
        Order selectedOrder = deliveryTable.getSelectionModel().getSelectedItem(); // Get the selected order
        if (selectedOrder != null) {
            try {
                // เรียกใช้ UpdateProductIntoStock จาก OrderStatusUpdateConnect
                OrderStatusUpdateConnect connect = new OrderStatusUpdateConnect();
                connect.UpdateProductIntoStock(selectedOrder.getOrder_ID());

                // Reload the orders after update
                orderList = OrderDeliveryConnect.loadOrders();
                deliveryTable.setItems(orderList); // Refresh the table view with updated data

                // Show confirmation alert
                showConfirmationMessage("ระบุสินค้าเข้าคลังเรียบร้อย");

            } catch (SQLException e) {
                System.err.println("Error updating order status: " + e.getMessage());
            }
        } else {
            System.out.println("No order selected.");
        }
    }


    // Method to show confirmation alert
    private void showConfirmationMessage(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("ยืนยันการทำงาน");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
