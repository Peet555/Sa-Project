package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.Product;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class productModelItemController {

    @FXML
    public ImageView imageProduct;
    @FXML
    public Label productName;
    @FXML
    public Label productPrice;

    public void setProductDetails(Product product) {
        productName.setText(product.getProduct_Name());
        productPrice.setText(String.format("%.2f", product.getPrice()));

        if (product.getImage() != null) {
            InputStream imageStream = new ByteArrayInputStream(product.getProduct_Image_Byte());
            imageProduct.setImage(new Image(imageStream));
        }
    }
}
