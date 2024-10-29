package ku.cs.controller;

import javafx.fxml.FXML;
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

    // Method to set the order ID
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    // Method to save the selected delivery date to the database
    private void saveDeliveryDate() {
        LocalDate selectedDate = specifyDate.getValue();
        if (selectedDate != null) {
            String sql = "UPDATE `order` SET Delivery_date = ? WHERE Order_ID = ?";
            try (Connection connection = DatabaseConnect.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDate(1, java.sql.Date.valueOf(selectedDate)); // Set the delivery date
                statement.setString(2, orderId); // Set the order ID
                statement.executeUpdate(); // Execute the update
            } catch (SQLException e) {
                System.err.println("Error saving delivery date: " + e.getMessage());
            }
        } else {
            System.out.println("No date selected.");
        }
    }

    // Method to close the window
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
