package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class salerCheckProductPageController {

    @FXML
    private Button backButton;
    @FXML
    private Button confirmButton;

    @FXML
    public void initialize() {
        // กำหนดการทำงานของปุ่มย้อนกลับ
        backButton.setOnAction(event -> {
            try {
                backToOrderPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // กำหนดการทำงานของปุ่มยืนยันคำสั่งซื้อ
        confirmButton.setOnAction(event -> {
            try {
                openConfirmOrderWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // เมธอดเพื่อกลับไปยังหน้า salerCheckOrderPageController
    private void backToOrderPage() throws IOException {
        // โหลดหน้า salerCheckOrderPage.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/salerCheckOrderPage.fxml"));
        Parent orderPage = loader.load();

        // ตั้งค่า Scene ใหม่เพื่อแสดงหน้า order page
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(orderPage);
        stage.setScene(scene);
        stage.show();
    }
    // เมธอดสำหรับเปิดหน้าต่างยืนยันคำสั่งซื้อ
    private void openConfirmOrderWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmOrderAcceptWindow.fxml"));
        Parent confirmOrderWindow = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(confirmOrderWindow));
        stage.showAndWait(); // รอจนกว่าจะปิดหน้าต่าง
    }
}
