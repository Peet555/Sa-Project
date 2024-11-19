package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ku.cs.connect.OrderStatusUpdateConnect;

import java.sql.SQLException;

public class confirmReceiptProductController {

    @FXML
    private Button confirmButton; // ปุ่มยืนยัน

    @FXML
    private Button cancelButton;  // ปุ่มยกเลิก

    private String orderId; // รหัสออเดอร์
    private String orderType; // ประเภทการสั่งซื้อ

    // Setter สำหรับการส่งข้อมูลจาก customerOrderHistoryItemController
    public void setOrderData(String orderId, String orderType) {
        this.orderId = orderId;
        this.orderType = orderType;
    }

    @FXML
    public void initialize() {
        // เมื่อกดปุ่ม "ยืนยัน" ให้ปิดหน้าต่างและอัปเดตสถานะ
        confirmButton.setOnAction(event -> {
            updateOrderStatus();
            closeWindow();
        });

        // เมื่อกดปุ่ม "ยกเลิก" ให้ปิดหน้าต่าง
        cancelButton.setOnAction(event -> {
            closeWindow();
        });
    }

    // เมธอดสำหรับปิดหน้าต่าง
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    // เมธอดสำหรับอัปเดต Order_Status และ Status_Pay
    private void updateOrderStatus() {
        int newOrderStatus;
        int newStatusPay;

        // กำหนดค่าตามประเภทการสั่งซื้อ
        if ("สั่งซื้อ".equals(orderType)) {
            newOrderStatus = 5;
            newStatusPay = 5;
        } else if ("สั่งจอง".equals(orderType)) {
            newOrderStatus = 7;
            newStatusPay = 7;
        } else {
            return; // ถ้าไม่ตรงตามประเภทใดๆ ไม่ต้องทำอะไร
        }

        try {
            // เรียกใช้งาน OrderStatusUpdateConnect
            OrderStatusUpdateConnect.updateOrderAndInvoiceStatus(orderId, newOrderStatus, newStatusPay);
            showSuccessAlert();
        } catch (SQLException e) {
            System.err.println("Failed to update order status: " + e.getMessage());
        }
    }

    // เมธอดสำหรับแสดง Alert
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("สำเร็จ");
        alert.setHeaderText(null);
        alert.setContentText("ยืนยันการรับสินค้าสำเร็จ");
        alert.showAndWait();
    }
}
