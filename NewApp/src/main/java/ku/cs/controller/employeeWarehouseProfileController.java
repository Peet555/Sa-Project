package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.connect.DatabaseConnect;
import ku.cs.connect.LoginConnect;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class employeeWarehouseProfileController {

    @FXML
    private Label employeeName; // name

    @FXML
    private Label employeePhone; // phone

    @FXML
    private Label employeeAddress; // address

    @FXML
    private Button profileButton ; // profile

    @FXML
    private Button editProfileButton ; // edit profile


    @FXML
    public void initialize() throws IOException {
        loadEmployeeData();
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

    private void loadEmployeeData() {
        Connection connection = DatabaseConnect.getConnection();
        String employeeId = LoginConnect.getCurrentUser().getID(); // ใช้ ID จาก currentUser ที่ล็อกอิน
        String query = "SELECT Employee_Name, Employee_Address, Employee_Phone_number FROM employee WHERE Employee_ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employeeId); // ใช้ Employee_ID ของพนักงานที่ล็อกอินอยู่

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                employeeName.setText(resultSet.getString("Employee_Name"));
                employeePhone.setText(resultSet.getString("Employee_Phone_number"));
                employeeAddress.setText(resultSet.getString("Employee_Address"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to load employee data: " + e.getMessage());
        } finally {
            DatabaseConnect.closeConnection();
        }
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
