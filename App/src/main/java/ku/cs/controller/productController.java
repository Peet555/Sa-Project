package ku.cs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ku.cs.services.FXRouter;

import java.io.IOException;
import java.util.Objects;

public class productController {

    @FXML

    public Button buttonOrder;
    public Button buttonPreOrder;

    public VBox vBox;

    public void initialize() throws IOException {
        addTypeProductItem(1);

        homeButton.setOnAction(event -> {
            try {
                FXRouter.goTo("Homepage"); // เปลี่ยนไปหน้า HomePage
            } catch (IOException e) {
                System.err.println();

            }
        });

    }

    public Button homeButton; // เชื่อมต่อกับปุ่ม "Home" จาก FXML

    public void addTypeProductItem(int index) throws IOException {

        VBox p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ku/cs/view/productItem.fxml")));

        vBox.getChildren().add(p);


    }
}
