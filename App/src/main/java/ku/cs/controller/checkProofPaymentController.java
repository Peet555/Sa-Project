package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class checkProofPaymentController {
    @FXML
    public TableView paymentList ;

    @FXML
    public ImageView proofPayment ;

    @FXML
    public void goVerifyPayment(){
        try {
            FXRouter.goTo("VerifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void goCheckOrder(){
        try {
            FXRouter.goTo("SalerCheckOrder");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
