package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class productOrderListCustomerController {

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public VBox vBox;

    @FXML
    private Button handleContinueShopping; // อ้างอิงถึงปุ่ม "เลือกซื้อสินค้าต่อ"

    @FXML
    public void initialize() throws IOException {
        // จำนวนสินค้าที่จะ Mock ข้อมูล (เช่น 5 รายการ)
        int itemCount = 5;

        // เพิ่มรายการสินค้าแต่ละอันเข้าไปใน VBox
        for (int i = 0; i < itemCount; i++) {
            addProductItem(i);
        }

        // ตั้งค่า ScrollPane ให้สามารถเลื่อนดู VBox ได้
        scrollPane.setContent(vBox);

        handleContinueShopping.setOnAction(event -> {
            try {
                FXRouter.goTo("Homepage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println();

            }
        });
    }

    // Method สำหรับเพิ่มสินค้า Mock ลงใน VBox
    private void addProductItem(int index) throws IOException {
        // โหลด FXML สำหรับแต่ละสินค้าที่จะนำมาแสดง (orderListCustomerItem.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/orderListCustomerItem.fxml"));
        Node node = loader.load();

        // เพิ่มสินค้า (node) ลงใน VBox
        vBox.getChildren().add(node);
    }


}
