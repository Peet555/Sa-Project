package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ku.cs.connect.OrderStatusUpdateConnect;
import ku.cs.connect.orderProductConnect;

import java.io.IOException;

public class confirmOrderAcceptWindowController {

    @FXML
    private Button confirmButton, cancelButton;

    private String orderId;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> {
            try {
                updateOrderStatusToConfirmed(); // เรียกใช้ฟังก์ชันเพื่ออัปเดตสถานะ
                openNotiInvoiceWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        cancelButton.setOnAction(event -> {
            closeWindow();
        });
    }

    private void updateOrderStatusToConfirmed() {
        OrderStatusUpdateConnect statusUpdater = new OrderStatusUpdateConnect();
        statusUpdater.updateOrderStatus(orderId, 2); // เปลี่ยนสถานะเป็น 2
    }

    // เมธอดสำหรับเปิดหน้าต่าง notiInvoiceCreatedWindowController
    private void openNotiInvoiceWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/notiInvoiceCreatedWindow.fxml"));
        Parent notiInvoiceWindow = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(notiInvoiceWindow));
        stage.showAndWait(); // รอจนกว่าจะปิดหน้าต่าง

        closeWindow(); // ปิดหน้าต่าง confirmOrder
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
