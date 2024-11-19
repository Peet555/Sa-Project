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

        // เรียกใช้ ProductSaleConnect เพื่อดึงข้อมูลสินค้าและ paymentImageData
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

    // Method to update the Invoice_Status and Order_Status
    public void updateInvoiceStatus() {
        String selectOrderTypeQuery = "SELECT Order_Type, Status_Pay FROM invoice JOIN `order` ON invoice.Order_ID = `order`.Order_ID WHERE Invoice_ID = ?";
        String updateInvoiceQuery = "UPDATE invoice SET Status_Pay = ? WHERE Invoice_ID = ?";

        try (Connection connection = DatabaseConnect.getConnection()) {
            // ตรวจสอบ Order_Type และ Status_Pay
            try (PreparedStatement selectStatement = connection.prepareStatement(selectOrderTypeQuery)) {
                selectStatement.setString(1, invoiceID);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String orderType = resultSet.getString("Order_Type");
                        int statusPay = resultSet.getInt("Status_Pay");

                        // เงื่อนไข: ถ้า Order_Type = "สั่งจอง" และ Status_Pay = 4
                        if ("สั่งจอง".equals(orderType) && statusPay == 4) {
                            // อัปเดต Status_Pay เป็น 5
                            try (PreparedStatement updateStatement = connection.prepareStatement(updateInvoiceQuery)) {
                                updateStatement.setInt(1, 5); // ตั้งค่า Status_Pay เป็น 5
                                updateStatement.setString(2, invoiceID);
                                int rowsUpdated = updateStatement.executeUpdate();

                                if (rowsUpdated > 0) {
                                    System.out.println("อัปเดตสถานะการชำระเงินสำเร็จ");
                                } else {
                                    System.out.println("ไม่พบข้อมูลสำหรับอัปเดต");
                                }
                            }
                        } else {
                            System.out.println("ไม่สามารถอัปเดตสถานะได้เนื่องจากเงื่อนไขไม่ตรง");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("เกิดข้อผิดพลาดในการอัปเดตสถานะ: " + e.getMessage());
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
