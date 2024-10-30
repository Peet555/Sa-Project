package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.Button;

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
            // ลบไอเท็มนี้ออกจาก VBox
            parentVBox.getChildren().remove((Node) deleteProduct.getParent());
        });
    }
}