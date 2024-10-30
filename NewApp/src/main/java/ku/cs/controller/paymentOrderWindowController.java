package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class paymentOrderWindowController {

    @FXML
    private Button attachPaymentButton;

    @FXML
    private Button confirmPaymentButton; // อ้างอิงถึงปุ่มยืนยันการชำระเงิน

    @FXML
    private Label fileName; // อ้างอิง Label ที่จะแสดงชื่อไฟล์

    private File selectedFile;
    private double totalPrice;
    private double priceDeposit;

    @FXML
    public void initialize() {
        // ปิดการใช้งานปุ่มยืนยันการชำระเงินในตอนเริ่มต้น
        confirmPaymentButton.setDisable(true);

        // เมื่อกดปุ่ม "แนบหลักฐานการชำระเงิน" จะเปิด FileChooser
        attachPaymentButton.setOnAction(event -> {
            openFileChooser();
        });

        // เมื่อกดปุ่ม "ยืนยันการชำระเงิน" จะปิดหน้าต่าง
        confirmPaymentButton.setOnAction(event -> {
            closeWindow();
        });
    }

    // เมธอดสำหรับเปิด FileChooser เพื่อเลือกไฟล์
    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("เลือกไฟล์หลักฐานการชำระเงิน");

        // กำหนดประเภทของไฟล์ที่สามารถเลือกได้
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        // เปิดหน้าต่างเลือกไฟล์
        Stage stage = (Stage) attachPaymentButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // ตรวจสอบว่าผู้ใช้ได้เลือกไฟล์หรือไม่
        if (selectedFile != null) {
            // อัปเดต Label เพื่อแสดงชื่อไฟล์ที่เลือก
            fileName.setText(selectedFile.getName());

            // เปิดใช้งานปุ่มยืนยันการชำระเงินเมื่อเลือกไฟล์แล้ว
            confirmPaymentButton.setDisable(false);
        } else {
            // ถ้าไม่ได้เลือกไฟล์ ให้แสดงข้อความที่เหมาะสม
            fileName.setText("ไม่มีไฟล์ถูกเลือก");

            // ปิดการใช้งานปุ่มยืนยันการชำระเงินถ้าไม่ได้เลือกไฟล์
            confirmPaymentButton.setDisable(true);
        }
    }
    // เมธอดสำหรับปิดหน้าต่าง
    private void closeWindow() {
        Stage stage = (Stage) confirmPaymentButton.getScene().getWindow();
        stage.close();
    }
}
