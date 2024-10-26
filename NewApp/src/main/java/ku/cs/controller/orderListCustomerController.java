package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class orderListCustomerController {

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
        // จำนวนสินค้าที่จะ Mock ข้อมูล (เช่น 5 รายการ)
        int itemCount = 5;

        // เพิ่มรายการสินค้าแต่ละอันเข้าไปใน VBox
        for (int i = 0; i < itemCount; i++) {
            addProductItem(i);
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

    // Method สำหรับเพิ่มสินค้า Mock ลงใน VBox
    private void addProductItem(int index) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/orderListCustomerItem.fxml"));
        Node node = loader.load();

        // ส่ง VBox (vBox) ไปยัง orderListCustomerItemController
        orderListCustomerItemController controller = loader.getController();
        controller.setParentVBox(vBox);

        vBox.getChildren().add(node);
    }

}
