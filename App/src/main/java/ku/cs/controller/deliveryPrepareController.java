package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class deliveryPrepareController {
    @FXML
    public ImageView logo ;

    @FXML
    public TableView deliveryTable ;

    @FXML
    public void goOrder(){
        try {
            FXRouter.goTo("OrderStock");
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
