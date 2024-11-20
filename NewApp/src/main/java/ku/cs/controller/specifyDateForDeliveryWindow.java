package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DateCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import ku.cs.connect.OrderStatusUpdateConnect;

import java.time.LocalDate;

public class specifyDateForDeliveryWindow {

    @FXML
    private Button confirmButton, cancelButton;
    @FXML
    private DatePicker specifyDate;

    private String orderId; // To hold the order ID passed from deliveryPrepareController
    private String orderType; // เพื่อเก็บประเภทของคำสั่งซื้อ

    private final OrderStatusUpdateConnect orderStatusUpdateConnect = new OrderStatusUpdateConnect();

    // Method to set the order ID and type
    public void setOrderDetails(String orderId, String orderType) {
        this.orderId = orderId;
        this.orderType = orderType;
    }

    @FXML
    public void initialize() {
        // Set the DatePicker to disable past dates
        specifyDate.setDayCellFactory(getDayCellFactory());

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
            int updatedStatus = orderType.equals("สั่งซื้อ") ? 4 : 6; // กำหนดสถานะใหม่ตามประเภทคำสั่งซื้อ
            boolean isSuccess = orderStatusUpdateConnect.updateDeliveryDateAndStatus(orderId, selectedDate, updatedStatus);

            if (isSuccess) {
                // Show success message
                showConfirmationMessage();
            } else {
                System.err.println("Failed to update delivery date and order status.");
            }
        } else {
            System.out.println("No date selected.");
        }
    }

    // Method to get DayCellFactory to disable past dates
    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                // Disable past dates
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;"); // Optional: Change background for disabled dates
                }
            }
        };
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
