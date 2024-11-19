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
import ku.cs.connect.DatabaseConnect;
import ku.cs.connect.OrderStatusUpdateConnect;
import ku.cs.connect.orderProductConnect;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class salerCheckProductPageController {

    @FXML
    private Button backButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button refuseButton;

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

    public void setOrderData(String orderId, ObservableList<Product> products) {
        this.orderId = orderId;
        // ใช้ข้อมูลสินค้าที่ได้รับมาแสดงในตารางโดยตรง
        orderProductTable.setItems(products);
        checkOrderStatusAndDisableConfirmButton();
    }


    @FXML
    public void initialize() {
        Product_ID.setCellValueFactory(new PropertyValueFactory<>("Product_ID"));
        Product_Name.setCellValueFactory(new PropertyValueFactory<>("Product_Name"));
        Product_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        Order_Quantity_Line.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Price.setCellValueFactory(new PropertyValueFactory<>("Price"));

        backButton.setOnAction(event -> {
            try {
                backToOrderPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        confirmButton.setOnAction(event -> {
            try {
                openConfirmOrderWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refuseButton.setOnAction(event -> {
            try {
                openRefuseOrderWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("employeeSellerProfile");
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });
    }

    private void backToOrderPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/salerCheckOrderPage.fxml"));
        Parent orderPage = loader.load();
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(orderPage);
        stage.setScene(scene);
        stage.show();
    }

    private void openRefuseOrderWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/refuseOrderWindow.fxml"));
        Parent confirmOrderWindow = loader.load();
        RefuseOrderWindow controller = loader.getController();
        controller.setOrderId(orderId);
        Stage stage = new Stage();
        stage.setScene(new Scene(confirmOrderWindow));
        stage.showAndWait();

        OrderStatusUpdateConnect statusUpdater = new OrderStatusUpdateConnect();
        boolean success = statusUpdater.updateOrderAndCreateInvoice(orderId);

        if (success) {
            System.out.println("Order confirmed and invoice created successfully.");
        } else {
            System.out.println("Failed to confirm order and create invoice.");
        }
    }


    private void openConfirmOrderWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmOrderAcceptWindow.fxml"));
        Parent confirmOrderWindow = loader.load();
        confirmOrderAcceptWindowController controller = loader.getController();
        controller.setOrderId(orderId);
        Stage stage = new Stage();
        stage.setScene(new Scene(confirmOrderWindow));
        stage.showAndWait();

        // เรียกใช้ OrderStatusUpdateConnect เพื่ออัปเดตสถานะและสร้างข้อมูล invoice
        OrderStatusUpdateConnect statusUpdater = new OrderStatusUpdateConnect();
        boolean success = statusUpdater.updateOrderAndCreateInvoice(orderId);

        if (success) {
            System.out.println("Order confirmed and invoice created successfully.");
        } else {
            System.out.println("Failed to confirm order and create invoice.");
        }
    }


    private void checkOrderStatusAndDisableConfirmButton() {
        OrderStatusUpdateConnect statusUpdater = new OrderStatusUpdateConnect();
        int orderStatus = statusUpdater.getOrderStatus(orderId);

        // ตรวจสอบสถานะ และปิดปุ่มยืนยันตามความเหมาะสม
        if (orderStatus >= 2) { // ปิดปุ่มถ้าสถานะเป็นรอชำระเงินหรือชำระเงินแล้ว
            confirmButton.setDisable(true);
        } else {
            confirmButton.setDisable(false);
        }
    }

    private void openDeniedOrderWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/deniedOrderWindow.fxml"));
        Parent deniedOrderWindow = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(deniedOrderWindow));
        stage.showAndWait();
    }


    @FXML
    public void goVerifyPayment() {
        try {
            FXRouter.goTo("verifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goCheckOrder() {
        try {
            FXRouter.goTo("salerCheckOrder");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
