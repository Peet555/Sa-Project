package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.util.Objects;

public class productController {

    @FXML
    public Button buttonOrderList;

    public VBox vBox;

    @FXML
    public Button homeButton;

    public void initialize() throws IOException {
        addTypeProductItem(1);

        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("Homepage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println("Cannot go to Homepage");
            }
        });

        buttonOrderList.setOnAction(event -> {
            try {
                // เมื่อกดปุ่ม เพิ่มในรายการสินค้า ให้เปลี่ยนไปยังหน้า productOrderListCustomerController
                FXRouter.goTo("orderListPageCustomer"); // ชื่อเส้นทางที่ต้องการไป
            } catch (IOException e) {
                System.err.println("Cannot go to productOrderListCustomer");
            }
        });
    }

    public void addTypeProductItem(int index) throws IOException {
        VBox p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ku/cs/view/productItem.fxml")));
        vBox.getChildren().add(p);
    }
}
