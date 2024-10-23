package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
    public void initialize() {
        // กำหนดค่าใน TableColumn โดยใช้ PropertyValueFactory และ String โดยตรง
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderTypeColumn.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        orderTimestampColumn.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));

        // สร้างข้อมูลจำลอง (Mock Data)
        ObservableList<Order> orders = FXCollections.observableArrayList(
                new Order("001", "Online", "Pending", "2024-10-11 10:00:00"),
                new Order("002", "Offline", "Shipped", "2024-10-11 12:30:00"),
                new Order("003", "Online", "Delivered", "2024-10-12 09:45:00")
        );

        // เพิ่มข้อมูลลงใน TableView
        orderTable.setItems(orders);

        // ตรวจจับการคลิกสองครั้งที่แถวใน TableView
        orderTable.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Order rowData = row.getItem();
                    System.out.println("Double click on: " + rowData.getOrderID());

                    // เปลี่ยนหน้าไปยังหน้า salerCheckProductPageController
                    try {
                        changeToProductPage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    // เมธอดเพื่อเปลี่ยนหน้าไปยัง salerCheckProductPageController
    private void changeToProductPage() throws IOException {
        // โหลดหน้าใหม่
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/salerCheckProductPage.fxml"));
        Parent productPage = loader.load();

        // ตั้งค่า Scene ใหม่
        Stage stage = (Stage) orderTable.getScene().getWindow();
        Scene scene = new Scene(productPage);
        stage.setScene(scene);
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
            FXRouter.goTo("VerifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
