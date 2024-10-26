package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class deniedOrderWindowController {

    @FXML
    private Button confirmButton, cancelButton;

    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> {
            closeWindow(); // ปิดหน้าต่างเมื่อกดยืนยัน
        });

        cancelButton.setOnAction(event -> {
            closeWindow(); // ปิดหน้าต่างเมื่อกดยกเลิก
        });
    }

    // เมธอดสำหรับปิดหน้าต่าง
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

}
