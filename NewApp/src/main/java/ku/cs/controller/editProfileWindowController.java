package ku.cs.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.connect.CustomerProfileConnect;
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

    private CustomerProfileConnect editCustomerConnect = new CustomerProfileConnect();

    private profileController pprofileController ;


    public void initialize() {
        confirmButton.setOnAction(event -> {
            User user = LoginConnect.getCurrentUser();
            if(user != null){
                editCustomerConnect.editProfileCustomer(nameField.getText(),phoneNumberField.getText(),addressField.getText(),emailField.getText(), user.getID());
                pprofileController.loadCustomerData();
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

    public void setProfileController(ku.cs.controller.profileController profileController) {
        this.pprofileController = profileController;
    }

}