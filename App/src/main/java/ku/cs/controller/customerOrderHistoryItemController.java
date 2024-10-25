package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class customerOrderHistoryItemController {

    @FXML
    public Button paymentButton;  // ปุ่มชำระเงิน

    @FXML
    public Button confirmReceiptProduct; // ปุ่มยืนยันการรับสินค้า

    @FXML
    private Label orderDetailsButton;  // ปุ่มดูรายละเอียด

    @FXML
    public void initialize() {
        // กำหนดการทำงานของปุ่มชำระเงิน
        paymentButton.setOnAction(event -> {
            try {
                openPaymentWindow();
            } catch (IOException e) {
                System.err.println("Error opening payment window: " + e.getMessage());
            }
        });

        // กำหนดการทำงานของปุ่มยืนยันการรับสินค้า
        confirmReceiptProduct.setOnAction(event -> {
            try {
                openConfirmReceiptProduct();
            } catch (IOException e) {
                System.err.println("Error opening payment window: " + e.getMessage());
            }
        });
        // กำหนดการทำงานเมื่อกดปุ่ม "ดูรายละเอียด"
        orderDetailsButton.setOnMouseClicked(event -> {
            try {
                FXRouter.goTo("orderDetailsPage"); // เปลี่ยนไปหน้า orderDetailsPage
            } catch (IOException e) {
                System.err.println("Cannot go to order details page: " + e.getMessage());
            }
        });
    }

    // Method สำหรับเปิดหน้าต่าง paymentOrderWindow
    private void openPaymentWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/paymentOrderWindow.fxml"));
        Parent root = loader.load();

        // สร้าง Stage สำหรับหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Payment Confirmation");
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal (โฟกัสเฉพาะหน้าต่างนี้)
        stage.showAndWait();  // แสดงหน้าต่าง
    }

    // Method สำหรับเปิดหน้าต่าง paymentOrderWindow
    private void openConfirmReceiptProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmReceiptProduct.fxml"));
        Parent root = loader.load();

        // สร้าง Stage สำหรับหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Order Receipt Confirmation");
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal (โฟกัสเฉพาะหน้าต่างนี้)
        stage.showAndWait();  // แสดงหน้าต่าง
    }
}
