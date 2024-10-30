package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.connect.EditEmployeeConnect;
import ku.cs.connect.LoginConnect;
import ku.cs.models.User;

public class editEmployeeSellerProfileWindowController {
    @FXML
    private Button confirmButton, cancelButton;
    @FXML
    private TextField nameField,phoneNumberField;
    @FXML
    private TextArea addressField;
    private EditEmployeeConnect editEmployeeConnect = new EditEmployeeConnect();


    public void initialize() {
        confirmButton.setOnAction(event -> {
            User user = LoginConnect.getCurrentUser();
            if(user != null){
                editEmployeeConnect.editProfileEmployee(nameField.getText(),phoneNumberField.getText(),addressField.getText(), user.getID());
                closeWindow();
            }
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
