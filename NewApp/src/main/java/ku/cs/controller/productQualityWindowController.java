package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import ku.cs.models.Product;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class productQualityWindowController {
    private Product product;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField quantity;
    @FXML
    private Button confirmOrder;  // Corrected this line to mark it as @FXML

    public void setProduct(Product product) {
        this.product = product;
    }
    @FXML
    public void closeWindow() {
        // ปิดหน้าต่างที่เป็น Modal (จะใช้ Stage ที่เป็นหน้าต่างใหม่)
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();  // ปิดหน้าต่าง Modal
    }

    @FXML
    public void confirmOrder(ActionEvent event) {
        try {

            closeWindow();
            // เปลี่ยนไปที่หน้า orderListPageCustomer
            FXRouter.goTo("orderListPageCustomer",new Object[]{product,quantity.getText()});
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot go to orderListPageCustomer");
        }
    }
}
