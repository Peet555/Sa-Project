package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;
import java.io.IOException;


public class profileController {
    @FXML
    public Label profileName;

    @FXML
    public Label profileID;

    @FXML
    public Label profilePhone;

    @FXML
    public Label profileAddress;

    @FXML
    public ImageView profileImageView;

    @FXML
    public Button homeButton;

    @FXML
    public Button cartButton;

    @FXML
    public Button orderHistoryButton;

    @FXML
    public Button profileButton ;


    @FXML
    public void initialize() throws IOException {

        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("Homepage"); // เปลี่ยนไปหน้า HomePage
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
                FXRouter.goTo("CustomerOrderList");
            } catch (IOException e) {
                System.err.println("Cannot go to cart");
            }
        });

        orderHistoryButton.setOnAction(event -> {
            try {
                FXRouter.goTo("CustomerOrderHistory");
            } catch (IOException e) {
                System.err.println("Cannot go to order history");
            }
        });

    }
}

