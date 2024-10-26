package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class confirmReadyForDeliveryWindow {

    @FXML
    private Button confirmButton, cancelButton;

    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> {
            closeWindow();
        });

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
