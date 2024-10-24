package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class productQualityWindowController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmOrder;  // Corrected this line to mark it as @FXML

    @FXML
    public void closeWindow() {
        // ปิดหน้าต่างที่เป็น Modal (จะใช้ Stage ที่เป็นหน้าต่างใหม่)
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();  // ปิดหน้าต่าง Modal
    }

    @FXML
    public void confirmOrder(ActionEvent event) {
        try {
            // เมื่อกด "ยืนยัน" ให้เปลี่ยนไปที่หน้าต่าง productOrderListCustomerController
            FXRouter.goTo("orderListPageCustomer");  // ใช้ FXRouter เพื่อไปยังหน้า productOrderListCustomerController
            closeWindow();  // ปิดหน้าต่าง Modal หลังจากไปที่หน้าใหม่
        } catch (IOException e) {
            System.err.println("Cannot go to orderListPageCustomer");
        }
    }
}
