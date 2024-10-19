package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class customerOrderHistoryController {

    @FXML
    private VBox vBox;  // เชื่อมกับ VBox จาก FXML

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
