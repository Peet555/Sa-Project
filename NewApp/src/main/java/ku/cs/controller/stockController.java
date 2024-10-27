package ku.cs.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import ku.cs.connect.stockConnect;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class stockController {

    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, String> Product_ID;
    @FXML
    public TableColumn<Product, String> Product_Name;
    @FXML
    public TableColumn<Product, Integer> Quantity;
    @FXML
    public TableColumn<Product, Integer> Price;
    @FXML
    public TableColumn<Product, String> Type;
    @FXML
    public Button editButton;  // ปุ่มแก้ไขข้อมูล
    @FXML
    public Button profileButton;

    @FXML
    public void initialize() {
        Product_ID.setCellValueFactory(new PropertyValueFactory<>("Product_ID"));
        Product_Name.setCellValueFactory(new PropertyValueFactory<>("Product_Name"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));


        stockConnect stockDb = new stockConnect();
        ObservableList<Product> products = stockDb.getAllProducts();
        productTable.setItems(products);

        // ปิดการใช้งานปุ่ม "แก้ไขข้อมูล" ก่อนเลือกข้อมูล
        editButton.setDisable(true);

        // เพิ่ม listener เพื่อตรวจสอบว่าแถวถูกเลือกในตารางหรือไม่
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
        if (!productTable.getSelectionModel().isEmpty()) {  // ตรวจสอบว่ามีการเลือกแถวหรือไม่
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            try {
                FXRouter.goTo("editStock", selectedProduct);  // ส่งข้อมูล product ที่เลือกไปยัง editStock
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}