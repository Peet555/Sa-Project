package ku.cs.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;
public class orderDetailsPageController {

    @FXML
    public VBox vBox;

    @FXML
    public Button profileButton ;

    @FXML
    public Button cartButton ;

    @FXML
    public Button orderHistoryButton ;

    @FXML
    public Button homeButton ;

    @FXML
    public Button backButton; // ปุ่ม Back

    @FXML
    public Label totalOrderPrice;

    @FXML
    public void initialize() throws IOException {
        // จำนวนสินค้าที่จะ Mock ข้อมูล (เช่น 5 รายการ)
        int itemCount = 5;

        // เพิ่มรายการสินค้าแต่ละอันเข้าไปใน VBox
        for (int i = 0; i < itemCount; i++) {
            addProductItem(i);
        }

        // ตั้งค่า ScrollPane ให้สามารถเลื่อนดู VBox ได้
        // scrollPane.setContent(vBox);  // ใช้ได้ในกรณีที่มี ScrollPane

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
        // เมื่อกดปุ่ม "Back" ให้กลับไปหน้า customerOrderHistory
        backButton.setOnAction(event -> {
            try {
                FXRouter.goTo("customerOrderHistory"); // เปลี่ยนไปหน้า customerOrderHistory
            } catch (IOException e) {
                System.err.println("Cannot go back to order history: " + e.getMessage());
            }
        });
    }


    // Method สำหรับเพิ่มสินค้า Mock ลงใน VBox
    private void addProductItem(int index) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/orderListCustomerItem.fxml"));
        Node node = loader.load();
        vBox.getChildren().add(node);
    }

}
