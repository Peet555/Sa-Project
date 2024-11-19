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
import ku.cs.connect.DatabaseConnect;
import ku.cs.connect.LoginConnect;
import ku.cs.models.Order;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;
import ku.cs.services.OrderManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import ku.cs.connect.OrderConnect;

public class orderListCustomerController {

    private OrderConnect orderConnect = new OrderConnect();

    private List<Product> temporaryProductList = new ArrayList<>();
    private String productId; // ตัวแปรเพื่อเก็บ Product_ID
    @FXML
    public VBox vBox;

    @FXML
    public Button handleContinueShopping, handleConfirmOrder, handleConfirmPreOrder;


    @FXML
    public Button profileButton ;

    @FXML
    public Button cartButton ;

    @FXML
    public Button orderHistoryButton ;

    @FXML
    public Button homeButton ;


    @FXML
    public Label cannotClickOrderButton, totalOrderPrice;

    private static int orderCounter = 0;


    @FXML
    public void initialize() throws IOException {
        Object[] object = (Object[]) FXRouter.getData();
        if (object != null) {
            Product product = (Product) object[0];
            productId = product.getProduct_ID(); // เก็บ Product_ID
            int quantity = Integer.parseInt((String) object[1]); // Get product quantity
            product.setQuantity(quantity); // Set the product quantity
            addProductToTemporaryList(product); // Add product to temporary list
            calculateTotalPrice();
        }

        displayOrderList(); // แสดงรายการสินค้าใน vBox

        // ตรวจสอบสถานะของปุ่ม
        updateOrderButtonState();

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

        handleConfirmOrder.setOnAction(event -> {
            String orderID = generateOrderId(); // สร้าง Order_ID ใหม่
            saveOrder(orderID, "สั่งซื้อ", OrderManager.getInstance().getTemporaryProductList()); // บันทึกออเดอร์
            openConfirmationWindow("สั่งซื้อเสร็จสิ้น", "สั่งซื้อสำเร็จ");
        });

        handleConfirmPreOrder.setOnAction(event -> {
            String orderID = generateOrderId(); // สร้าง Order_ID ใหม่
            saveOrder(orderID, "สั่งจอง", OrderManager.getInstance().getTemporaryProductList()); // บันทึกออเดอร์
            openConfirmationWindow("สั่งจองเสร็จสิ้น", "สั่งจองสำเร็จ");
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

    public String getProductId() {
        return productId; // Getter สำหรับ Product_ID
    }

    // เมธอดเพื่อสร้าง Order_ID แบบสุ่ม
    private String generateOrderId() {
        return "ORD" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        // ตัวอย่างผลลัพธ์: ORDABC12345
    }

    // เมธอดสำหรับตรวจสอบสถานะของปุ่ม
    private void updateOrderButtonState() {
        boolean hasItems = !vBox.getChildren().isEmpty();
        handleConfirmOrder.setDisable(!hasItems);
        handleConfirmPreOrder.setDisable(!hasItems);

        if (!hasItems) {
            cannotClickOrderButton.setText("กรุณาเพิ่มสินค้าก่อนกดสั่งซื้อหรือสั่งจอง");
        } else {
            cannotClickOrderButton.setText("");
        }
    }


    private void openConfirmationWindow(String message, String title) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle(title);

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: green;");

        Button closeButton = new Button("ปิด");
        closeButton.setOnAction(e -> {
            dialogStage.close(); // ปิดหน้าต่างยืนยัน
            try {
                // เปลี่ยนกลับไปยังหน้า orderListCustomerController
                FXRouter.goTo("customerOrderHistory");
            } catch (IOException ex) {
                System.err.println("Cannot navigate to orderListPageCustomer: " + ex.getMessage());
            }
        });

        VBox vbox = new VBox(10, messageLabel, closeButton);
        vbox.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);
        dialogStage.show();
    }



    // Method สำหรับเปิดหน้าต่าง preOrderConfirmationWindowController
    private void openPreOrderConfirmationWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/preOrderConfirmationWindows.fxml"));
        VBox root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("ยืนยันคำสั่งจอง");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }
    // GPT
    // เมธอดเพื่อเพิ่มสินค้าในรายการ orderList

    // Add selected product to temporary list
    public void addProductToTemporaryList(Product product) {
        OrderManager.getInstance().addProduct(product); // Add to OrderManager
        displayOrderList(); // อัปเดตรายการแสดงสินค้า
    }

    private void saveOrder(String orderID, String actionType, List<Product> productList) {
        int totalPrice = 0;
        for (Product product : productList) {
            totalPrice += product.getPrice() * product.getQuantity();
        }

        String customerId = LoginConnect.getCurrentUser().getID();
        Order order = new Order(orderID, null, customerId, 1, new Timestamp(System.currentTimeMillis()), totalPrice, actionType, null);

        orderConnect.saveOrderToDatabase(order); // บันทึกออเดอร์โดยใช้ OrderConnect
        orderConnect.saveOrderLineToDatabase(orderID, productList); // บันทึก Order Line โดยใช้ OrderConnect
    }




    // Method to calculate total price of the order
    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (Product product : OrderManager.getInstance().getTemporaryProductList()) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        totalOrderPrice.setText(String.format("%d", totalPrice)); // Update total price label
        return totalPrice;
    }

    private void displayOrderList() {
        vBox.getChildren().clear();
        List<Product> temporaryProductList = OrderManager.getInstance().getTemporaryProductList();
        for (Product product : temporaryProductList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/orderListCustomerItem.fxml"));
                VBox productItemBox = loader.load();
                orderListCustomerItemController itemController = loader.getController();

                itemController.productNameLabel.setText(product.getProduct_Name());
                itemController.quantityLabel.setText(String.valueOf(product.getQuantity()));
                itemController.priceLabel.setText(String.format("%d", product.getPrice()));

                ByteArrayInputStream bis = new ByteArrayInputStream(product.getProduct_Image_Byte());
                Image image = new Image(bis);
                itemController.productImageView.setImage(image);

                itemController.setParentVBox(vBox);
                vBox.getChildren().add(productItemBox);
            } catch (IOException e) {
                System.err.println("Error loading product item: " + e.getMessage());
            }
        }

        // ตรวจสอบสถานะปุ่มหลังจากแสดงรายการสินค้า
        updateOrderButtonState();
    }

}