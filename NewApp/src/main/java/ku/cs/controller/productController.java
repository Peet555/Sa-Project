package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.connect.ProductInfoConnect;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class productController {

    @FXML
    public Button buttonOrderList;

    @FXML
    public VBox vBox;

    @FXML
    public Button homeButton;

    @FXML
    public Button profileButton ;

    @FXML
    public Button cartButton ;

    @FXML
    public Button orderHistoryButton ;
    private Product product;
    @FXML

    private ProductInfoConnect productInfoConnect;
    public void initialize() throws IOException {
        String id = (String) FXRouter.getData(); // รับ Product_ID
        productInfoConnect = new ProductInfoConnect();

        product = productInfoConnect.productInfo(id); // โหลดข้อมูลผลิตภัณฑ์

        if (product != null) {
            product.setProduct_ID(id); // ตั้ง Product_ID ใน product
            addTypeProductItem(1);
        } else {
            System.out.println("Product not found for ID: " + id);
        }

        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("homePage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to Homepage");
            }
        });

        // เปลี่ยนจากการไปที่หน้าใหม่เป็นเปิด Modal ของ productQualityWindow
        buttonOrderList.setOnAction(event -> {
            try {
                openProductQualityWindow(product);  // เรียกใช้ method เปิด Modal
            } catch (IOException e) {
                System.err.println("Cannot go to productQualityWindow");
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

    public void addTypeProductItem(int index) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/view/productItem.fxml"));
        VBox p = fxmlLoader.load();
        productItemController controller = fxmlLoader.getController();
        controller.productName.setText(product.getProduct_Name());
        controller.productPrice.setText(String.valueOf(product.getPrice()));
        controller.productQuantity.setText(String.valueOf(product.getQuantity()));
        controller.productDetails.setText(product.getDescription());
        InputStream imageStream = new ByteArrayInputStream(product.getProduct_Image_Byte());
        controller.imageProduct.setImage(new Image(imageStream));

        System.out.println("Product ID in Product Controller: " + product.getProduct_ID()); // แสดง Product_ID

        vBox.getChildren().add(p);
    }


    // Method เพื่อเปิดหน้าต่าง modal ที่เป็น ProductQualityWindow
    private void openProductQualityWindow(Product product) throws IOException {
        // โหลดหน้าต่าง Modal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/productQualityWindow.fxml"));
        VBox vbox = loader.load();
        // เข้าถึง Controller ของ productQualityWindow
        productQualityWindowController controller = loader.getController();
        controller.setProduct(product);  // ส่ง product ไปยัง Controller
        // สร้าง Stage ใหม่เพื่อแสดง Modal
        Stage modalStage = new Stage();
        modalStage.setTitle("Product Quality Window");
        modalStage.initModality(Modality.APPLICATION_MODAL);  // ทำให้หน้าต่างนี้เป็น Modal
        modalStage.setScene(new Scene(vbox));  // กำหนด Scene ให้กับ Stage ใหม่
        modalStage.showAndWait();  // แสดง Modal และรอให้ปิดก่อนกลับมาทำงานต่อ
    }
}
