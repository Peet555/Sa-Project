package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmOrderAcceptWindowController {

    @FXML
    private Button confirmButton, cancelButton;

    @FXML
    public void initialize() {
        confirmButton.setOnAction(event -> {
            try {
                openNotiInvoiceWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        cancelButton.setOnAction(event -> {
            closeWindow();
        });
    }

    // เมธอดสำหรับเปิดหน้าต่าง notiInvoiceCreatedWindowController
    private void openNotiInvoiceWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/notiInvoiceCreatedWindow.fxml"));
        Parent notiInvoiceWindow = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(notiInvoiceWindow));
        stage.showAndWait(); // รอจนกว่าจะปิดหน้าต่าง

        closeWindow(); // ปิดหน้าต่าง confirmOrder
    }

    // เมธอดสำหรับปิดหน้าต่าง
    private void closeWindow() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
