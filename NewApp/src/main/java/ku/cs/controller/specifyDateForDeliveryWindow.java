package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import ku.cs.connect.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class specifyDateForDeliveryWindow {

    @FXML
    private Button confirmButton, cancelButton;
    @FXML
    private DatePicker specifyDate;

    private String orderId; // To hold the order ID passed from deliveryPrepareController
    private String orderType; // เพื่อเก็บประเภทของคำสั่งซื้อ

    // Method to set the order ID and type
    public void setOrderDetails(String orderId, String orderType) {
        this.orderId = orderId;
        this.orderType = orderType;
    }

    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> {
            saveDeliveryDate();
            closeWindow();
        });

        cancelButton.setOnAction(event -> {
            closeWindow();
        });
    }

    // Method to save the selected delivery date and update Order_Status
    private void saveDeliveryDate() {
        LocalDate selectedDate = specifyDate.getValue();
        if (selectedDate != null) {
            String sql = "UPDATE `order` SET Delivery_date = ?, Order_Status = ? WHERE Order_ID = ?";
            try (Connection connection = DatabaseConnect.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                // Set the delivery date
                statement.setDate(1, java.sql.Date.valueOf(selectedDate));

                // Set the appropriate status based on the order type
                int updatedStatus = orderType.equals("สั่งซื้อ") ? 4 : 6;
                statement.setInt(2, updatedStatus);

                statement.setString(3, orderId); // Set the order ID
                statement.executeUpdate(); // Execute the update

                // Show success message
                showConfirmationMessage();

            } catch (SQLException e) {
                System.err.println("Error saving delivery date and updating status: " + e.getMessage());
            }
        } else {
            System.out.println("No date selected.");
        }
    }

    // Method to show a confirmation message
    private void showConfirmationMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("การดำเนินการสำเร็จ");
        alert.setHeaderText(null);
        alert.setContentText("ระบุวันจัดส่งเรียบร้อย");
        alert.showAndWait();
    }

    // Method to close the window
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
