package ku.cs.controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import ku.cs.network.RegisterClient;
import ku.cs.services.FXRouter;
import ku.cs.services.FocusedPropertyUtil;
import ku.cs.services.RootService;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class RegisterCustomerController {
    @FXML

    public TextField userName;
    @FXML
    public TextField yourName;
    @FXML
    public PasswordField password;
    @FXML
    public PasswordField confirmPassword;
    @FXML
    public Label userNameLabel;
    @FXML
    public Label yourNameLabel;
    @FXML
    public Label passwordLabel;
    @FXML
    public Label confirmPasswordLabel;
    public Label userNameError;
    public Label passwordError;
    public Label confirmPasswordError;
    @FXML
    private ComboBox<String> role;
    int annoyScore = 0;

    static class Location {
        public double x;
        public double y;

        Location(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }


    @FXML
    protected Button registerButton;
    @FXML
    protected ImageView logo;

    private boolean annoyingAnimationFinish = true;
    final private Location currentAnnoyingButtonLocation = new Location(0, 0);

    @FXML
    private void initialize() {
        URL url = getClass().getResource("/ku/cs/picture/backgroundlogin.png");
        userNameError.setVisible(false);
        passwordError.setVisible(false);
        confirmPasswordError.setVisible(false);
        if (url != null) {
            logo.setImage(new Image(url.toExternalForm()));
        }
        registerButton.hoverProperty().addListener(observable -> {
            annoyScore++;
            if (!annoyingAnimationFinish || annoyScore < 32) return;
            annoyingAnimationFinish = false;
            TranslateTransition a = new TranslateTransition(Duration.seconds(0.4), registerButton);
            a.setFromX(currentAnnoyingButtonLocation.x);
            a.setFromY(currentAnnoyingButtonLocation.y);
            double newX = (new Random().nextDouble() > 0.5 ? 1.0 : -1.0) * (200.0 + (600.0 - 200.0) * new Random().nextDouble());
            double newY = (0.1 + (1.0 - 0.1) * new Random().nextDouble()) * 600 - 500;
            a.setToX(newX);
            a.setToY(newY);
            currentAnnoyingButtonLocation.x = newX;
            currentAnnoyingButtonLocation.y = newY;
            a.setOnFinished(actionEvent -> annoyingAnimationFinish = true);
            a.play();
        });


        FocusedPropertyUtil.setAppearNodeOnFieldFocused(userName, userNameLabel);
        FocusedPropertyUtil.setAppearNodeOnFieldFocused(password, passwordLabel);
        FocusedPropertyUtil.setAppearNodeOnFieldFocused(yourName, yourNameLabel);
        FocusedPropertyUtil.setAppearNodeOnFieldFocused(confirmPassword, confirmPasswordLabel);
    }

    @FXML
    public void onRegister() throws Exception {
        if (!isPasswordValid() ) {
            Platform.runLater(() -> RootService.showErrorBar("Confirm Password failed"));
            return;
        }
        RegisterClient client = new RegisterClient();
        String res;
        res = client.registerCustomer(userName.getText(),yourName.getText(),password.getText());
        System.out.println(res);
        RootService.open("login.fxml");
    }

    @FXML
    public void onGoLogIn() throws IOException {
        RootService.open("login.fxml");
    }

    private boolean isPasswordValid() {
        return password.getText().equals(confirmPassword.getText());
    }
    private boolean isRoleNull(){
        return role.getValue() == null;
    }

}
