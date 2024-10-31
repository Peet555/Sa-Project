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
import ku.cs.services.FXRouter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class checkProofPaymentController {

    private String invoiceID;
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
        loadProductSales();
        loadProofPaymentImage();
    }

    private void loadProductSales() {
        ObservableList<ProductSale> productSales = FXCollections.observableArrayList();
        String query = "SELECT p.Product_ID, p.Product_Name, ol.Quantity_Order_Line, p.Price, " +
                "(ol.Quantity_Order_Line * p.Price) AS TotalPrice " +
                "FROM Order_Line ol " +
                "JOIN Product p ON ol.Product_ID = p.Product_ID " +
                "JOIN `order` o ON ol.Order_ID = o.Order_ID " +
                "JOIN invoice i ON o.Order_ID = i.Order_ID " +
                "WHERE i.Invoice_ID = ?";

        int totalSumPrice = 0; // ตัวแปรเก็บผลรวมราคา

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, invoiceID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int quantity = resultSet.getInt("Quantity_Order_Line");
                    int productPrice = resultSet.getInt("Price");
                    int totalPrice = resultSet.getInt("TotalPrice");

                    productSales.add(new ProductSale(
                            resultSet.getString("Product_ID"),
                            resultSet.getString("Product_Name"),
                            quantity,
                            productPrice,
                            totalPrice
                    ));

                    totalSumPrice += totalPrice; // เพิ่มราคาของสินค้าลงในยอดรวม
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading product sales: " + e.getMessage());
        }

        paymentList.setItems(productSales);
        sumPrice.setText(String.valueOf(totalSumPrice)); // แสดงยอดรวมใน Label sumPrice
    }

    private void loadProofPaymentImage() {
        String query = "SELECT Payment_Image FROM invoice WHERE Invoice_ID = ?";
        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, invoiceID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    byte[] imageData = resultSet.getBytes("Payment_Image");
                    if (imageData != null) {
                        InputStream imageStream = new ByteArrayInputStream(imageData);
                        Image image = new Image(imageStream);
                        proofPayment.setImage(image); // แสดงรูปภาพใน ImageView
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading proof payment image: " + e.getMessage());
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
        String updateInvoiceQuery = "UPDATE invoice SET Status_Pay = ? WHERE Invoice_ID = ?";
        String updateOrderQuery = "UPDATE `order` SET Order_Status = ? WHERE Order_ID = (SELECT Order_ID FROM invoice WHERE Invoice_ID = ?)";

        try (Connection connection = DatabaseConnect.getConnection()) {
            // Update invoice status
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateInvoiceQuery)) {
                preparedStatement.setInt(1, 3); // Set new status for invoice
                preparedStatement.setString(2, invoiceID);
                preparedStatement.executeUpdate();
            }

            // Update order status
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateOrderQuery)) {
                preparedStatement.setInt(1, 3); // Assuming you want to set Order_Status to the same value
                preparedStatement.setString(2, invoiceID);
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("Affected rows in order: " + affectedRows); // Print affected rows
            }
        } catch (SQLException e) {
            System.err.println("Error updating invoice and order status: " + e.getMessage());
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
