package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
        // สามารถทำการส่งข้อมูลหรือไปยังหน้าอื่นได้ตามที่ต้องการ
        System.out.println("Order Confirmed");

        // ปิดหน้าต่าง
        Stage stage = (Stage) confirmOrder.getScene().getWindow();
        stage.close();
    }
}
