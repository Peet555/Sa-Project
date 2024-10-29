package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class confirmCheckPaymentWindowController {
    @FXML
    public Button cancleButton , okayButton ;

    private checkProofPaymentController checkProofPaymentController;

    public void setCheckProofPaymentController(checkProofPaymentController controller) {
        this.checkProofPaymentController = controller;
    }

    @FXML
    public void cancleClick(){
        Stage stage = (Stage) cancleButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void confirmClick() {
        System.out.println("Confirm button clicked."); // Debug statement
        if (checkProofPaymentController != null) {
            checkProofPaymentController.updateInvoiceStatus();
        }

        // Navigate to the verify payment page
        try {
            FXRouter.goTo("verifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        closeWindow(); // Close the confirmation window
    }


    @FXML
    public void closeWindow() {
        // ปิดหน้าต่างที่เป็น Modal (จะใช้ Stage ที่เป็นหน้าต่างใหม่)
        Stage stage = (Stage) okayButton.getScene().getWindow();
        stage.close();  // ปิดหน้าต่าง Modal
    }



}
