package ku.cs.controller;


import animatefx.animation.FadeIn;
import animatefx.animation.FadeOutDown;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ku.cs.connect.LoginConnect;
import ku.cs.models.Customer;
import ku.cs.models.Employee;
import ku.cs.network.CustomerInfoClient;
import ku.cs.network.EmployeeInfoClient;
import ku.cs.network.LoginClient;
import ku.cs.services.FXRouter;
import ku.cs.services.FocusedPropertyUtil;
import ku.cs.services.RootService;

import java.io.IOException;
import java.net.URL;

public class loginController {

    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Label errorLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public VBox body;
    @FXML
    protected ImageView logo;
    @FXML
    public ComboBox<String> role;
    private LoginConnect loginConnect = new LoginConnect();
    public void initialize() {

        URL url = getClass().getResource("/ku/cs/picture/backgroundlogin.png");
        if (url != null) logo.setImage(new Image(url.toExternalForm()));
        role.getItems().addAll("Customer", "Employee");

    }


    public void onLoginButton(ActionEvent actionEvent) throws IOException {
        if(role.getValue() == null) {
            RootService.showErrorBar("Please select a role");
        }else {
            if (role.getValue().equals("Customer")) {
                loginConnect.selectCustomer(username.getText(), password.getText());
                RootService.open("homePage.fxml");
            } else {
                if(loginConnect.selectEmployee(username.getText(), password.getText())) {
                    if(loginConnect.selectRole(username.getText()).equals("Sale")){
                        RootService.open("salerCheckOrderPage.fxml");
                    }else{
                        RootService.open("stock.fxml");
                    }

                }
            }
        }
    }


    public void onRegisterCustomerButton(ActionEvent actionEvent) throws IOException {

        RootService.open("registerCustomer.fxml");

    }

    public void onRegisterEmployeeButton(ActionEvent actionEvent) throws IOException {
        RootService.open("registerEmployee.fxml");
    }
}
