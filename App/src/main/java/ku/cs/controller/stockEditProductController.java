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
import ku.cs.services.FXRouter;

import java.io.File;
import java.io.IOException;

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

    @FXML
    public void initialize() {
        // กำหนดให้ Hyperlink ทำงานเมื่อคลิก
        upload.setOnAction(event -> uploadImage());
    }

    // เมธอดสำหรับการอัพโหลดรูปภาพ
    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("เลือกไฟล์รูปภาพ");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // เปิดหน้าต่างเลือกไฟล์
        File file = fileChooser.showOpenDialog(upload.getScene().getWindow());
        if (file != null) {
            // สร้าง Image จากไฟล์และตั้งค่าให้แสดงใน productPic
            Image image = new Image(file.toURI().toString());
            productPic.setImage(image);
        }
    }

    @FXML
    public void saveEdit() {
        try {
            openConfirmWindow();
        } catch (IOException e) {
            System.err.println("Error opening confirmation window: " + e.getMessage());
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
