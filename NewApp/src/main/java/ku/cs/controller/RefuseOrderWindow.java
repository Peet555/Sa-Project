package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ku.cs.connect.OrderStatusUpdateConnect;

import java.io.IOException;

public class RefuseOrderWindow {
    @FXML
    private Button confirmButton, cancelButton;

    private String orderId;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> {
            updateOrderStatusToConfirmed(); // เรียกใช้ฟังก์ชันเพื่ออัปเดตสถานะ
            closeWindow();
        });

        cancelButton.setOnAction(event -> {
            closeWindow();
        });
    }
    private void updateOrderStatusToConfirmed() {
        OrderStatusUpdateConnect statusUpdater = new OrderStatusUpdateConnect();
        statusUpdater.updateOrderStatus(orderId, 0); // เปลี่ยนสถานะเป็น 2
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

}
