package ku.cs.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ku.cs.connect.OrderStatusUpdateConnect;
import ku.cs.connect.orderProductConnect;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import javax.swing.text.TabableView;
import java.io.IOException;

public class salerCheckProductPageController {

    @FXML
    private Button backButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button denyButton;
    @FXML
    private Button profileButton;


    @FXML
    private TableView<Product> orderProductTable;
    @FXML
    private TableColumn<Product, String> Product_ID;
    @FXML
    private TableColumn<Product, String> Product_Type;
    @FXML
    private TableColumn<Product, String> Product_Name;
    @FXML
    private TableColumn<Product, Integer> Order_Quantity_Line;
    @FXML
    private TableColumn<Product, Double> Price;

    private String orderId;

    public void setOrderID(String orderId) {
        this.orderId = orderId;
        loadProducts();
        checkOrderStatusAndDisableConfirmButton(); // เรียกใช้การตรวจสอบสถานะหลังจาก orderId ได้ค่าแล้ว
    }

    private void loadProducts() {
        orderProductConnect productConnect = new orderProductConnect();
        ObservableList<Product> products = productConnect.getProductsForOrder(orderId);
        orderProductTable.setItems(products);
    }

    @FXML
    public void initialize() {


        Product_ID.setCellValueFactory(new PropertyValueFactory<>("Product_ID"));
        Product_Name.setCellValueFactory(new PropertyValueFactory<>("Product_Name"));
        Product_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Order_Quantity_Line.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        // กำหนดการทำงานของปุ่มย้อนกลับ
        backButton.setOnAction(event -> {
            try {
                backToOrderPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // กำหนดการทำงานของปุ่มยืนยันคำสั่งซื้อ
        confirmButton.setOnAction(event -> {
            try {
                openConfirmOrderWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // กำหนดการทำงานของปุ่มปฎิเสธคำสั่งซื้อ
        denyButton.setOnAction(event -> {
            try {
                openDeniedOrderWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("employeeSellerProfile"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });


    }


    // เมธอดเพื่อกลับไปยังหน้า salerCheckOrderPageController
    private void backToOrderPage() throws IOException {
        // โหลดหน้า salerCheckOrderPage.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/salerCheckOrderPage.fxml"));
        Parent orderPage = loader.load();

        // ตั้งค่า Scene ใหม่เพื่อแสดงหน้า order page
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(orderPage);
        stage.setScene(scene);
        stage.show();
    }
    // เมธอดสำหรับเปิดหน้าต่างยืนยันคำสั่งซื้อ
    private void openConfirmOrderWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmOrderAcceptWindow.fxml"));
        Parent confirmOrderWindow = loader.load();

        confirmOrderAcceptWindowController controller = loader.getController();
        controller.setOrderId(orderId); // ส่ง Order_ID ไปยังหน้าต่างยืนยันคำสั่งซื้อ

        Stage stage = new Stage();
        stage.setScene(new Scene(confirmOrderWindow));
        stage.showAndWait();
    }

    private void checkOrderStatusAndDisableConfirmButton() {
        OrderStatusUpdateConnect statusUpdater = new OrderStatusUpdateConnect();
        int orderStatus = statusUpdater.getOrderStatus(orderId); // ดึงสถานะคำสั่งซื้อจากฐานข้อมูล

        System.out.println("Order Status: " + orderStatus); // เพิ่มการดีบักค่า orderStatus
        if (orderStatus == 2) { // ถ้าสถานะเป็น 2
            confirmButton.setDisable(true); // ปิดการใช้งานปุ่มยืนยันคำสั่งซื้อ
            System.out.println("Confirm button is now disabled.");
        } else {
            confirmButton.setDisable(false); // เปิดการใช้งานปุ่มเมื่อสถานะไม่เป็น 2
            System.out.println("Confirm button is enabled.");
        }
    }

    // เมธอดสำหรับเปิดหน้าต่างปฎิเสธคำสั่งซื้อ
    private void openDeniedOrderWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/deniedOrderWindow.fxml"));
        Parent deniedOrderWindow = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(deniedOrderWindow));
        stage.showAndWait(); // รอจนกว่าจะปิดหน้าต่าง
    }

    @FXML
    public void goVerifyPayment(){
        try {
            FXRouter.goTo("verifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void goCheckOrder(){
        try {
            FXRouter.goTo("salerCheckOrder");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
