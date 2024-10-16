package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class preOrderProductController {

    @FXML
    private Button cancelButton;

    @FXML
    public void closeWindow() {
        // ปิดหน้าต่างสั่งจอง
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
