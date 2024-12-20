package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.Product;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class productModelItemController {

    private String productId;
    @FXML
    public ImageView imageProduct;
    @FXML
    public Label productName;
    @FXML
    public Label productPrice;

    public void setProductDetails(Product product) {
        this.productId = product.getProduct_ID(); // เก็บ Product_ID ที่นี่
        productName.setText(product.getProduct_Name());
        productPrice.setText(String.format("%d", product.getPrice()));

        if (product.getProduct_Image_Byte() != null) {
            InputStream imageStream = new ByteArrayInputStream(product.getProduct_Image_Byte());
            imageProduct.setImage(new Image(imageStream));
        }
    }

}
