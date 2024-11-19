package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.connect.InvoiceDataConnect;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class paymentOrderWindowController {

    @FXML
    private Button attachPaymentButton;

    @FXML
    private Button confirmPaymentButton;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label priceDepositLabel;

    @FXML
    private Label fileName;

    private File selectedFile;
    private String orderID; // รับค่า Order_ID จาก controller ก่อนหน้า
    private String orderType;
    private int orderPrice;

    public void initialize() {
        confirmPaymentButton.setDisable(true);

        attachPaymentButton.setOnAction(event -> openFileChooser());
        confirmPaymentButton.setOnAction(event -> {
            try {
                InvoiceDataConnect.savePaymentProof(orderID, selectedFile);
                showConfirmationAlert();
                closeWindow();
            } catch (IOException | SQLException e) {
                System.err.println("Error saving payment proof: " + e.getMessage());
            }
        });
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setOrderDetails(String orderType, int orderPrice) {
        this.orderType = orderType;
        this.orderPrice = orderPrice;
        totalPriceLabel.setText(String.valueOf(orderPrice));

        if ("สั่งจอง".equals(orderType)) {
            int deposit = (int) (orderPrice * 0.3); // คำนวณค่ามัดจำ 30%
            priceDepositLabel.setText(String.valueOf(deposit));
        } else {
            priceDepositLabel.setText("ไม่ต้องการค่ามัดจำ");
        }
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("เลือกไฟล์หลักฐานการชำระเงิน");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) attachPaymentButton.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            fileName.setText(selectedFile.getName());
            confirmPaymentButton.setDisable(false);
        } else {
            fileName.setText("ไม่มีไฟล์ถูกเลือก");
            confirmPaymentButton.setDisable(true);
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmPaymentButton.getScene().getWindow();
        stage.close();
    }

    private void showConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ยืนยันการชำระเงิน");
        alert.setHeaderText(null);
        alert.setContentText("ชำระเงินเสร็จสิ้น โปรดรอการตรวจสอบ");
        alert.showAndWait();
    }
}
