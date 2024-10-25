package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class stockOrderController {

    @FXML
    public ImageView logo;

    @FXML
    public TextField idStockOrder;

    @FXML
    public TextField quantityStockOrder;

    @FXML
    private Button profileButton;

    @FXML
    private Button recordOrderForSup;

    @FXML
    public void initialize() {
        // Disable the button initially
        recordOrderForSup.setDisable(true);

        // Add listeners to the text fields to enable the button if both fields are filled
        idStockOrder.textProperty().addListener((observable, oldValue, newValue) -> checkFields());
        quantityStockOrder.textProperty().addListener((observable, oldValue, newValue) -> checkFields());

        profileButton.setOnAction(event -> {
            try {
                FXRouter.goTo("employeeWarehouseProfile");
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });

        recordOrderForSup.setOnAction(event -> {
            try {
                recordOrderForSup();
            } catch (IOException e) {
                System.err.println("Cannot go to profile");
            }
        });
    }

    private void checkFields() {
        // Enable the button only if both text fields are not empty
        recordOrderForSup.setDisable(idStockOrder.getText().isEmpty() || quantityStockOrder.getText().isEmpty());
    }

    @FXML
    public void goStock() {
        try {
            FXRouter.goTo("stock");
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

    private void recordOrderForSup() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/recordOrderForSupWindow.fxml"));
        Parent root = loader.load();

        // Create a new stage for the new window
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);  // Modal window
        stage.showAndWait();  // Show the window
    }
}
