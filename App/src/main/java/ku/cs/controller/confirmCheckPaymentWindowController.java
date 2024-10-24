package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class confirmCheckPaymentWindowController {
    @FXML
    public Button cancleButton ;

    @FXML
    public void cancleClick(){
        Stage stage = (Stage) cancleButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void confirmClick(){
        try {
            FXRouter.goTo("verifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //อัตเดตข้อมูลที่บันทึก


    }

}
