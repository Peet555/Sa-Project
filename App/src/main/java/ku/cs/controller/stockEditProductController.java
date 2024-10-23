package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;


import java.awt.*;
import java.io.IOException;

public class stockEditProductController {
    @FXML
    public ImageView logo ;

    @FXML
    public TextField nameText ;

    @FXML
    public TextField quantityText ;
    @FXML
    public TextField typeText ;
    @FXML
    public TextField priceText ;

    @FXML
    public ImageView productPic ;

    @FXML
    public TextArea description ;

    @FXML
    public Hyperlink upload ;

    @FXML
    public void saveEdit() {
        try {
            FXRouter.goTo("Stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cancleEdit() {
        try {
            FXRouter.goTo("Stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goOrder(){
        try {
            FXRouter.goTo("OrderStock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goDeliver(){
        try {
            FXRouter.goTo("Delivery");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goStock(){
        try {
            FXRouter.goTo("Stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
