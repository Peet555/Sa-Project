package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.connect.stockAddProductConnect;
import ku.cs.services.FXRouter;

import java.io.File;
import java.io.IOException;

public class stockAddProductController {

    @FXML
    public ImageView logo;
    @FXML
    public TextField productId;
    @FXML
    public TextField nameAdd;
    @FXML
    public TextField quantityAdd;
    @FXML
    public TextField typeAdd;
    @FXML
    public TextField priceAdd;
    @FXML
    public ImageView addPic;
    @FXML
    public TextArea description;
    @FXML
    public Hyperlink upload;

    private stockAddProductConnect stockConnect = new stockAddProductConnect();

    @FXML
    public void initialize() {
        upload.setOnAction(event -> uploadImage());
    }

    private boolean validateInput() {
        if (productId.getText().isEmpty() || nameAdd.getText().isEmpty() || quantityAdd.getText().isEmpty() ||
                typeAdd.getText().isEmpty() || priceAdd.getText().isEmpty()) {
            showAlert("Error", "กรุณาใส่ข้อมูลให้ครบ");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(upload.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            addPic.setImage(image);
        }
    }

    @FXML
    public void saveAdd() {
        if (!validateInput()) {
            return;
        }

        String id = productId.getText();
        String name = nameAdd.getText();
        int quantity = Integer.parseInt(quantityAdd.getText());
        String type = typeAdd.getText();
        double price = Double.parseDouble(priceAdd.getText());
        String desc = description.getText();

        stockConnect.addProduct(id, name, quantity, type, price, desc);

        showAlert("Success", "Product added successfully!");
        clearFields();
    }

    private void clearFields() {
        productId.clear();
        nameAdd.clear();
        quantityAdd.clear();
        typeAdd.clear();
        priceAdd.clear();
        description.clear();
        addPic.setImage(null);
    }

    @FXML
    public void cancleAdd() {
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
}
