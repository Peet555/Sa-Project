package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

public class stockAddProductController {

    @FXML
    public ImageView logo;

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

    @FXML
    public void initialize() {
        upload.setOnAction(event -> uploadImage());
    }

    // Method to validate input fields
    private boolean validateInput() {
        if (nameAdd.getText().isEmpty() || quantityAdd.getText().isEmpty() ||
                typeAdd.getText().isEmpty() || priceAdd.getText().isEmpty()) {
            showAlert("Error", "กรุณาใส่ข้อมูลให้ครบ");
            return false;
        }
        return true;
    }

    // Method to show alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // Method for uploading images
    @FXML
    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Open file chooser dialog
        File file = fileChooser.showOpenDialog(upload.getScene().getWindow());
        if (file != null) {
            // Create Image from the file and set to addPic
            Image image = new Image(file.toURI().toString());
            addPic.setImage(image);
        }
    }

    @FXML
    public void saveAdd() {
        if (!validateInput()) {
            return; // Stop if validation fails
        }

        try {
            openConfirmWindow();
        } catch (IOException e) {
            System.err.println("Error opening confirmation window: " + e.getMessage());
        }
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

    private void openConfirmWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/addConfirmWindow.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Confirmation");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
