package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.connect.InvoiceDataConnect;
import ku.cs.models.Order;

import java.io.IOException;

public class customerOrderHistoryItemController {

    @FXML
    private Label Order_ID;
    @FXML
    private Label Order_Type;
    @FXML
    private Label Delivery_date;
    @FXML
    private Label Order_Status;
    @FXML
    private Label Price;
    @FXML
    private Label paymentStatusLabel; // New Label for the "รอการตรวจสอบชำระเงิน" message

    @FXML
    public Button paymentButton;  // ปุ่มชำระเงิน

    @FXML
    public Button confirmReceiptProduct; // ปุ่มยืนยันการรับสินค้า


    public void setOrderData(Order order) {
        Order_ID.setText(order.getOrder_ID());
        Order_Type.setText(order.getOrder_Type());
        Delivery_date.setText(order.getDelivery_date());
        Order_Status.setText(order.getOrderStatus());
        Price.setText(String.valueOf(order.getOutstanding_Balance()));

        // ซ่อน/ปิดการใช้งานปุ่มตามสถานะ
        // สถานะ 0: คำสั่งซื้อใหม่ที่ยังไม่ได้ทำการชำระเงิน
        if (order.getOrder_Status() == 0) {
            paymentButton.setVisible(false);  // ไม่แสดงปุ่มชำระเงิน
            confirmReceiptProduct.setVisible(false);  // ไม่แสดงปุ่มยืนยันการรับสินค้า
        }
        // สถานะที่มีการชำระเงินแล้วหรือกำลังรอการตรวจสอบการชำระเงิน
        else if (order.getOrder_Status() == 1 ||order.getOrder_Status() == 5 && "สั่งซื้อ".equals(order.getOrder_Type())|| order.getOrder_Status() == 6 || order.getOrder_Status() == 4 && "สั่งซื้อ".equals(order.getOrder_Type())|| order.getOrder_Status() == 7 && "สั่งจอง".equals(order.getOrder_Type())) {
            paymentButton.setDisable(true);  // ปิดปุ่มชำระเงิน
            // เปิดใช้งานปุ่มยืนยันการรับสินค้า ถ้าสถานะเป็น 4 หรือ 6 (หมายถึงการยืนยันการรับสินค้า)
            confirmReceiptProduct.setDisable(order.getOrder_Status() != 4 && order.getOrder_Status() != 6);
        } else {
            paymentButton.setVisible(true);  // แสดงปุ่มชำระเงิน
            confirmReceiptProduct.setDisable(true);  // ปิดปุ่มยืนยันการรับสินค้าในกรณีนี้
        }

        // ตรวจสอบ Payment_Image และแสดงข้อความที่เหมาะสม
        if (InvoiceDataConnect.isPaymentImageExists(order.getOrder_ID())) {
            paymentStatusLabel.setText("แนบหลักฐานแล้ว");
        } else {
            paymentStatusLabel.setText("");  // หากไม่มีการแนบหลักฐานการชำระเงิน
        }
    }



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
    }

    // Method สำหรับเปิดหน้าต่าง paymentOrderWindow
    private void openPaymentWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/paymentOrderWindow.fxml"));
        Parent root = loader.load();

        paymentOrderWindowController controller = loader.getController();
        controller.setOrderID(Order_ID.getText());
        controller.setOrderDetails(Order_Type.getText(), Integer.parseInt(Price.getText()));

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Payment Confirmation");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    // Method สำหรับเปิดหน้าต่าง paymentOrderWindow
    private void openConfirmReceiptProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmReceiptProduct.fxml"));
        Parent root = loader.load();

        confirmReceiptProductController controller = loader.getController();
        controller.setOrderData(Order_ID.getText(), Order_Type.getText()); // ส่ง Order_ID และ Order_Type

        // สร้าง Stage สำหรับหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Order Receipt Confirmation");
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal
        stage.showAndWait();  // แสดงหน้าต่าง
    }


}
