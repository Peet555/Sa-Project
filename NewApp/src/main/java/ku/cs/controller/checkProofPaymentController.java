package ku.cs.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.connect.DatabaseConnect;
import ku.cs.connect.InvoiceOrderConnect;
import ku.cs.services.FXRouter;
import ku.cs.connect.InvoiceOrderConnect.ProductSalesData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class checkProofPaymentController {

    private String invoiceID;

    private byte[] paymentImageData; // ตัวแปรสำหรับเก็บ Payment_Image
    @FXML
    private TableView<ProductSale> paymentList ;

    @FXML
    private TableColumn<ProductSale,String> id ;
    @FXML
    private TableColumn<ProductSale,String> name ;
    @FXML
    private TableColumn<ProductSale,Integer> quantity ;
    @FXML
    private TableColumn<ProductSale,Integer> productPrice ;
    @FXML
    private TableColumn<ProductSale,Integer> price ;
    @FXML
    private Label sumPrice;

    @FXML
    public ImageView proofPayment ;


    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;

        // ดึงข้อมูลสินค้าและ paymentImageData
        ProductSalesData data = InvoiceOrderConnect.loadProductSales(invoiceID);

        // ตั้งค่า productSales และ paymentImageData
        paymentList.setItems(data.productSales);
        paymentImageData = data.paymentImageData;

        // โหลดรูปภาพการชำระเงิน
        loadProofPaymentImage();

        // คำนวณราคารวม
        int totalSumPrice = data.productSales.stream().mapToInt(ProductSale::getPrice).sum();
        sumPrice.setText(String.valueOf(totalSumPrice));
    }

    private void loadProofPaymentImage() {
        if (paymentImageData != null) {
            InputStream imageStream = new ByteArrayInputStream(paymentImageData);
            Image image = new Image(imageStream);
            proofPayment.setImage(image);
        }
    }



    @FXML
    public void initialize(){
        id.setCellValueFactory(new PropertyValueFactory<>("productID"));
        name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        proofPayment.setOnMouseClicked(event -> openImageInNewWindow(proofPayment.getImage()));    }

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

    @FXML
    public void confirmCheck(){
        try {
            openConfirmWindow();
        } catch (IOException e) {
            System.err.println("Error opening confirm window: " + e.getMessage());
        }
    }

    private void openConfirmWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmCheckPaymentWindow.fxml"));
        Parent root = loader.load();

        // Pass the current instance of this controller to the confirm window controller
        confirmCheckPaymentWindowController confirmController = loader.getController();
        confirmController.setCheckProofPaymentController(this);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Confirmation");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void updateInvoiceStatus() {
        boolean isUpdated = InvoiceOrderConnect.updateInvoiceStatus(invoiceID);
        if (isUpdated) {
            System.out.println("สถานะใบแจ้งหนี้และออร์เดอร์ได้รับการอัปเดตสำเร็จ");
        } else {
            System.out.println("ไม่สามารถอัปเดตสถานะใบแจ้งหนี้และออร์เดอร์ได้");
        }
    }




    private void openImageInNewWindow(Image image) {
        if (image != null) {
            // สร้าง ImageView และ Stage ใหม่
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(800); // กำหนดขนาดกว้างสุด
            imageView.setFitHeight(800); // กำหนดขนาดสูงสุด

            // สร้าง Scene และ Stage สำหรับแสดงรูปภาพขนาดใหญ่
            Stage stage = new Stage();
            stage.setTitle("แสดงรูปภาพขนาดใหญ่");
            stage.initModality(Modality.APPLICATION_MODAL); // ให้เป็น modal window

            // ใส่ ImageView ลงใน Scene
            Scene scene = new Scene(new StackPane(imageView), 800, 800);
            stage.setScene(scene);

            // ปิดหน้าต่างเมื่อคลิกที่ไหนก็ได้ในหน้าต่าง
            scene.setOnMouseClicked(e -> stage.close());

            // แสดงหน้าต่าง
            stage.showAndWait();
        }
    }


    @FXML
    public void backClick(){
        try {
            FXRouter.goTo("verifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static class ProductSale {
        private String productID ;
        private String productName ;
        private Integer quantity ;
        private Integer productPrice ;
        private Integer price ;

        public ProductSale(String productID, String productName, Integer quantity, Integer productPrice, Integer price) {
            this.productID = productID;
            this.productName = productName;
            this.quantity = quantity;
            this.productPrice = productPrice;
            this.price = price;
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

        public Integer getPrice() {
            return price;
        }
    }
}
