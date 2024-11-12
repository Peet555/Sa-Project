package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import ku.cs.connect.orderProductConnect;
import ku.cs.connect.sellerCheckOrderConnect;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class salerCheckOrderPageController {

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, String> orderIdColumn;
    @FXML
    private TableColumn<Order, String> orderTypeColumn;
    @FXML
    private TableColumn<Order, String> orderStatusColumn;
    @FXML
    private TableColumn<Order, String> orderTimestampColumn;
    @FXML
    private Button profileButton ;
    @FXML
    private ComboBox<String> statusComboBox;

    public void initialize() {
        // กำหนดค่าใน TableColumn โดยใช้ PropertyValueFactory และ String โดยตรง
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderTypeColumn.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderTimestampColumn.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));

        // ดึงข้อมูลจากฐานข้อมูลมาแสดงใน TableView
        sellerCheckOrderConnect dbConnect = new sellerCheckOrderConnect();
        ObservableList<Order> orders = dbConnect.getOrders();
        orderTable.setItems(orders);

        // Listener สำหรับตรวจจับการเลือกค่าใน ComboBox
        statusComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // กรองข้อมูลใน TableView ตามค่า Order_Status ที่เลือก
                ObservableList<Order> filteredOrders = orders.filtered(order -> order.getOrderStatus().equals(newValue));
                orderTable.setItems(filteredOrders);
            } else {
                // หากไม่มีค่าเลือก ให้แสดงข้อมูลทั้งหมด
                orderTable.setItems(orders);
            }
        });

        // เพิ่มค่าใน ComboBox
        ObservableList<String> statusOptions = FXCollections.observableArrayList(
                "รอยืนยัน", "รอชำระเงิน", "รอชำระค่ามัดจำ", "รอสินค้าเข้าคลัง", "ชำระยอดคงเหลือ",
                "ชำระเงินแล้ว", "กำลังจัดส่ง", "ได้รับของแล้ว"
        );
        statusComboBox.setItems(statusOptions);

        // การคลิกสองครั้งที่แถวใน TableView
        orderTable.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Order rowData = row.getItem();
                    try {
                        changeToProductPage(rowData.getOrderID());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("employeeSellerProfile"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });
    }


    private void setStatusComboBoxOptions(String orderType) {
        if ("สั่งซื้อ".equals(orderType)) { // For Purchase orders
            ObservableList<String> purchaseStatusOptions = FXCollections.observableArrayList(
                    "รอยืนยัน", "รอชำระเงิน", "ชำระเงินแล้ว", "กำลังจัดส่ง", "ได้รับของแล้ว"
            );
            statusComboBox.setItems(purchaseStatusOptions);
        } else if ("สั่งจอง".equals(orderType)) { // For Pre-Order/Reservation
            ObservableList<String> preOrderStatusOptions = FXCollections.observableArrayList(
                    "รอยืนยัน", "รอชำระค่ามัดจำ", "รอสินค้าเข้าคลัง", "ชำระยอดคงเหลือ", "ชำระแล้ว", "กำลังจัดส่ง", "ได้รับของแล้ว"
            );
            statusComboBox.setItems(preOrderStatusOptions);
        } else {
            statusComboBox.setItems(FXCollections.observableArrayList()); // Clear options if unknown type
        }
    }

    // เมธอดเพื่อเปลี่ยนหน้าไปยัง salerCheckProductPageController
    private void changeToProductPage(String orderId) throws IOException {
        // ดึงข้อมูลสินค้าของคำสั่งซื้อ
        orderProductConnect productConnect = new orderProductConnect();
        ObservableList<Product> products = productConnect.getProductsForOrder(orderId);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/salerCheckProductPage.fxml"));
        Parent productPage = loader.load();

        // ส่งข้อมูลสินค้าไปยังหน้า salerCheckProductPageController
        salerCheckProductPageController controller = loader.getController();
        controller.setOrderData(orderId, products);

        Stage stage = (Stage) orderTable.getScene().getWindow();
        stage.setScene(new Scene(productPage));
        stage.show();
    }


    // คลาส Order ใช้ String ธรรมดาแทน SimpleStringProperty
    public static class Order {
        private String orderID;
        private String orderType;
        private String orderStatus;
        private String orderTimestamp;

        public Order(String orderID, String orderType, String orderStatus, String orderTimestamp) {
            this.orderID = orderID;
            this.orderType = orderType;
            this.orderStatus = orderStatus;
            this.orderTimestamp = orderTimestamp;
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

        public String getOrderTimestamp() {
            return orderTimestamp;
        }
    }

    @FXML
    public void goVerifyPayment() {
        try {
            FXRouter.goTo("verifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
