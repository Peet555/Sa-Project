package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.connect.stockConnect;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.*;

public class stockEditProductController {
    @FXML
    public ImageView logo;

    @FXML
    public TextField nameText;
    @FXML
    public TextField quantityText;
    @FXML
    public TextField typeText;
    @FXML
    public TextField priceText;

    @FXML
    public ImageView productPic;
    @FXML
    public TextArea description;
    @FXML
    public Hyperlink upload;

    private Product selectedProduct;  // เก็บข้อมูล Product ที่ต้องการแก้ไข
    private File selectedImageFile;

    @FXML
    public void initialize() {
        // รับข้อมูล Product ที่ส่งมาจาก stockController
        selectedProduct = (Product) FXRouter.getData();

        // กำหนดให้ Hyperlink ทำงานเมื่อคลิก
        upload.setOnAction(event -> uploadImage());

        if (selectedProduct != null && selectedProduct.getImage() != null) {
            productPic.setImage(new Image(selectedProduct.getImage()));
        }



        // แสดงข้อมูล Product ที่เลือกในฟอร์มแก้ไข
        if (selectedProduct != null) {
            nameText.setText(selectedProduct.getProduct_Name());
            quantityText.setText(String.valueOf(selectedProduct.getQuantity()));
            typeText.setText(selectedProduct.getType());
            priceText.setText(String.valueOf(selectedProduct.getPrice()));
            description.setText(selectedProduct.getDescription());
        }
    }

    // เมธอดสำหรับการอัพโหลดรูปภาพ
    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("เลือกไฟล์รูปภาพ");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(upload.getScene().getWindow());
        if (file != null) {
            // Create Image from file and display it
            Image image = new Image(file.toURI().toString());
            productPic.setImage(image);

            // Save the selected image file reference
            selectedImageFile = file; // Save the file for later use in saveEdit
        }
    }

    @FXML
    public void saveEdit() {
        // Set the product details
        selectedProduct.setProduct_Name(nameText.getText());
        selectedProduct.setQuantity(Integer.parseInt(quantityText.getText()));
        selectedProduct.setType(typeText.getText());
        selectedProduct.setPrice(Integer.parseInt(priceText.getText()));
        selectedProduct.setDescription(description.getText());

        // If an image was uploaded, convert it to InputStream
        if (selectedImageFile != null) {
            try {
                InputStream inputStream = new FileInputStream(selectedImageFile);
                selectedProduct.setImage(inputStream);
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
            }
        } else {
            selectedProduct.setImage(null); // No image selected
        }

        // Update the database
        stockConnect.updateProduct(selectedProduct);

        // Return to product list
        try {
            FXRouter.goTo("stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void cancleEdit() {
        try {
            FXRouter.goTo("stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
    public void goStock() {
        try {
            FXRouter.goTo("stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openConfirmWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/editConfirmWindow.fxml"));
        Parent root = loader.load();

        // สร้าง Stage สำหรับหน้าต่างใหม่
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Confirmation");
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal
        stage.showAndWait();  // แสดงหน้าต่าง
    }
}
