package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import ku.cs.connect.CustomerOrderConnect;
import ku.cs.connect.LoginConnect;
import ku.cs.models.Order;
import ku.cs.services.FXRouter;

import java.io.IOException;
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
    private ComboBox<String> order_choice;


    @FXML
    public void initialize() {

        // เพิ่มตัวเลือกใน ComboBox
        order_choice.getItems().addAll("order รอดำเนินการ", "ประวัติการสั่งซื้อ");

        // ตัวเลือกเริ่มต้น
        order_choice.setValue("order รอดำเนินการ");

        // เพิ่ม Listener เพื่อเปลี่ยนการแสดงผลตามการเลือกใน ComboBox
        order_choice.setOnAction(event -> loadOrdersByStatus());
        // เรียกใช้ครั้งแรกเมื่อเปิดหน้า

        loadOrdersByStatus();

        List<Order> orders = CustomerOrderConnect.fetchCustomerOrders(LoginConnect.getCurrentUser().getID());
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

    // Method สำหรับโหลดข้อมูลตามการเลือกใน ComboBox
    private void loadOrdersByStatus() {
        vBox.getChildren().clear();
        List<Order> orders = CustomerOrderConnect.fetchCustomerOrders(LoginConnect.getCurrentUser().getID());

        String choice = order_choice.getValue();
        for (Order order : orders) {
            try {
                if ("ประวัติการสั่งซื้อ".equals(choice) && (order.getOrder_Status() == 5 || order.getOrder_Status() == 7)) {
                    addOrderHistoryItem(order);
                } else if ("order รอดำเนินการ".equals(choice) && (order.getOrder_Status() != 5 && order.getOrder_Status() != 7)) {
                    addOrderHistoryItem(order);
                }
            } catch (IOException e) {
                System.err.println("Failed to add order history item: " + e.getMessage());
            }
        }
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
