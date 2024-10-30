package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class orderListCustomerController {
    private static List<Product> productList = new ArrayList<>();
    @FXML
    public VBox vBox;

    @FXML
    public Button handleContinueShopping;

    @FXML
    public Button handleConfirmOrder;  // ปุ่มสั่งซื้อ

    @FXML
    public Button handleConfirmPreOrder;  // ปุ่มสั่งจอง

    @FXML
    public Button profileButton ;

    @FXML
    public Button cartButton ;

    @FXML
    public Button orderHistoryButton ;

    @FXML
    public Button homeButton ;

    @FXML
    public Label cannotClickOrderButton;

    @FXML
    public Label totalOrderPrice;

    @FXML
    public void initialize() throws IOException {
        Object[] object  = (Object[])FXRouter.getData();
        if(object != null) {
            Product product = (Product) object[0];
            String quantity = (String) object[1];
            addProductToOrderList(product);
        }
        // ตั้งค่า ScrollPane ให้สามารถเลื่อนดู VBox ได้
        // scrollPane.setContent(vBox);  // ใช้ได้ในกรณีที่มี ScrollPane

        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("homePage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to Homepage");
            }
        });

        handleContinueShopping.setOnAction(event -> {
            try {
                FXRouter.goTo("homePage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println();
            }
        });

        // เมื่อกดปุ่ม "สั่งซื้อ"
        handleConfirmOrder.setOnAction(event -> {
            try {
                // เปิดหน้าต่างยืนยันคำสั่งซื้อ (orderConfirmationWindowController)
                openOrderConfirmationWindow();
            } catch (IOException e) {
                System.err.println("Cannot open order confirmation window.");
            }
        });

        // เมื่อกดปุ่ม "สั่งจอง"
        handleConfirmPreOrder.setOnAction(event -> {
            try {
                // เปิดหน้าต่างยืนยันคำสั่งซื้อ (preOrderConfirmationWindowController)
                openPreOrderConfirmationWindow();
            } catch (IOException e) {
                System.err.println("Cannot open order confirmation window.");
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

    // Method สำหรับเปิดหน้าต่าง orderConfirmationWindowController
    private void openOrderConfirmationWindow() throws IOException {
        // โหลด FXML ของหน้าต่าง orderConfirmationWindow
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/orderConfirmationWindow.fxml"));
        VBox root = loader.load();

        // สร้าง Stage ใหม่เพื่อเปิดหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setTitle("ยืนยันคำสั่งซื้อ");
        stage.initModality(Modality.APPLICATION_MODAL);  // ทำให้หน้าต่างนี้เป็น Modal
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Method สำหรับเปิดหน้าต่าง preOrderConfirmationWindowController
    private void openPreOrderConfirmationWindow() throws IOException {
        // โหลด FXML ของหน้าต่าง preOrderConfirmationWindow
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/preOrderConfirmationWindows.fxml"));
        VBox root = loader.load();

        // สร้าง Stage ใหม่เพื่อเปิดหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setTitle("ยืนยันคำสั่งจอง");
        stage.initModality(Modality.APPLICATION_MODAL);  // ทำให้หน้าต่างนี้เป็น Modal
        stage.setScene(new Scene(root));
        stage.show();
    }

    // GPT
    // เมธอดเพื่อเพิ่มสินค้าในรายการ orderList
    public void addProductToOrderList(Product product) {
        productList.add(product);
        loadProducts();
        displayOrderList();  // แสดงรายการสินค้าใน vBox
    }

    // เมธอดเพื่อแสดงสินค้าทั้งหมดใน orderList
    private void displayOrderList() {
        vBox.getChildren().clear();
        for (Product product : productList) {
            // เพิ่มรายการสินค้าแต่ละชิ้นลงใน vBox
            Label label = new Label(product.getProduct_Name() + " - $" + product.getPrice());
            vBox.getChildren().add(label);
        }
    }
    private void loadProducts() {
        for (Product product : productList) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/orderListCustomerItem.fxml"));
                VBox productItemBox = loader.load();

                orderListCustomerItemController itemController = loader.getController();
                itemController.productNameLabel.setText(product.getProduct_Name());
                itemController.quantityLabel.setText(String.valueOf(product.getQuantity()));
                itemController.priceLabel.setText(String.format("$%d", product.getPrice()));

                // Convert byte array to Image and set to ImageView
                ByteArrayInputStream bis = new ByteArrayInputStream(product.getProduct_Image_Byte());
                Image image = new Image(bis);
                itemController.productImageView.setImage(image);

                // ส่ง parent VBox ให้กับ itemController เพื่อให้สามารถลบตัวเองได้
                itemController.setParentVBox(vBox);

                // เพิ่ม HBox ของสินค้าแต่ละรายการลงใน VBox หลัก
                vBox.getChildren().add(productItemBox);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading product item: " + e.getMessage());
            }
        }
    }

}
