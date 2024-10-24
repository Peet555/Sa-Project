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


public class profileController {

    @FXML
    public Label customerUserName; // username

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
    public Button profileButton ; // profile

    @FXML
    public Button editProfileButton ; // edit profile


    @FXML
    public void initialize() throws IOException {
            // กำหนดการทำงานของปุ่มชำระเงิน
        editProfileButton.setOnAction(event -> {
                try {
                    openEditProfileWindow();
                } catch (IOException e) {
                    System.err.println("Error opening payment window: " + e.getMessage());
                }
            });


        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("homePage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to Homepage");
            }
        });
        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("profile"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });

        cartButton.setOnAction(event -> {
            try {
                FXRouter.goTo("customerOrderList"); // เปลี่ยนไปหน้า Cart
            } catch (IOException e) {
                System.err.println("Cannot go to cart");
            }
        });

        orderHistoryButton.setOnAction(event -> {
            try {
                FXRouter.goTo("customerOrderHistory"); // เปลี่ยนไปหน้า Order History
            } catch (IOException e) {
                System.err.println("Cannot go to order history");
            }
        });

    }
    // Method สำหรับเปิดหน้าต่าง paymentOrderWindow
    private void openEditProfileWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/editProfileWindow.fxml"));
        Parent root = loader.load();

        // สร้าง Stage สำหรับหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal (โฟกัสเฉพาะหน้าต่างนี้)
        stage.showAndWait();  // แสดงหน้าต่าง
    }
}

