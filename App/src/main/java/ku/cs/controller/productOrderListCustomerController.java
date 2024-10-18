package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class productOrderListCustomerController {

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public VBox vBox;

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
    }

    // Method สำหรับเพิ่มสินค้า Mock ลงใน VBox
    private void addProductItem(int index) throws IOException {
        // โหลด FXML สำหรับแต่ละสินค้าที่จะนำมาแสดง (orderListCustomerItem.fxml)
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/ku/cs/view/orderListCustomerItem.fxml")));
        Node node = loader.load();




        // เพิ่มสินค้า (node) ลงใน VBox
        vBox.getChildren().add(node);
    }
}
