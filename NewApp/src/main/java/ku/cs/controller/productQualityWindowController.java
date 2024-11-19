package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import ku.cs.connect.ProductInfoConnect;
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
            int quantityToReduce = Integer.parseInt(quantity.getText());
            ProductInfoConnect productInfoConnect = new ProductInfoConnect();

            // ลดจำนวนสินค้าในฐานข้อมูล
            boolean isUpdated = productInfoConnect.updateProductQuantity(product.getProduct_ID(), quantityToReduce);

            if (isUpdated) {
                System.out.println("Quantity updated successfully!");

                // สร้าง orderID และส่งข้อมูลไปหน้าอื่น
                String orderID = generateOrderID();
                FXRouter.goTo("orderListPageCustomer", new Object[]{product, quantity.getText(), orderID});
                closeWindow();
            } else {
                System.out.println("Failed to update quantity or insufficient stock.");
                // แสดงข้อความแจ้งเตือนเมื่อจำนวนสินค้าไม่พอ
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid quantity entered: " + quantity.getText());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot go to orderListPageCustomer");
        }
    }


}
