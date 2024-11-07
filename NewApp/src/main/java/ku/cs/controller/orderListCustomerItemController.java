package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Button;
import ku.cs.models.Product;
import ku.cs.services.OrderManager;

import java.util.List;

public class orderListCustomerItemController {

    @FXML
    public Label productNameLabel;

    @FXML
    public Label quantityLabel;

    @FXML
    public Label priceLabel;

    @FXML
    public ImageView productImageView;

    @FXML
    public Button deleteProduct;

    private VBox parentVBox;

    public void setParentVBox(VBox parentVBox) {
        this.parentVBox = parentVBox;
    }

    @FXML
    public void initialize() {
        deleteProduct.setOnAction(event -> {
            // Get the product name from the label
            String productName = productNameLabel.getText();
            // Get the list of products from the OrderManager
            List<Product> temporaryProductList = OrderManager.getInstance().getTemporaryProductList();

            // Find the product in the list to remove
            for (Product product : temporaryProductList) {
                if (product.getProduct_Name().equals(productName)) {
                    OrderManager.getInstance().removeProduct(product); // Remove from OrderManager
                    break; // Exit the loop after finding and removing the product
                }
            }

            // Remove this item from VBox
            parentVBox.getChildren().remove((Node) deleteProduct.getParent());

        });
    }

}