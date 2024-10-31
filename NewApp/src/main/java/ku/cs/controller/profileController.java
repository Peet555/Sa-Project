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
import ku.cs.models.Customer;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class profileController {

    @FXML
    public Label customerName; // name

    @FXML
    public Label customerEmail; // email

    @FXML
    public Label customerPhone; // phone

    @FXML
    public Label customerAddress; // address

    @FXML
    public Button homeButton; // home

    @FXML
    public Button cartButton; // cart

    @FXML
    public Button orderHistoryButton; // order history

    @FXML
    public Button profileButton; // profile

    @FXML
    public Button editProfileButton; // edit profile

    @FXML
    public void initialize() throws IOException {
        loadCustomerData();
        // ตั้งค่าปุ่มต่าง ๆ
        editProfileButton.setOnAction(event -> {
            try {
                openEditProfileWindow();
            } catch (IOException e) {
                System.err.println("Error opening payment window: " + e.getMessage());
            }
        });

        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("homePage");
            } catch (IOException e) {
                System.err.println("Cannot go to Homepage");
            }
        });

        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("profile");
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });

        cartButton.setOnAction(event -> {
            try {
                FXRouter.goTo("customerOrderList");
            } catch (IOException e) {
                System.err.println("Cannot go to cart");
            }
        });

        orderHistoryButton.setOnAction(event -> {
            try {
                FXRouter.goTo("customerOrderHistory");
            } catch (IOException e) {
                System.err.println("Cannot go to order history");
            }
        });
    }

    private void loadCustomerData() {
        Connection connection = DatabaseConnect.getConnection();
        String customerId = LoginConnect.getCurrentUser().getID(); // ใช้ ID จาก currentUser ที่ล็อกอิน
        String query = "SELECT Name, Email, Customer_Address, Customer_Phone_number FROM customer WHERE Customer_ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerId); // ใช้ Customer_ID ของผู้ใช้ที่ล็อกอินอยู่

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customerName.setText(resultSet.getString("Name"));
                customerEmail.setText(resultSet.getString("Email"));
                customerAddress.setText(resultSet.getString("Customer_Address"));
                customerPhone.setText(resultSet.getString("Customer_Phone_number"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to load customer data: " + e.getMessage());
        } finally {
            DatabaseConnect.closeConnection();
        }
    }


    private void openEditProfileWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/editProfileWindow.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
