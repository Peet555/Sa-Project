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

public class employeeWarehouseProfileController {
    @FXML
    private Label employeeWarehouseUserName; // username

    @FXML
    private Label employeeWarehouseName; // name

    @FXML
    private Label employeeWarehousePhone; // phone

    @FXML
    private Label employeeWarehouseAddress; // address

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
                FXRouter.goTo("employeeWarehouseProfile"); // เปลี่ยนไปหน้า HomePage
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


    // Method สำหรับเปิดหน้าต่าง paymentOrderWindow
    private void openEditEmployeeProfileWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/editEmployeeWarehouseProfileWindow.fxml"));
        Parent root = loader.load();

        // สร้าง Stage สำหรับหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal (โฟกัสเฉพาะหน้าต่างนี้)
        stage.showAndWait();  // แสดงหน้าต่าง
    }

    @FXML
    public void goOrder() {
        try {
            FXRouter.goTo("orderStock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goDeliver() {
        try {
            FXRouter.goTo("delivery");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void goStock(){
        try {
            FXRouter.goTo("stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
