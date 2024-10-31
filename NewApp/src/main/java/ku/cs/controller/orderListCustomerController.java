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

public class orderListCustomerController {

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
        }

        displayOrderList(); // แสดงรายการสินค้าใน vBox

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
            String orderID = generateOrderId(); // สร้าง Order_ID ใหม่
            saveOrder(orderID, "สั่งซื้อ", OrderManager.getInstance().getTemporaryProductList()); // บันทึกออเดอร์
        });

// เมื่อกดปุ่ม "สั่งจอง"
        handleConfirmPreOrder.setOnAction(event -> {
            String orderID = generateOrderId(); // สร้าง Order_ID ใหม่
            saveOrder(orderID, "สั่งจอง", OrderManager.getInstance().getTemporaryProductList()); // บันทึกออเดอร์
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

    // เมธอดเพื่อสร้าง Order_ID
    private String generateOrderId() {
        orderCounter++; // เพิ่มลำดับทุกครั้งที่สร้าง Order_ID
        return "ORD" + String.format("%04d", orderCounter); // ตัวอย่างผลลัพธ์: ORD0001
    }

    // Method สำหรับเปิดหน้าต่าง orderConfirmationWindowController
    private void openConfirmationWindow(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/" + fxml));
            VBox root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Cannot open confirmation window.");
        }
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
        // คำนวณ Order_Price
        int totalPrice = 0;
        for (Product product : productList) {
            totalPrice += product.getPrice() * product.getQuantity();
        }

        // ดึง Customer_ID จาก currentUser
        String customerId = LoginConnect.getCurrentUser().getID(); // สมมุติว่า `getId()` คืนค่า Customer_ID

        // สร้าง Order object
        Order order = new Order(orderID, null, customerId, 1, new Timestamp(System.currentTimeMillis()), totalPrice, actionType, null);

        // บันทึกลงฐานข้อมูล
        saveOrderToDatabase(order);

        // บันทึก Order Line
        saveOrderLineToDatabase(orderID, productList); // บันทึกข้อมูล product ลงใน order_line
    }


    private void saveOrderToDatabase(Order order) {
        String sql = "INSERT INTO `order` (Order_ID, Employee_ID, Customer_ID, Order_Status, Order_Timestamp, Outstanding_Balance, Order_Type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnect.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // ตรวจสอบและจำกัดขนาดของ Order_ID
            if (order.getOrder_ID().length() > 30) { // สมมุติว่า Order_ID มีขนาดสูงสุด 50 ตัวอักษร
                System.out.println("Error: Order_ID too long");
                return; // หยุดการทำงานถ้า Order_ID ยาวเกินไป
            }
            pstmt.setString(1, order.getOrder_ID());
            pstmt.setString(2, order.getEmployee_ID());
            pstmt.setString(3, order.getCustomer_ID());
            pstmt.setInt(4, order.getOrder_Status());
            pstmt.setTimestamp(5, order.getOrder_Timestamp());
            pstmt.setInt(6, order.getOutstanding_Balance());
            pstmt.setString(7, order.getOrder_Type());

            pstmt.executeUpdate();
            System.out.println("Order saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving order to database: " + e.getMessage());
        }
    }

    private void saveOrderLineToDatabase(String orderID, List<Product> productList) {
        String sql = "INSERT INTO order_line (Product_ID, Order_ID, Quantity_Order_Line) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (Product product : productList) {
                pstmt.setString(1, product.getProduct_ID()); // ดึง Product_ID จาก product
                pstmt.setString(2, orderID); // ใช้ Order_ID ที่สร้างขึ้น
                pstmt.setInt(3, product.getQuantity()); // ใช้ Quantity ที่กรอกมา
                pstmt.addBatch(); // เพิ่มคำสั่ง SQL ใน batch
            }

            pstmt.executeBatch(); // ส่งคำสั่ง SQL ทั้งหมดใน batch
            System.out.println("Order lines saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving order lines to database: " + e.getMessage());
        }
    }




    // Method to calculate total price of the order
    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (Product product : OrderManager.getInstance().getTemporaryProductList()) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        totalOrderPrice.setText(String.format("$%d", totalPrice)); // Update total price label
        return totalPrice;
    }

    private void displayOrderList() {
        vBox.getChildren().clear();
        List<Product> temporaryProductList = OrderManager.getInstance().getTemporaryProductList(); // Retrieve from OrderManager
        for (Product product : temporaryProductList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/orderListCustomerItem.fxml"));
                VBox productItemBox = loader.load();
                orderListCustomerItemController itemController = loader.getController();

                itemController.productNameLabel.setText(product.getProduct_Name());
                itemController.quantityLabel.setText(String.valueOf(product.getQuantity()));
                itemController.priceLabel.setText(String.format("$%d", product.getPrice()));

                ByteArrayInputStream bis = new ByteArrayInputStream(product.getProduct_Image_Byte());
                Image image = new Image(bis);
                itemController.productImageView.setImage(image);

                itemController.setParentVBox(vBox);
                vBox.getChildren().add(productItemBox);
            } catch (IOException e) {
                System.err.println("Error loading product item: " + e.getMessage());
            }
        }
    }

}