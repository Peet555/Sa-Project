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

public class employeeSellerProfileController {

    @FXML
    private Label employeeSellerUserName; // username

    @FXML
    private Label employeeSellerName; // name

    @FXML
    private Label employeeSellerPhone; // phone

    @FXML
    private Label employeeSellerAddress; // address

    @FXML
    private Button profileButton ; // profile

    @FXML
    private Button editProfileButton ; // edit profile


    @FXML
    public void initialize() throws IOException {
        // กำหนดการทำงานของปุ่มชำระเงิน
        editProfileButton.setOnAction(event -> {
            try {
                openEditEmployeeProfileWindow();
            } catch (IOException e) {
                System.err.println("Error opening payment window: " + e.getMessage());
            }
        });


        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("employeeSellerProfile"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });
        editProfileButton.setOnAction(event -> {
            try {
                openEditEmployeeProfileWindow();
            } catch (IOException e) {
                System.err.println("Error opening payment window: " + e.getMessage());
            }
        });

    }

    @FXML
    public void goVerifyPayment() {
        try {
            FXRouter.goTo("verifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    public void goCheckOrder(){
        try {
            FXRouter.goTo("salerCheckOrder");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Method สำหรับเปิดหน้าต่าง paymentOrderWindow
    private void openEditEmployeeProfileWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/editEmployeeSellerProfileWindow.fxml"));
        Parent root = loader.load();

        // สร้าง Stage สำหรับหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal (โฟกัสเฉพาะหน้าต่างนี้)
        stage.showAndWait();  // แสดงหน้าต่าง
    }
}
