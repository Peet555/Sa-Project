package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class confirmCheckPaymentWindowController {
    @FXML
    public Button cancleButton , okayButton ;

    @FXML
    public void cancleClick(){
        Stage stage = (Stage) cancleButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void confirmClick(){
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void closeWindow() {
        // ปิดหน้าต่างที่เป็น Modal (จะใช้ Stage ที่เป็นหน้าต่างใหม่)
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();  // ปิดหน้าต่าง Modal
    }

}
