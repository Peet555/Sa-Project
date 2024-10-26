package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class confirmReceiptProductController {

    @FXML
    private Button confirmButton; // ปุ่มยืนยัน

    @FXML
    private Button cancelButton;  // ปุ่มยกเลิก

    @FXML
    public void initialize() {
        // เมื่อกดปุ่ม "ยืนยัน" ให้ปิดหน้าต่าง
        confirmButton.setOnAction(event -> {
            closeWindow();
        });

        // เมื่อกดปุ่ม "ยกเลิก" ให้ปิดหน้าต่าง
        cancelButton.setOnAction(event -> {
            closeWindow();
        });
    }

    // เมธอดสำหรับปิดหน้าต่าง
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
