package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ku.cs.services.FXRouter;

import java.io.IOException;

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
        // จำนวนออเดอร์ที่จะแสดง
        int orderCount = 5;  // ตัวอย่าง mock ข้อมูล 5 รายการ

        // เพิ่มรายการออเดอร์แต่ละอันเข้าไปใน VBox
        for (int i = 0; i < orderCount; i++) {
            try {
                addOrderHistoryItem(i);
            } catch (IOException e) {
                System.err.println("Error loading order history item: " + e.getMessage());
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

    // Method สำหรับเพิ่ม customerOrderHistoryItem ลงใน VBox
    private void addOrderHistoryItem(int index) throws IOException {
        // โหลด FXML ของ customerOrderHistoryItem.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/customerOrderHistoryItem.fxml"));
        Node node = loader.load();

        // เพิ่ม item (node) ลงใน VBox
        vBox.getChildren().add(node);
    }


}
