package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.util.UUID;

public class productQualityWindowController {
    private Product product;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField quantity;

    @FXML
    private Button confirmOrder;

    public void setProduct(Product product) {
        this.product = product;
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private String generateOrderID() {
        return UUID.randomUUID().toString(); // สร้าง orderID ชั่วคราว
    }

    @FXML
    public void confirmOrder(ActionEvent event) {
        try {
            String orderID = generateOrderID(); // สร้าง orderID ชั่วคราว
            System.out.println("Sending Product ID: " + product.getProduct_ID()); // แสดง Product_ID ที่จะส่ง
            FXRouter.goTo("orderListPageCustomer", new Object[]{product, quantity.getText(), orderID});
            closeWindow();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot go to orderListPageCustomer");
        }
    }



}
