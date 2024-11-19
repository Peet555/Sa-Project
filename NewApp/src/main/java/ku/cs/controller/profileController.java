package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.connect.CustomerProfileConnect;
import ku.cs.models.Customer;
import ku.cs.services.FXRouter;

import java.io.IOException;

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

    public void loadCustomerData() {
        CustomerProfileConnect connect = new CustomerProfileConnect();
        Customer customer = connect.profile();
        customerName.setText(customer.getName());
        customerEmail.setText(customer.getEmail());
        customerAddress.setText(customer.getAddress());
        customerPhone.setText(customer.getPhoneNumber());

    }


    private void openEditProfileWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/editProfileWindow.fxml"));
        Parent root = loader.load();
        editProfileWindowController controller = loader.getController();
        controller.setProfileController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

}
