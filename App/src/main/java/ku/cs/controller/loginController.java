package ku.cs.controller;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class loginController {

    @FXML
    public ImageView bgLogin;


    public void initialize() {
        URL url = getClass().getResource("/ku/cs/picture/backgroundlogin.png");
        if (url != null) bgLogin.setImage(new Image(url.toExternalForm()));

    }
}
