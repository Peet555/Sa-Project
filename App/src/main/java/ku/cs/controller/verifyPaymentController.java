package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class verifyPaymentController {
    @FXML
    public TableView statusPayment ;

    @FXML
    public ImageView logo ;

    @FXML
    public void goCheckOrder(){
        try {
            FXRouter.goTo("SalerCheckOrder");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
