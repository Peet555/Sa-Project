package ku.cs.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class editProfileWindowController {

    @FXML
    private Button confirmButton, cancelButton;

    @FXML
    private TextField nameField,phoneNumberField;
    @FXML
    private TextArea addressField;

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
