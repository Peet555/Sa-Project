package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;  // import FXRouter
import java.io.IOException;

public class orderConfirmationWindowController {

    @FXML
    public Button confirmOrder;  // ปุ่มยืนยันคำสั่งซื้อ

    @FXML
    public Button cancelOrder;  // ปุ่มยกเลิกคำสั่งซื้อ

    @FXML
    public void cancelOrder() {
        // ปิดหน้าต่าง order confirmation และกลับไปหน้า productOrderListCustomerController
        Stage stage = (Stage) cancelOrder.getScene().getWindow();
        stage.close();  // ปิดหน้าต่างนี้
    }

    @FXML
    public void confirmOrder() {
        // ทำการยืนยันคำสั่งซื้อ
        System.out.println("Order Confirmed");

        // ปิดหน้าต่างนี้
        Stage stage = (Stage) confirmOrder.getScene().getWindow();
        stage.close();

        // เปลี่ยนไปยังหน้า customerOrderHistoryController
        try {
            FXRouter.goTo("customerOrderHistory");
        } catch (IOException e) {
            System.err.println("Error navigating to customerOrderHistory: " + e.getMessage());
        }
    }
}
