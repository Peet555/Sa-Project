package ku.cs.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class deliveryPrepareController {
    @FXML
    public ImageView logo ;

    @FXML
    private TableView<Order> deliveryTable ;
    @FXML
    private TableColumn<Order,String> id ;
    @FXML
    private TableColumn<Order,String> type ;
    @FXML
    private TableColumn<Order,String> status ;
    @FXML
    private TableColumn<Order,String> timeStamp ;
    @FXML
    private TableColumn<Order,String> delivery ;

    @FXML
    public void initialize() {
        // กำหนดค่าใน TableColumn โดยใช้ PropertyValueFactory และ String โดยตรง
        id.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        type.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        status.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        timeStamp.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        delivery.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));

        // สร้างข้อมูลจำลอง (Mock Data)
        ObservableList<Order> orders = FXCollections.observableArrayList(
                new Order("001", "Order", "ชำระแล้ว", "2024-10-11 10:00:00","รอจัดส่ง"),
                new Order("002","Order","ยังไม่ชำระ","2024-10-15 10.00.00","รอจัดส่ง")
        );

        // เพิ่มข้อมูลลงใน TableView
        deliveryTable.setItems(orders);
    }

    @FXML
    public void goOrder(){
        try {
            FXRouter.goTo("orderStock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goStock(){
        try {
            FXRouter.goTo("stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Order {
        private String orderID ;
        private String orderType ;
        private String orderStatus ;
        private String timeStamp ;
        private String deliveryStatus ;

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
