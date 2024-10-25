package ku.cs.controller;


import animatefx.animation.FadeIn;
import animatefx.animation.FadeOutDown;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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


    public void initialize() {

        URL url = getClass().getResource("/ku/cs/picture/backgroundlogin.png");
        if (url != null) logo.setImage(new Image(url.toExternalForm()));


    }


    public void onLoginButton(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("Homepage");
    }


    public void onRegisterCustomerButton(ActionEvent actionEvent) throws IOException {

        RootService.open("registerCustomer.fxml");

    }

    public void onRegisterEmployeeButton(ActionEvent actionEvent) throws IOException {
        RootService.open("registerEmployee.fxml");
    }
}
