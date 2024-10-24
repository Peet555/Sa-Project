package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public void initialize() throws IOException {

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
}

