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
import ku.cs.services.FXRouter;

import java.io.IOException;

public class deliveryPrepareController {
    @FXML
    public ImageView logo;

    @FXML
    private TableView<Order> deliveryTable;
    @FXML
    private TableColumn<Order, String> id;
    @FXML
    private TableColumn<Order, String> type;
    @FXML
    private TableColumn<Order, String> status;
    @FXML
    private TableColumn<Order, String> timeStamp;
    @FXML
    private TableColumn<Order, String> delivery;

    @FXML
    private Button prepareButton;

    @FXML
    private Button profileButton;

    @FXML
    public void initialize() {
        // Setting cell value factories for table columns
        id.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        type.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        status.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        timeStamp.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        delivery.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));

        // Populating table with sample data
        ObservableList<Order> orders = FXCollections.observableArrayList(
                new Order("001", "Order", "ชำระแล้ว", "2024-10-11 10:00:00", "รอจัดส่ง"),
                new Order("002", "Order", "ยังไม่ชำระ", "2024-10-15 10:00:00", "รอจัดส่ง")
        );
        deliveryTable.setItems(orders);

        // Disable the prepare button by default
        prepareButton.setDisable(true);

        // Enable the prepare button only when a row is selected
        deliveryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            prepareButton.setDisable(newSelection == null); // Enable if a row is selected
        });

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


    // เมธอดสำหรับเปิดหน้าต่างยืนยันการเตรียมจัดส่ง
    private void prepareDelivery() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmReadyForDeliveryWindow.fxml"));
        Parent root = loader.load();

        // สร้าง Stage สำหรับหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal (โฟกัสเฉพาะหน้าต่างนี้)
        stage.showAndWait();  // แสดงหน้าต่าง
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

    public static class Order {
        private String orderID;
        private String orderType;
        private String orderStatus;
        private String timeStamp;
        private String deliveryStatus;

        public Order(String orderID, String orderType, String orderStatus, String timeStamp, String deliveryStatus) {
            this.orderID = orderID;
            this.orderType = orderType;
            this.orderStatus = orderStatus;
            this.timeStamp = timeStamp;
            this.deliveryStatus = deliveryStatus;
        }

        public String getOrderID() {
            return orderID;
        }

        public String getOrderType() {
            return orderType;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public String getDeliveryStatus() {
            return deliveryStatus;
        }
    }
}
