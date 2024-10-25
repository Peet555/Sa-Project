package ku.cs.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class stockController {

    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn<Product, String> productID;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, Integer> quantity;
    @FXML
    private TableColumn<Product, Integer> price;
    @FXML
    private TableColumn<Product, String> supid;
    @FXML
    private Button editButton;  // ปุ่มแก้ไขข้อมูล
    @FXML
    private Button profileButton;

    @FXML
    public void initialize() {
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        supid.setCellValueFactory(new PropertyValueFactory<>("supplierID"));

        ObservableList<Product> products = FXCollections.observableArrayList(
                new Product("001", "TV", 20, 1000, "00010")
        );

        table.setItems(products);

        // ปิดการใช้งานปุ่ม "แก้ไขข้อมูล" ก่อนเลือกข้อมูล
        editButton.setDisable(true);

        // เพิ่ม listener เพื่อตรวจสอบว่าแถวถูกเลือกในตารางหรือไม่
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // หากเลือกแถวให้เปิดใช้งานปุ่มแก้ไข, หากไม่ให้ปิดการใช้งาน
            editButton.setDisable(newValue == null);
        });
        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("employeeWarehouseProfile"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });
    }

    @FXML
    public ImageView logo;

    @FXML
    public void goOrder() {
        try {
            FXRouter.goTo("orderStock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goDeliver() {
        try {
            FXRouter.goTo("delivery");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goAdd() {
        try {
            FXRouter.goTo("addStock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goEdit() {
        if (!table.getSelectionModel().isEmpty()) {  // ตรวจสอบว่ามีการเลือกแถวหรือไม่
            try {
                FXRouter.goTo("editStock");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class Product {
        private String productID;
        private String productName;
        private Integer quantity;
        private Integer productPrice;
        private String supplierID;

        public Product(String productID, String productName, Integer quantity, Integer productPrice, String supplierID) {
            this.productID = productID;
            this.productName = productName;
            this.quantity = quantity;
            this.productPrice = productPrice;
            this.supplierID = supplierID;
        }

        public String getProductID() {
            return productID;
        }

        public String getProductName() {
            return productName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Integer getProductPrice() {
            return productPrice;
        }

        public String getSupplierID() {
            return supplierID;
        }
    }

}
