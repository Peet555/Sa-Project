package ku.cs.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.connect.EditProfileCustomerConnect;
import ku.cs.connect.LoginConnect;
import ku.cs.models.Customer;
import ku.cs.models.Product;
import ku.cs.models.User;
import ku.cs.services.FXRouter;


public class editProfileWindowController {

    @FXML
    private Button confirmButton, cancelButton;

    @FXML
    private TextField nameField,phoneNumberField,emailField;
    @FXML
    private TextArea addressField;
    private EditProfileCustomerConnect editProfileCustomerConnect = new EditProfileCustomerConnect();



    public void initialize() {
        confirmButton.setOnAction(event -> {
            User user = LoginConnect.getCurrentUser();
            if(user != null){
                editProfileCustomerConnect.editProfileCustomer(nameField.getText(),phoneNumberField.getText(),emailField.getText(),addressField.getText(), user.getID());
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
