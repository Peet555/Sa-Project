package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ku.cs.connect.DatabaseConnect;
import ku.cs.connect.ProductModelConnect;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class productModelController {

    public GridPane gridPane;
    private HBox currentSelectedProduct = null; // ใช้เพื่อเก็บ item ที่ถูกเลือก
    private ProductModelConnect productConnect = new ProductModelConnect();

    @FXML
    private Button homeButton; // เชื่อมต่อกับปุ่ม "Home" จาก FXML

    @FXML
    public Button profileButton ;

    @FXML
    public Button cartButton ;

    @FXML
    public Button orderHistoryButton ;
    private String selectedType;

    @FXML
    public void initialize() throws IOException {
        loadProductsByType((String) FXRouter.getData());

        // กดปุ่ม "Home" แล้วกลับไปหน้า Home Page
        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("homePage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println();

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


    public void loadProductsByType(String typeName) {
        List<Product> products = productConnect.loadProductsByType(typeName);

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/productModelItem.fxml"));
                HBox productItemBox = loader.load();

                // Set product details via setProductDetails method
                productModelItemController controller = loader.getController();
                controller.setProductDetails(product);

                // Add event listeners for interaction
                addEventListeners(productItemBox,product);

                // Calculate row and column based on index
                int row = i / 4;
                int col = i % 4;

                // Add product item to gridPane
                gridPane.add(productItemBox, col, row);

            } catch (IOException e) {
                System.err.println("Error loading product item: " + e.getMessage());
            }
        }
    }
    private void addEventListeners(HBox productItemBox,Product product) {
        productItemBox.setOnMousePressed(event -> {
            if (currentSelectedProduct != null) {
                currentSelectedProduct.setStyle("");  // Clear previous selection
            }
            productItemBox.setStyle("-fx-background-color: lightblue;");
            currentSelectedProduct = productItemBox;
        });

        productItemBox.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                System.out.println("Double clicked on item");
                try {
                    FXRouter.goTo("productDetails",product.getProduct_ID());
                } catch (IOException e) {
                    System.err.println("Failed to go to product details: " + e.getMessage());
                }
            }
        });
    }
}
