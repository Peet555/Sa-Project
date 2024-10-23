package ku.cs.controller;


import animatefx.animation.FadeIn;
import animatefx.animation.FadeOutDown;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ku.cs.services.FocusedPropertyUtil;

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
        password.textProperty().addListener((observableValue, s, t1) -> errorLabel.setVisible(false));
        username.textProperty().addListener((observableValue, s, t1) -> errorLabel.setVisible(false));
        URL url = getClass().getResource("/ku/cs/picture/backgroundlogin.png");
        if (url != null) logo.setImage(new Image(url.toExternalForm()));


    }


    public void onLoginButton(ActionEvent actionEvent) {
    }

    public void onRegisterButton(ActionEvent actionEvent) {
    }
}
