package ku.cs.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class checkProofPaymentController {
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
    public ImageView proofPayment ;

    @FXML
    public void initialize(){
        id.setCellValueFactory(new PropertyValueFactory<>("productID"));
        name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<ProductSale> productSales = FXCollections.observableArrayList(
                new ProductSale("001", "TV", 2, 1000,2000)
        );

        paymentList.setItems(productSales);
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

        // สร้าง Stage สำหรับหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Confirmation");
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal (โฟกัสเฉพาะหน้าต่างนี้)
        stage.showAndWait();  // แสดงหน้าต่าง
    }

    @FXML
    public void backClick(){
        try {
            FXRouter.goTo("verifyPayment");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cancleClick() {
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
