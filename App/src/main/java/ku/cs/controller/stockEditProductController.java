package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;


import java.awt.*;
import java.io.IOException;

public class stockEditProductController {
    @FXML
    public ImageView logo ;

    @FXML
    public TextField nameText ;

    @FXML
    public TextField quantityText ;
    @FXML
    public TextField typeText ;
    @FXML
    public TextField priceText ;

    @FXML
    public ImageView productPic ;

    @FXML
    public TextArea description ;

    @FXML
    public Hyperlink upload ;

    @FXML
    public void saveEdit() {
        try {
            openConfirmWindow();
        } catch (IOException e) {
            System.err.println("Error opening payment window: " + e.getMessage());
        }
    }

    @FXML
    public void cancleEdit() {
        try {
            FXRouter.goTo("Stock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goOrder(){
        try {
            FXRouter.goTo("OrderStock");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goDeliver(){
        try {
            FXRouter.goTo("Delivery");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goStock(){
        try {
            FXRouter.goTo("Stock");
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
        stage.initModality(Modality.APPLICATION_MODAL);  // หน้าต่างใหม่จะเป็นแบบ modal (โฟกัสเฉพาะหน้าต่างนี้)
        stage.showAndWait();  // แสดงหน้าต่าง
    }



}